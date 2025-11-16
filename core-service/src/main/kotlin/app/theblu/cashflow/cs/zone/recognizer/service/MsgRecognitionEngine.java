package app.theblu.cashflow.cs.zone.recognizer.service;

import app.theblu.cashflow.cs.domain.FirstRule;
import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.zone.bootstrap.service.FirstRuleService;
import app.theblu.cashflow.cs.domain.template.MsgTemplate;
import app.theblu.cashflow.cs.domain.org.FiMsgIntent;
import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.zone.org.service.msg.MsgTemplateService;
import app.theblu.cashflow.cs.zone.org.service.org.OrgIgnoreStringService;
import app.theblu.cashflow.cs.zone.org.service.org.OrgService;
import app.theblu.cashflow.cs.zone.recognizer.dto.MsgRecognitionRes;
import app.theblu.cashflow.cs.zone.recognizer.service.msgParser.MsgParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MsgRecognitionEngine {
    private final FirstRuleService firstRuleService;
    private final OrgService orgService;
    private final MsgTemplateService templateService;
    private final OrgIgnoreStringService orgIgnoreStringService;
    private final MsgTemplatePerformanceMonitor msgTemplatePerformanceMonitor;

//    private final Set<FiMsgIntent> allowedFiMsgIntents = Set.of(FiMsgIntent.DEBIT, FiMsgIntent.AUTO_DEBIT);
    private final Set<FiMsgIntent> allowedFiMsgIntents = Set.of(FiMsgIntent.DEBIT, FiMsgIntent.AUTO_DEBIT, FiMsgIntent.DEBIT_REVERSAL, FiMsgIntent.CREDIT);

    public MsgRecognitionEngine(FirstRuleService firstRuleService, OrgService orgService, MsgTemplateService templateService, OrgIgnoreStringService orgIgnoreStringService, MsgTemplatePerformanceMonitor msgTemplatePerformanceMonitor) {
        this.firstRuleService = firstRuleService;
        this.orgService = orgService;
        this.templateService = templateService;
        this.orgIgnoreStringService = orgIgnoreStringService;
        this.msgTemplatePerformanceMonitor = msgTemplatePerformanceMonitor;
    }

    public MsgRecognitionRes recognize(Msg msg) {
        MsgRecognitionRes res = validateByFirstRule(msg);
        if (res != null) return res;

        Optional<Org> orgOptional = this.orgService.findByHeader(msg.getHeader());
        res = validateByOrg(msg, orgOptional);
        if (res != null) return res;

        MsgTemplate template = findMsgTemplate(msg);
        if (template == null) {
            if (isInOrgIgnoreString(msg, orgOptional.get())) {
                log.info("[msgId:{}] REJECTED by OrgIgnoreString. body: {}", msg.getId(), msg.getBody());
                return MsgRecognitionRes.reject(msg);
            } else {
                return MsgRecognitionRes.unknown(msg);
            }
        }

        res = validateByMsgTemplate(template, msg);
        if (res != null) return res;

        res = MsgParser.parse(msg, template, orgOptional.get());
        return res;
    }

    private MsgRecognitionRes validateByFirstRule(Msg msg) {
        log.info("[msgId:{}] validating msg by first rule", msg.getId());

        FirstRule firstRule = firstRuleService.getFirstRule();
        var ir = firstRule.shouldIgnore(msg);
        if (ir != null) {
            log.info("[msgId:{}] REJECTED by firstRule. Reason: {} body: {}", msg.getId(), ir, msg.getBody());
            return MsgRecognitionRes.reject(msg);

        }
        return null;
    }

    private MsgRecognitionRes validateByOrg(Msg msg, Optional<Org> orgOptional) {
        log.info("[msgId:{}] validating msg by Org", msg.getId());
        if (orgOptional.isEmpty()) {
            log.info("[msgId:{}] UNKNOWN. no org found for given header {}", msg.getId(), msg.getHeader());
            return MsgRecognitionRes.unknown(msg);
        }
        Org org = orgOptional.get();

        if (org.isIgnore()) {
            log.info("[msgId:{}] REJECTED. Org is marked to ignore: {} orgId:{} orgName:{}", msg.getId(), msg.getHeader(), org.getId(), org.getName());
            return MsgRecognitionRes.reject(msg);
        }

        if (org.isPromotionalHeader(msg.getHeader())) {
            log.info("[msgId:{}] REJECTED. Promotional header: {}", msg.getId(), msg.getHeader());
            return MsgRecognitionRes.reject(msg);
        }
        if (!org.isBanking()) {
            log.info("[msgId:{}] REJECTED. Not a banking header: {}", msg.getId(), msg.getHeader());
            return MsgRecognitionRes.reject(msg);
        }
        return null;
    }

    private MsgTemplate findMsgTemplate(Msg msg) {
        List<MsgTemplate> templates = this.templateService.getByHeader(msg.getHeader());
        if (templates.isEmpty()) {
            log.info("[msgId:{}] UNKNOWN No templates found for given header: {}", msg.getId(), msg.getHeader());
            return null;
        }
        log.info("[msgId:{}] found {} msg templates for header: {}", msg.getId(), templates.size(), msg.getHeader());
        for (MsgTemplate msgTemplate : templates) {
            var start = System.currentTimeMillis();
            var hasMatch = msgTemplate.hasMatch(msg);
            var end = System.currentTimeMillis();
            if (end - start > 2) {
                log.warn("[{}] Template took too much time for hasMatch call. TemplateID {}, msg:{}", end - start, msgTemplate.getId(), msg);
            }
            msgTemplatePerformanceMonitor.count(msgTemplate, end - start);
            if (hasMatch) {
                log.info("[msgId:{}] found msg template that can process given msg templateId: {}", msg.getId(), msgTemplate.getId());
                return msgTemplate;
            }
        }
        return null;
    }

    private Boolean isInOrgIgnoreString(Msg msg, Org org) {
        var ois = this.orgIgnoreStringService.getIgnoreStringList(msg.getHeader());
        if (ois.isEmpty()) return false;

        for (String ignoredString : ois.get().getStringSet()) {
            if (msg.getBody().toLowerCase().contains(ignoredString.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    private MsgRecognitionRes validateByMsgTemplate(MsgTemplate template, Msg msg) {
        if (template.isIgnoreTemplate()) {
            log.info("[msgId:{}] REJECTED. Msg template is marked to ignore. templateId: {}", msg.getId(), template.getId());
            return MsgRecognitionRes.reject(msg);
        }

        // TODO Enable after testing
        FiMsgIntent fiMsgIntent = template.getFiMsgIntent();
        if (!allowedFiMsgIntents.contains(fiMsgIntent)) {
            log.info("[msgId:{}] REJECTED. msg type is not allowed. msg type: {}", msg.getId(), fiMsgIntent);
            return MsgRecognitionRes.reject(msg);
        }
        return null;
    }
}
