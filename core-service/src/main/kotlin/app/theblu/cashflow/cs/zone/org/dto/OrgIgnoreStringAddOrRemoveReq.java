package app.theblu.cashflow.cs.zone.org.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class OrgIgnoreStringAddOrRemoveReq {
    @NotBlank
    private String header;
    private List<String> ignoreString;
}
