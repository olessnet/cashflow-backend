package app.theblu.cashflow.cs.zone.org.service.msg;

import app.theblu.cashflow.cs.batteries.common.DateTimeUtil;
import app.theblu.cashflow.cs.batteries.common.IdGen;
import app.theblu.cashflow.cs.batteries.common.JPattern;
import app.theblu.cashflow.cs.batteries.rocket.exception.BusinessValidationException;
import app.theblu.cashflow.cs.batteries.rocket.res.ErrorDetail;
import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.domain.template.MsgDataField;
import app.theblu.cashflow.cs.domain.template.MsgTemplate;
import app.theblu.cashflow.cs.zone.org.repo.MsgTemplateRepo;
import app.theblu.cashflow.cs.zone.org.dto.MsgTemplateSaveReq;
import app.theblu.cashflow.cs.zone.org.service.org.OrgService;
import app.theblu.cashflow.cs.zone.recognizer.dto.MsgRecognitionReq;
import app.theblu.cashflow.cs.zone.recognizer.service.CacheClearService;
import app.theblu.cashflow.cs.zone.recognizer.service.MsgRecognizerService;
import app.theblu.cashflow.cs.zone.recognizer.service.msgParser.MsgParser;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MsgTemplateService {
    private final MsgTemplateService self;
    private final MsgTemplateRepo templateRepo;
    private final OrgService orgService;
    private final CacheClearService cacheClearService;

    public MsgTemplateService(@Lazy MsgTemplateService self, MsgTemplateRepo templateRepo, OrgService orgService, CacheClearService cacheClearService) {
        this.self = self;
        this.templateRepo = templateRepo;
        this.orgService = orgService;
        this.cacheClearService = cacheClearService;
    }

    public List<MsgTemplate> getAll() {
        return this.templateRepo.findAll();
    }

    public Optional<MsgTemplate> getById(String id) {
        return this.templateRepo.findById(id);
    }

    @Cacheable(value = "msg_template_cache")
    public List<MsgTemplate> getByHeader(String header) {
        Optional<Org> orgOptional = this.orgService.findByHeader(header);
        if (orgOptional.isPresent()) {
            Org org = orgOptional.get();
            return self.getByOrgId(org.getId());
        } else {
            return new LinkedList<>();
        }
    }

    @Cacheable(value = "msg_template_cache")
    public List<MsgTemplate> getByOrgId(String orgId) {
        var temp = this.templateRepo.findAllByOrgId(orgId);
        temp = temp.stream().sorted(Comparator.comparing(MsgTemplate::getCreatedAt)).toList();
        Collections.reverse(new LinkedList<>(temp));
        return temp;
    }

    public Object save(MsgTemplateSaveReq saveReq) {
        Object obj = null;
        if (StringUtils.hasText(saveReq.getId())) {
            obj = this.updateTemplate(saveReq);
        } else {
            obj = this.createTemplate(saveReq);
        }

        // TODO Clear only Msg Temlate Cache
        this.cacheClearService.clearCache();
        return obj;
    }

    private MsgTemplate createTemplate(MsgTemplateSaveReq saveReq) {
        List<ErrorDetail> errorDetails = new LinkedList<>();

        errorDetails.addAll(assertOrgExists(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertExpressionNotUsed(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertDataFieldMappingNotEmpty(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertSampleDataIsNotEmpty(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertSampleDataShouldMatch(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertFiChannelAndFiMsgIntent(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        saveReq.setId(IdGen.INSTANCE.uuid());
        MsgTemplate template = saveReq.to();
        template.setCreatedAt(DateTimeUtil.INSTANCE.currentUtcTimestamp());
        template = this.templateRepo.save(template);

        return template;
    }

    private MsgTemplate updateTemplate(MsgTemplateSaveReq saveReq) {
        List<ErrorDetail> errorDetails = new LinkedList<>();

        errorDetails.addAll(assertOrgExists(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertExpressionNotUsed(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertDataFieldMappingNotEmpty(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertSampleDataIsNotEmpty(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertSampleDataShouldMatch(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        errorDetails.addAll(assertFiChannelAndFiMsgIntent(saveReq));
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);

        MsgTemplate template = saveReq.to();
        template = this.templateRepo.save(template);
        return template;
    }

    private List<ErrorDetail> assertOrgExists(MsgTemplateSaveReq saveReq) {
        List<ErrorDetail> errorDetails = new LinkedList<>();
        val optional = orgService.findById(saveReq.getOrgId());
        if (optional.isEmpty()) {
            val ed = new ErrorDetail("orgId", "No org found for given org id", null);
            errorDetails.add(ed);
        }
        return errorDetails;
    }

    private List<ErrorDetail> assertExpressionNotUsed(MsgTemplateSaveReq saveReq) {
        List<ErrorDetail> errorDetails = new LinkedList<>();
        List<MsgTemplate> templates = this.templateRepo.findAll();
        for (MsgTemplate template : templates) {
            boolean isPatternAlreadyUsed = template.getPattern().expression().equals(saveReq.getPattern().expression());
            if (isPatternAlreadyUsed && !template.getId().equals(saveReq.getId())) {
                val ed = new ErrorDetail("pattern.expression", "pattern already mapped with another template", "pattern already mapped with another template. id: " + template.getId());
                errorDetails.add(ed);
            }
        }
        return errorDetails;
    }

    private List<ErrorDetail> assertDataFieldMappingNotEmpty(MsgTemplateSaveReq saveReq) {
        List<ErrorDetail> errorDetails = new LinkedList<>();
        if (saveReq.getDataFieldMapping().isEmpty()) {
            val ed = new ErrorDetail(null, "data field mapping should contain at least one data field mapping", null);
            errorDetails.add(ed);
        }

        Set<Integer> duplicatePosition = new HashSet<>();
        for (MsgDataField mdf : saveReq.getDataFieldMapping().keySet()) {
            val position = saveReq.getDataFieldMapping().get(mdf);
            if (duplicatePosition.contains(position)) {
                val ed = new ErrorDetail(null, "duplicate data field position found: " + position, null);
                errorDetails.add(ed);
                break;
            } else {
                duplicatePosition.add(position);
            }
        }
        return errorDetails;
    }

    private List<ErrorDetail> assertSampleDataIsNotEmpty(MsgTemplateSaveReq saveReq) {
        List<ErrorDetail> errorDetails = new LinkedList<>();
        if (saveReq.getSampleData().isEmpty()) {
            val ed = new ErrorDetail(null, "at least one sample data must be provided", null);
            errorDetails.add(ed);
        }
        if (!StringUtils.hasText(saveReq.getSampleData())) {
            val ed = new ErrorDetail(null, "one of the sample data is blank", null);
            errorDetails.add(ed);
        }
        return errorDetails;
    }

    /**
     * sample data must match the pattern
     * sample data must contain given number of data fields
     */
    private List<ErrorDetail> assertSampleDataShouldMatch(MsgTemplateSaveReq saveReq) {
        List<ErrorDetail> errorDetails = new LinkedList<>();
        String sampleData = saveReq.getSampleData();
        JPattern pattern = saveReq.getPattern();
        if (!pattern.hasMatch(sampleData)) {
            val ed = new ErrorDetail(null, "sample data does not match the pattern", null);
            errorDetails.add(ed);
            return errorDetails;
        }

        val dataSet = pattern.data(sampleData);
        // dataFieldMapping index must be accessible within data set
        for (MsgDataField mdf : saveReq.getDataFieldMapping().keySet()) {
            var index = saveReq.getDataFieldMapping().get(mdf);
            if (index >= dataSet.size()) {
                val ed = new ErrorDetail(mdf.toString(), "sample dataFieldMapping is not accessible with in data set. dataField:" + mdf, null);
                errorDetails.add(ed);
            }
        }


        // try if you can parse date
        Map<MsgDataField, String> map = new HashMap<>();
        for (MsgDataField field : saveReq.getDataFieldMapping().keySet()) {
            map.put(field, dataSet.get(saveReq.getDataFieldMapping().get(field)));
        }
        try {
            MsgParser.parseDateTime(map, saveReq.getDateFormat(), saveReq.getTimeFormat(), 17564625);
        } catch (Exception e) {
            val ed = new ErrorDetail("DateTime", "unable to parse date time", null);
            errorDetails.add(ed);
        }
        return errorDetails;
    }

    private List<ErrorDetail> assertFiChannelAndFiMsgIntent(MsgTemplateSaveReq saveReq) {
        List<ErrorDetail> errorDetails = new LinkedList<>();
        if (saveReq.getFiChannel() == null) {
            val ed = new ErrorDetail("fiChannel", "fiChannel is required", null);
            errorDetails.add(ed);
        }

        if (saveReq.getFiMsgIntent() == null) {
            val ed = new ErrorDetail("fiMsgIntent", "fiChannel is required", null);
            errorDetails.add(ed);
        }
        return errorDetails;
    }

    public void delete(String id) {
        this.templateRepo.deleteById(id);
        cacheClearService.clearCache();
    }
}
