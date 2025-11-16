package app.theblu.cashflow.cs.zone.org.service.orgindex;


import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.zone.org.repo.OrgRepo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class OrgIndexService {
    private static String DLT_FILE_DOWNLOAD_LINK = "https://www.vilpower.in/header_link_doc/";
    private static final ReentrantLock lock = new ReentrantLock();
    private final OrgRepo orgRepo;
    private File tempDltPdf = new File("/var/tmp/dlt-file.pdf");
    private File tempDltJson = new File("/var/tmp/dlt-file.json");
    private DltFileReader reader = new DltFileReader(tempDltPdf, tempDltJson);

    public OrgIndexService(OrgRepo orgRepo) {
        this.orgRepo = orgRepo;
    }

    @SneakyThrows
    public void index() {
        try {
            if (lock.tryLock()) {
                long start = System.currentTimeMillis();
                this.download();
                this.read();
                this.update();
                this.delete();
                long end = System.currentTimeMillis() - start;
                log.info("Header Update finished. Indexing took: {}.", end);
            }
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void download() {
        this.delete();
        log.info("downloading dlt file to location: {}", tempDltPdf.toPath().toAbsolutePath());
        try (BufferedInputStream in = new BufferedInputStream(new URL(DLT_FILE_DOWNLOAD_LINK).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(tempDltPdf)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            log.info("downloaded dlt file location: {}", tempDltPdf.getAbsolutePath());
        }
    }

    public void read() {
        if (tempDltPdf == null || !(tempDltPdf.exists())) return;
        log.info("reading dlt file: {}", tempDltPdf.toPath().toAbsolutePath());
        this.reader = new DltFileReader(tempDltPdf, tempDltJson).processToJson();
        log.info("reading dlt file done: {}", tempDltPdf.toPath().toAbsolutePath());
    }

    public void update() {
        if (reader == null) return;
        List<Org> newOrgs = reader.readFromJson();
        new OrgIndexDbUpdater().update(newOrgs, orgRepo);
    }

    public void delete() {
        if (tempDltPdf.exists()) {
            tempDltPdf.delete();
            log.info("deleted last known dlt file: {}", tempDltPdf.getAbsoluteFile());
        }
    }
}
