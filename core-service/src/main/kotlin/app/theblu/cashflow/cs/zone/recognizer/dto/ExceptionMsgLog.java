package app.theblu.cashflow.cs.zone.recognizer.dto;

import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.batteries.common.ExceptionUtil;
import lombok.Data;

import java.util.List;

@Data
public class ExceptionMsgLog {
    private Msg msg;
    private List<String> trace;

    public ExceptionMsgLog(Msg msg, Throwable throwable) {
        this.msg = msg;
        this.trace = ExceptionUtil.INSTANCE.formatToPrettyString(throwable);
    }
}
