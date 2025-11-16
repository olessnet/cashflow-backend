package app.theblu.cashflow.cs.zone.recognizer.dto;

import app.theblu.cashflow.cs.domain.Msg;
import lombok.Data;


@Data
public class UnknownMsgLog {
    private Msg msg;
    private boolean isBankingHeader;
    private boolean isHeaderVerified;
    private boolean possibleTransaction;
}