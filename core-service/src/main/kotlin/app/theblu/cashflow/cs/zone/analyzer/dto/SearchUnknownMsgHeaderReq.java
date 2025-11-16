package app.theblu.cashflow.cs.zone.analyzer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SearchUnknownMsgHeaderReq {
    private boolean possibleTransaction;

    @NotBlank
    @Size(max = 12, min = 3)
    private String header;

    public SearchUnknownMsgHeaderReq() {}

    public SearchUnknownMsgHeaderReq(boolean possibleTransaction, String header) {
        this.possibleTransaction = possibleTransaction;
        this.header = header;
    }
}
