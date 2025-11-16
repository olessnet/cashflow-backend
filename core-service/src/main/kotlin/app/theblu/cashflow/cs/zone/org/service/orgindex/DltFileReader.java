package app.theblu.cashflow.cs.zone.org.service.orgindex;

import app.theblu.cashflow.cs.batteries.common.Hash;
import app.theblu.cashflow.cs.batteries.common.JPattern;
import app.theblu.cashflow.cs.batteries.common.JsonUtil;
import app.theblu.cashflow.cs.domain.org.Org;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tomcat.util.buf.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

// https://www.vilpower.in/header_link_doc/
// https://www.trai.gov.in/node/7411
@Slf4j
public class DltFileReader {
    private static final String EXPRESSION = "(\\d+)\\s+([a-zA-z0-9]+)\\s+(.+)(Transactional\\/Service|Promotional)";
    //    private Map<String, Org> entityMap = new HashMap<>();
    private final File dltPdf;
    private final File dltJson;

    public DltFileReader(File dltPdf, File dltJson) {
        this.dltPdf = dltPdf;
        this.dltJson = dltJson;
    }

    @SneakyThrows
    public DltFileReader processToJson() {
        List<String> lines = readFromPdf();
        lines = clean(lines);
        List<List<String>> dataGroups = dataGroups(lines);
        List<Org> fileOrgs = mapLinesToOrgs(dataGroups);
        var temp = fileOrgs.stream().filter(org -> org.getName().toLowerCase().contains("bank")).toList();

        String json = JsonUtil.INSTANCE.toJson(fileOrgs);
        Files.write(dltJson.toPath(), json.getBytes());
        return this;
    }

    @SneakyThrows
    private List<String> readFromPdf() {
        log.info("reading dtl header file pdf file");
        java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.OFF);
//        ClassPathResource classPathResource = new ClassPathResource("dlt_headers.pdf");
        PDDocument document = Loader.loadPDF(new FileInputStream(dltPdf).readAllBytes());
        PDFTextStripper stripper = new PDFTextStripper();
        String[] lines = stripper.getText(document).split("\\n");
        List<String> list = Arrays.asList(lines);
        log.info("dtl header pdf file parsed. total lines: {}", list.size());
        return list;
    }

    @SneakyThrows
    public List<Org> readFromJson() {
        log.info("reading dtl header from json file");
        String raw = Files.readString(dltJson.toPath());
        List<Org> lines = JsonUtil.INSTANCE.fromJsonAsList(raw, Org.class);
        lines.forEach(org -> {
            if (org.getShortName() == null) org.setShortName("");
        });
        log.info("dtl header json file parsed. total lines: {}", lines.size());
        return lines;
    }

    /**
     * Each line in PDF May be separated here into multiple lines
     * We will append these multiple lines into single line
     * Also we will discard lines that are not actual org lines
     */
    private LinkedList<String> clean(List<String> lines) {
        log.info("cleaning dlt header lines. total: {}", lines.size());
        LinkedList<String> temp = new LinkedList<>();
        LinkedList<String> buffer = new LinkedList<>();
        for (int ii = 0; ii < lines.size(); ii++) {
            String line = lines.get(ii);
            if (!buffer.isEmpty()) {
                String text = StringUtils.join(buffer, ' ');
                if (new JPattern(EXPRESSION, false, true).hasMatch(text)) {
                    temp.add(text);
                    buffer.clear();
                }
            }
            if (new JPattern(EXPRESSION, false, true).hasMatch(line)) {
                temp.add(line);
                buffer.clear();
            } else {
                buffer.add(line);
            }
        }
        log.info("cleaning done. total: {}", temp.size());
        return temp;
    }

    private List<List<String>> dataGroups(List<String> lines) {
        log.info("parsing data groups");
        return lines
                .parallelStream()
                .map(l -> JPattern.Companion.forExpression(EXPRESSION).data(l))
                .toList();
    }

    private List<Org> mapLinesToOrgs(List<List<String>> groupList) {
        Map<String, Org> entityMap = new HashMap<>();
        for (List<String> groups : groupList) {
            String header = groups.get(1).trim();
            String name = groups.get(2).toUpperCase().trim();
            if (!org.springframework.util.StringUtils.hasText(name)) {
                name = "Unknown";
            }
            boolean transactional = false;
            if (groups.get(3).startsWith("Transactional")) {
                transactional = true;
            }

            Org org = entityMap.get(name);
            if (org == null) {
                org = new Org();
                org.setId(Hash.INSTANCE.md5(name));
                org.setName(name);
                entityMap.put(name, org);
            }
            if (transactional) {
                org.getTransactionalHeaders().add(header);
            } else {
                org.getPromotionalHeaders().add(header);
            }
            org.setBanking(name.toLowerCase().contains("bank"));
        }
        log.info("mapped {} Orgs from text lines", entityMap.values().size());
        return entityMap.values().stream().toList();
    }

    private void findMissingNumbers(List<Map<String, String>> groupList) {
        Set<Integer> actualNumbers = groupList.stream().map(m -> m.get("$1")).map(n -> Integer.parseInt(n)).collect(Collectors.toSet());
        int first = 1;
        int last = actualNumbers.stream().sorted().reduce((f, s) -> s).get();

        List<Integer> missing = new LinkedList<>();
        for (int ii = 1; ii < last; ii++) {
            if (!actualNumbers.contains(ii)) {
                missing.add(ii);
            }
        }
    }
}
