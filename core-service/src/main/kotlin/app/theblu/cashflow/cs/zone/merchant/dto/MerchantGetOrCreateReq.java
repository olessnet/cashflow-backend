package app.theblu.cashflow.cs.zone.merchant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantGetOrCreateReq {
    @NotBlank(message = "you must specify merchantId")
    private String merchantId;
}
