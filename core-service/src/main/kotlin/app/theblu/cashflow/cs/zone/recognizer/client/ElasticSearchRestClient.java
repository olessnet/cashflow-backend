package app.theblu.cashflow.cs.zone.recognizer.client;

import app.theblu.cashflow.cs.zone.recognizer.dto.ExceptionMsgLog;
import app.theblu.cashflow.cs.zone.recognizer.dto.UnknownMsgLog;
import org.springframework.web.client.RestClient;

public class ElasticSearchRestClient {
    private final RestClient client;

    public ElasticSearchRestClient(String baseUrl) {
        this.client = RestClient.create(baseUrl);
    }

    public void logUnknownMsg(UnknownMsgLog log) {
        this.client.put().uri("/msg-unknown/_doc/{hash}", log.getMsg().hash())
                .body(log)
                .retrieve()
                .body(String.class);
    }

    public void logMsgCausingException(ExceptionMsgLog log) {
        this.client.put().uri("/msg-exception/_doc/{hash}", log.getMsg().hash()).body(log).retrieve().body(String.class);
    }
}
