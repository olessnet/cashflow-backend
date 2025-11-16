package app.theblu.cashflow.cs.zone.recognizer.service;

import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.zone.org.repo.OrgRepo;
import app.theblu.cashflow.cs.zone.recognizer.client.ElasticSearchRestClient;
import app.theblu.cashflow.cs.zone.recognizer.dto.UnknownMsgLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MsgLogger {
    private final OrgRepo orgRepo;
    private final ElasticSearchRestClient elasticClient;


    public MsgLogger(OrgRepo orgRepo, ElasticSearchRestClient elasticClient) {
        this.orgRepo = orgRepo;
        this.elasticClient = elasticClient;
    }

    @Async
    protected void logUnknownMsg(Msg msg) {
        try {
            UnknownMsgLog msgLog = new UnknownMsgLog();
            msgLog.setMsg(msg);
            msgLog.setPossibleTransaction(isPossibleTransaction(msg.get_lowerCaseBody()));
            Optional<Org> orgOp = orgRepo.findByHeader(msg.getHeader());
            if (orgOp.isPresent()) {
                msgLog.setBankingHeader(orgOp.get().isBanking());
                msgLog.setHeaderVerified(orgOp.get().isVerified());
            }
            elasticClient.logUnknownMsg(msgLog);
//            log.info("Unknown msg logged");
        } catch (Throwable t) {
            log.warn("unable to update unknown msg log", t);
        }
    }


    // check if msg contains any inr pattern
    // will be useful when analyzing unknown messages from back office app
    private boolean isPossibleTransaction(String text) {
        if (text.toLowerCase().contains("inr")) return true;
        if (text.toLowerCase().contains("rs")) return true;
        return false;
    }
}
