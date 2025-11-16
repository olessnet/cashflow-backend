package app.theblu.cashflow.cs.zone.analyzer.web;

import app.theblu.cashflow.cs.zone.analyzer.dto.MsgTemplateCreateReq;
import app.theblu.cashflow.cs.zone.analyzer.service.MsgTemplateCreatorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/message-template-creator")
public class MsgTemplateCreatorCtrl {
    private final MsgTemplateCreatorService msgTemplateCreatorService;

    public MsgTemplateCreatorCtrl(MsgTemplateCreatorService msgTemplateCreatorService) {
        this.msgTemplateCreatorService = msgTemplateCreatorService;
    }

    @PostMapping
    private void createTemplate(@RequestBody @Valid MsgTemplateCreateReq req) {
        this.msgTemplateCreatorService.createTemplate(req);
    }
}
