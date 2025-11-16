package app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "merchant_category_vote")
@CompoundIndexes({@CompoundIndex(name = "compound_index", def = "{'merchantId' : 1, 'userId' : 1, 'categoryId' : 1}")})
public class MerchantCategoryVoteDboMg {
    public MerchantCategoryVoteDboMg(String merchantId, String userId, String categoryId) {
        this.merchantId = merchantId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.id = id();
    }

    @Id
    private String id;
    private String merchantId;
    private String userId;
    private String categoryId;

    public String id() {return merchantId + ":" + userId + ":" + categoryId;}
}
