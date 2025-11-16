package app.theblu.cashflow.cs.zone.merchant.repo.mongo.dao;

import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo.MerchantNameVoteDboMg;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantNameVoteDaoMg extends MongoRepository<MerchantNameVoteDboMg, String> {
    MerchantNameVoteDboMg save(@NotNull MerchantNameVoteDboMg dbo);
    Optional<MerchantNameVoteDboMg> findById(String id);
    Optional<MerchantNameVoteDboMg> findByMerchantIdAndUserId(String merchantId, String userId);
    long countByMerchantIdAndName(String merchantId, String name);
}
