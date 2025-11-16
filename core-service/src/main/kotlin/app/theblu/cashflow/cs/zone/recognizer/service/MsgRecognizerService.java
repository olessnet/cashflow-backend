package app.theblu.cashflow.cs.zone.recognizer.service;

import app.theblu.cashflow.cs.zone.merchant.service.MerchantService;
import app.theblu.cashflow.cs.zone.recognizer.client.ElasticSearchRestClient;
import app.theblu.cashflow.cs.zone.recognizer.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MsgRecognizerService {
    private final MsgRecognitionEngine msgRecognitionEngine;
    private final ElasticSearchRestClient elasticClient;
    private final MerchantService merchantService;
    private final MsgLogger msgLogger;

    public MsgRecognizerService(MsgRecognitionEngine msgRecognitionEngine, ElasticSearchRestClient elasticClient, MerchantService merchantService, MsgLogger msgLogger) {
        this.msgRecognitionEngine = msgRecognitionEngine;
        this.elasticClient = elasticClient;
        this.merchantService = merchantService;
        this.msgLogger = msgLogger;
    }

    public MsgRecognitionRes recognize(MsgRecognitionReq req) {
        try {
            MsgRecognitionRes res = this.msgRecognitionEngine.recognize(req.getMsg());
            if (res.getStatus() == MsgRecognitionRes.Status.RECOGNIZED) {
                if (res.getMerchantId() != null && !res.getMerchantId().isEmpty()) {
                    merchantService.findByIdOrCreate(res.getMerchantId());
                }
            } else if (res.getStatus() == MsgRecognitionRes.Status.UNKNOWN) {
                msgLogger.logUnknownMsg(req.getMsg());
            }
            return res;
        } catch (Exception exception) {
            this.logExceptionCausingMsg(req, exception);
            throw exception;
        }
    }

    @Async
    protected void logExceptionCausingMsg(MsgRecognitionReq req, Exception exception) {
        try {
            ExceptionMsgLog exceptionMsgLog = new ExceptionMsgLog(req.getMsg(), exception);
            this.elasticClient.logMsgCausingException(exceptionMsgLog);
        } catch (Throwable t) {
            log.warn("unable to update exception msg log", t);
        }
    }
}
