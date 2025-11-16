package app.theblu.cashflow.cs.zone.merchant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantNameVoteReq {
    @NotBlank(message = "you must specify merchantId")
    private String merchantId;
    private String userId;
    @NotBlank(message = "you must specify name")
    private String name;
}
