package app.theblu.cashflow.cs.zone.analyzer.service;

import app.theblu.cashflow.cs.batteries.common.JsonUtil;
import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.zone.analyzer.client.ElasticSearchNativeClient;
import app.theblu.cashflow.cs.zone.analyzer.dto.AggregateHeaderGroupReq;
import app.theblu.cashflow.cs.zone.analyzer.dto.SearchUnknownMsgHeaderReq;
import app.theblu.cashflow.cs.zone.org.dto.OrgIgnoreStringAddOrRemoveReq;
import app.theblu.cashflow.cs.zone.org.service.org.OrgIgnoreStringService;
import com.google.gson.JsonElement;
import jakarta.json.JsonObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class UnknownMessageAnalyzerService {
    private final ElasticSearchNativeClient elasticClient;
    private final OrgIgnoreStringService orgIgnoreStringService;

    public UnknownMessageAnalyzerService(ElasticSearchNativeClient elasticClient, OrgIgnoreStringService orgIgnoreStringService) {
        this.elasticClient = elasticClient;
        this.orgIgnoreStringService = orgIgnoreStringService;
    }

    public List getHeaderGroup(AggregateHeaderGroupReq req) {
        return this.elasticClient.aggregateMsgByHeader(req);
    }

    public List<Msg> filterMsgByHeader(SearchUnknownMsgHeaderReq req) {
        var list = this.elasticClient.filterMsgByHeader(req);
        List<Msg> msgList = new LinkedList<>();
        for (var item : list) {
            var jsonStr = JsonUtil.INSTANCE.toJson(item);
            var jsonObj = JsonUtil.INSTANCE.fromJson(jsonStr, JsonElement.class);
            var msgJ = jsonObj.getAsJsonObject().getAsJsonObject("msg");
            var msg = JsonUtil.INSTANCE.fromJson(JsonUtil.INSTANCE.toJson(msgJ), Msg.class);
            msgList.add(msg);
        }

        return msgList;
    }

    @SneakyThrows
    public void deleteMsgByHeader(String header) {
        this.elasticClient.deleteMsgByHeader(header);
        var sreq = new SearchUnknownMsgHeaderReq(false, header);
        var list = this.filterMsgByHeader(sreq);
        while (!list.isEmpty()) {
            for (var item : list) {
                this.deleteMsgById(item);
            }
            list = this.filterMsgByHeader(sreq);
        }
        log.info("delete msg by header: {}", header);
    }

    @SneakyThrows
    public void deleteMsgById(Msg msg) {
        log.info("deleteing message {}", msg);
        this.elasticClient.deleteMsgById(msg.getId());
    }

    @SneakyThrows
    public void adIgnoreString(OrgIgnoreStringAddOrRemoveReq req) {
        // TODO Optimize this logic to search and delete with in elastic search
        this.orgIgnoreStringService.addIgnoreString(req.getHeader(), req.getIgnoreString());
        for (String is : req.getIgnoreString()) {
            var sreq = new SearchUnknownMsgHeaderReq();
            sreq.setHeader(req.getHeader());
            sreq.setPossibleTransaction(false);
            List<Msg> msgList = this.filterMsgByHeader(sreq);
            do {
                for (var item : msgList) {
                    if (item.getBody().contains(is)) {
                        log.info("deleteing message {}", item);
                        this.elasticClient.deleteMsgById(item.getId());
                    }
                }
                msgList = this.filterMsgByHeader(sreq);
            } while (msgList.isEmpty());
        }
    }
}
