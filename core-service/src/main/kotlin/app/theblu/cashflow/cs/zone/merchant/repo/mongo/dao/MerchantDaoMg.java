package app.theblu.cashflow.cs.zone.merchant.repo.mongo.dao;

import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo.MerchantDboMg;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantDaoMg extends MongoRepository<MerchantDboMg, String> {
    MerchantDboMg save(@NotNull MerchantDboMg merchantDbo);
    Optional<MerchantDboMg> findByMerchantId(String merchantId);
}
