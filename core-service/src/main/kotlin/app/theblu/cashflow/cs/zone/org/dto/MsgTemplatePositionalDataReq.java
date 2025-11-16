package app.theblu.cashflow.cs.zone.org.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MsgTemplatePositionalDataReq {
    @NotBlank(message = "you must specify expression")
    private String expression;
    private boolean caseSensitive;
    private boolean fullTextSearch;
    @NotBlank(message = "you must specify sampleData")
    private String sampleData;
}
