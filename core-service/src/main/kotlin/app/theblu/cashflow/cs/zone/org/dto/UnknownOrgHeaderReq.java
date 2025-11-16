package app.theblu.cashflow.cs.zone.org.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UnknownOrgHeaderReq {
    @NotNull
    private List<String> headers;
}
