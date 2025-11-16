package app.theblu.cashflow.cs.zone.analyzer.service;

import app.theblu.cashflow.cs.batteries.common.ThreadUtil;
import app.theblu.cashflow.cs.batteries.rocket.exception.BusinessValidationException;
import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.zone.analyzer.client.ElasticSearchNativeClient;
import app.theblu.cashflow.cs.zone.analyzer.dto.MsgTemplateCreateReq;
import app.theblu.cashflow.cs.zone.org.dto.MsgTemplateSaveReq;
import app.theblu.cashflow.cs.zone.org.service.msg.MsgTemplateService;
import app.theblu.cashflow.cs.zone.org.service.org.OrgService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MsgTemplateCreatorService {
    private final OrgService orgService;
    private final MsgTemplateService msgTemplateService;
    private final ElasticSearchNativeClient elasticSearchNativeClient;

    public MsgTemplateCreatorService(OrgService orgService, MsgTemplateService msgTemplateService, ElasticSearchNativeClient elasticSearchNativeClient) {
        this.orgService = orgService;
        this.msgTemplateService = msgTemplateService;
        this.elasticSearchNativeClient = elasticSearchNativeClient;
    }

    public void createTemplate(MsgTemplateCreateReq req) {
        var orgOptional = this.orgService.findByHeader(req.getHeader());
        validate(req, orgOptional);
        MsgTemplateSaveReq saveReq = req.toSaveReq();
        saveReq.setOrgId(orgOptional.get().getId());
        msgTemplateService.save(saveReq);
//        ThreadUtil.INSTANCE.sleep(3000);
        elasticSearchNativeClient.deleteUnknownMsg(req.getHeader(), req.getPatternExpression());
    }

    private void validate(MsgTemplateCreateReq req, Optional<Org> orgOptional) {
        if (orgOptional.isEmpty()) {
            throw BusinessValidationException.Companion.withError("header", "no org found for the given header", null);
        }
    }
}
