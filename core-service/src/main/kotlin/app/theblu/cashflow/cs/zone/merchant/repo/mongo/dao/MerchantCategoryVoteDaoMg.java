package app.theblu.cashflow.cs.zone.merchant.repo.mongo.dao;

import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo.MerchantCategoryVoteDboMg;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantCategoryVoteDaoMg extends MongoRepository<MerchantCategoryVoteDboMg, String> {
    MerchantCategoryVoteDboMg save(@NotNull MerchantCategoryVoteDboMg dbo);
    Optional<MerchantCategoryVoteDboMg> findById(String id);

    long countByMerchantIdAndCategoryId(String merchantId, String categoryId);
}
