package app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo;

import app.theblu.cashflow.cs.zone.merchant.model.Merchant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "merchant")
public class MerchantDboMg {
    @Id
    private String merchantId;
    private String preferredName;
    private String preferredCategory;
    private Map<String, Long> suggestedNames = new HashMap<>();
    private Map<String, Long> suggestedCategories = new HashMap<>();

    public static MerchantDboMg from(Merchant other) {
        MerchantDboMg self = new MerchantDboMg();
        self.setMerchantId(other.getMerchantId());
        self.setPreferredName(other.getPreferredName());
        self.setPreferredCategory(other.getPreferredCategory());
        self.suggestedNames.putAll(other.getSuggestedNames());
        self.suggestedCategories.putAll(other.getSuggestedCategories());
        return self;
    }

    public Merchant to() {
        Merchant self = new Merchant(
                this.merchantId,
                this.preferredName,
                this.suggestedNames,
                this.preferredCategory,
                this.suggestedCategories
        );
        return self;
    }
}
