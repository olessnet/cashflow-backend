package app.theblu.cashflow.cs.zone.org.web.msg;

import app.theblu.cashflow.cs.batteries.common.JPattern;
import app.theblu.cashflow.cs.zone.org.dto.MsgTemplatePositionalDataReq;
import app.theblu.cashflow.cs.zone.org.dto.MsgTemplateTestReq;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/msg-template-tester")
public class MsgTemplateTestCtrl {

    @PostMapping("/positional-data")
    public ResponseEntity<Object> getPositionalData(@Valid @RequestBody MsgTemplatePositionalDataReq req) {
        JPattern pattern = new JPattern(req.getExpression(), req.isCaseSensitive(), req.isFullTextSearch());
        String sample = req.getSampleData();
        var data = pattern.data(sample);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/test")
    public ResponseEntity<Object> testTemplate(@Valid @RequestBody MsgTemplateTestReq req) {


        return ResponseEntity.ok().body(req);
    }
}
