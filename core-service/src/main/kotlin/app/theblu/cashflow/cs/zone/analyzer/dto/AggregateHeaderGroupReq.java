package app.theblu.cashflow.cs.zone.analyzer.dto;

import lombok.Data;

@Data
public class AggregateHeaderGroupReq {
    private boolean possibleTransaction;
    private boolean headerVerified;
    private boolean bankingHeader;
}
