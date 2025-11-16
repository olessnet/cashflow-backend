package app.theblu.cashflow.cs.zone.merchant.repo;

import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo.MerchantNameVoteDboMg;

import java.util.Map;
import java.util.Optional;

public interface MerchantVoteRepo {
    boolean containsNameVote(String name);
    void removeName(String name);
    boolean addNameVote(String merchantId, String userId, String name);
    Long getVotesForName(String merchantId, String name);
    Map<String, Long> getAllNamesAndVotes(String merchantId);

    boolean addCategoryVote(String merchantId, String userId, String categoryId);
    Long getVotesForCategory(String merchantId, String categoryId);
    Map<Integer, Long> getAllCategoryAndVotes(String merchantId);

    Optional<MerchantNameVoteDboMg> findNameByMerchantIdAndUserId(String merchantId, String userId);

    void removeVoteName(MerchantNameVoteDboMg merchantNameVoteDboMg);
}

