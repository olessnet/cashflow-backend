//package app.theblu.cashflow.cs.zone.org.service;
//
//import app.theblu.cashflow.cs.module.common.JPattern;
//import app.theblu.cashflow.cs.module.rocket.exception.BusinessValidationException;
//import app.theblu.cashflow.cs.module.rocket.res.ErrorDetail;
//import app.theblu.cashflow.cs.domain.template.MsgDataField;
//import app.theblu.cashflow.cs.zone.org.repo.MsgTemplateRepo;
//import app.theblu.cashflow.cs.zone.org.web.dto.MsgTemplateTestReq;
//import lombok.val;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class MsgTemplateTestService {
//    private final MsgTemplateRepo templateRepo;
//
//    public MsgTemplateTestService(MsgTemplateRepo templateRepo) {
//        this.templateRepo = templateRepo;
//    }
//
//    public Map<Integer, String> getPositionalData(MsgTemplateTestReq req) {
//        List<ErrorDetail> errorDetails = new LinkedList<>();
//        errorDetails.addAll(assertDataFieldMappingNotEmpty(req));
//        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);
//
//        JPattern pattern = req.getPattern();
//        return pattern.positionalData(req.getSampleData());
//    }
//
//
//    public Object getDataFields(MsgTemplateTestReq req) {
//        List<ErrorDetail> errorDetails = new LinkedList<>();
//        errorDetails.addAll(assertDataFieldMappingNotEmpty(req));
//        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);
//
//        MsgPattern pattern = req.getPattern();
//        return pattern.dataFields(req.getSampleData(), req.getDataFieldMapping());
//    }
//
//    private List<ErrorDetail> assertDataFieldMappingNotEmpty(MsgTemplateTestReq req) {
//        List<ErrorDetail> errorDetails = new LinkedList<>();
//        if (req.getDataFieldMapping().isEmpty()) {
//            val ed = new ErrorDetail("data field mapping should contain at least one data field mapping", null, null);
//            errorDetails.add(ed);
//        }
//
//        Set<Integer> duplicatePosition = new HashSet<>();
//        for (MsgDataField mdf : req.getDataFieldMapping().keySet()) {
//            val position = req.getDataFieldMapping().get(mdf);
//            if (duplicatePosition.contains(position)) {
//                val ed = new ErrorDetail("duplicate data field position found: " + position, null, null);
//                errorDetails.add(ed);
//                break;
//            } else {
//                duplicatePosition.add(position);
//            }
//        }
//        return errorDetails;
//    }
//}
