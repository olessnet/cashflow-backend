package app.theblu.cashflow.cs.zone.analyzer.web;

import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder;
import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.zone.analyzer.service.UnknownMessageAnalyzerService;
import app.theblu.cashflow.cs.zone.analyzer.dto.AggregateHeaderGroupReq;
import app.theblu.cashflow.cs.zone.analyzer.dto.SearchUnknownMsgHeaderReq;
import app.theblu.cashflow.cs.zone.org.dto.OrgIgnoreStringAddOrRemoveReq;
import app.theblu.cashflow.cs.zone.org.service.org.OrgIgnoreStringService;
import app.theblu.cashflow.cs.zone.recognizer.dto.MsgRecognitionReq;
import app.theblu.cashflow.cs.zone.recognizer.dto.MsgRecognitionRes;
import app.theblu.cashflow.cs.zone.recognizer.service.CacheClearService;
import app.theblu.cashflow.cs.zone.recognizer.service.MsgRecognizerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/v1/unknown-message-analyzer")
@Slf4j
public class UnknownMsgAnalyzerCtrl {
    private final UnknownMessageAnalyzerService service;
    private final MsgRecognizerService msgRecognizerService;
    private final CacheClearService cacheClearService;

    public UnknownMsgAnalyzerCtrl(UnknownMessageAnalyzerService service, MsgRecognizerService msgRecognizerService, CacheClearService cacheClearService) {
        this.service = service;
        this.msgRecognizerService = msgRecognizerService;
        this.cacheClearService = cacheClearService;
    }

    @PostMapping("/headers")
    public ResponseEntity<Object> getHeaderGroup(@Valid @RequestBody AggregateHeaderGroupReq req) {
        val list = this.service.getHeaderGroup(req);
        return ApiResBuilder.INSTANCE.buildRes(list);
    }

    @PostMapping("/filter-msg")
    public ResponseEntity<Object> filterMsgByHeader(@Valid @RequestBody SearchUnknownMsgHeaderReq req) {
        val list = this.service.filterMsgByHeader(req);
        List<Msg> msgList = new LinkedList<>();
        for (Msg msg : list) {
            var mreq = new MsgRecognitionReq();
            mreq.setMsg(msg);
            var res = this.msgRecognizerService.recognize(mreq);
            if (res.getStatus() == MsgRecognitionRes.Status.UNKNOWN) {
                msgList.add(msg);
            } else {
                this.service.deleteMsgById(msg);
            }
        }
        msgList = msgList.stream().sorted().toList();
        msgList = new LinkedList<>(msgList);
        Collections.reverse(msgList);
        return ApiResBuilder.INSTANCE.buildRes(msgList);
    }

    @DeleteMapping("/delete-all-msg-by-header/{header}")
    public void deleteMsgByHeader(@PathVariable String header) {
        this.service.deleteMsgByHeader(header);
    }

    @GetMapping("/print-all-cache-details")
    public ResponseEntity<Object> printAllCacheDetails() {
        var data = cacheClearService.printCache();
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @PostMapping("/addIgnoreStrings")
    public void addIgnoreString(@Valid @RequestBody OrgIgnoreStringAddOrRemoveReq req) {
        this.service.adIgnoreString(req);
    }
}
