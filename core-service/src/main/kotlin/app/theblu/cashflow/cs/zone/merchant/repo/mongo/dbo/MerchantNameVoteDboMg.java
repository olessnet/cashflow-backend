package app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "merchant_name_vote")
public class MerchantNameVoteDboMg {
    public MerchantNameVoteDboMg(String merchantId, String userId, String name) {
        this.merchantId = merchantId;
        this.userId = userId;
        this.name = name;
        this.id = id();
    }

    @Id
    private String id;
    private String merchantId;
    private String userId;
    private String name;

    public String id() {
        return merchantId + ":" + userId + ":" + name;
    }
}
