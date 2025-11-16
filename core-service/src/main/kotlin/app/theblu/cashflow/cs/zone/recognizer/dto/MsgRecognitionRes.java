package app.theblu.cashflow.cs.zone.recognizer.dto;

import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.domain.org.FiMsgIntent;
import app.theblu.cashflow.cs.domain.org.FiChannel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MsgRecognitionRes {
    public enum Status {
        RECOGNIZED, REJECT, UNKNOWN,
    }
    private String id;
    private Status status;
    private double amount;
    private long timestamp;

    private String currency;
    private FiMsgIntent fiMsgIntent;
    private FiChannel fiChannel;
    private String orgShortName;

    private String myAccountId;
    private String merchantId;
    private String referenceId;


    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public static MsgRecognitionRes reject(Msg msg) {
        MsgRecognitionRes recognition = new MsgRecognitionRes();
        recognition.setId(msg.getId());
        recognition.status = Status.REJECT;
        return recognition;
    }

    public static MsgRecognitionRes unknown(Msg msg) {
        MsgRecognitionRes recognition = new MsgRecognitionRes();
        recognition.setId(msg.getId());
        recognition.status = Status.UNKNOWN;
        return recognition;
    }
}
