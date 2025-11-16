package app.theblu.cashflow.cs.zone.org.dto;

import app.theblu.cashflow.cs.batteries.common.JPattern;
import app.theblu.cashflow.cs.domain.template.MsgDataField;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class MsgTemplateTestReq {
    @NotNull(message = "you must specify pattern details")
    @Valid
    private JPattern pattern;
    @NotNull(message = "data field mapping should not be null")
    private Map<MsgDataField, Integer> dataFieldMapping;
    private String dateFormat;
    private String timeFormat;
    @NotBlank(message = "sample data should not be blank or null")
    private String sampleData;
}
