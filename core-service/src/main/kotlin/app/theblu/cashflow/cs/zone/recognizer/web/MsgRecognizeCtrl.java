package app.theblu.cashflow.cs.zone.recognizer.web;

import app.theblu.cashflow.cs.batteries.rocket.exception.StaticValidationException;
import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder;
import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.zone.recognizer.dto.MsgRecognitionBatchReq;
import app.theblu.cashflow.cs.zone.recognizer.dto.MsgRecognitionReq;
import app.theblu.cashflow.cs.zone.recognizer.dto.MsgRecognitionRes;
import app.theblu.cashflow.cs.zone.recognizer.service.MsgRecognizerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/v1/msg/recognize")
@Slf4j
public class MsgRecognizeCtrl {
    private final MsgRecognizerService service;

    public MsgRecognizeCtrl(MsgRecognizerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> recognize(@Valid @RequestBody MsgRecognitionReq req) {
        req.getMsg().sanitize();
        MsgRecognitionRes res = service.recognize(req);
        return ApiResBuilder.INSTANCE.buildRes(res);
    }

    @PostMapping("/batch")
    public ResponseEntity<Object> recognizeBatch(@Valid @RequestBody MsgRecognitionBatchReq batchReq) {
        var batchSize = 10000;
        if (batchReq.getReqBatch().size() > batchSize) {
            throw StaticValidationException.Companion.withError("reqBatch", "maximum of " + batchSize + " requests in a batch is allowed. received size: " + batchReq.getReqBatch().size(), null);
        }

        List<MsgRecognitionRes> list = new LinkedList<>();
        var count = 0;
        for (Msg msg : batchReq.getReqBatch()) {
            msg.sanitize();
            var req = new MsgRecognitionReq();
            req.setMsg(msg);
            var start = System.currentTimeMillis();
            MsgRecognitionRes res = service.recognize(req);
            var end = System.currentTimeMillis();
            if (end - start > 100) {
                count++;
                log.info("[{}] Msg took too long to recognize: {}", end - start, msg);
                log.info("REG STATUS: {}", res.getStatus());
            }
            list.add(res);
        }

        var size = list.stream().filter(reg -> reg.getStatus() == MsgRecognitionRes.Status.UNKNOWN).toList().size();
        log.info("Unknown Messages {}/{}", size, list.size());
        log.info("Long Msg {}", count);
        return ApiResBuilder.INSTANCE.buildRes(list);
    }
}
