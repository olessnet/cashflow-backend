package app.theblu.cashflow.cs.zone.merchant.repo.mongo.impl;

import app.theblu.cashflow.cs.batteries.rocket.exception.NotYetImplementedException;
import app.theblu.cashflow.cs.zone.merchant.repo.MerchantVoteRepo;
import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dao.MerchantCategoryVoteDaoMg;
import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dao.MerchantNameVoteDaoMg;
import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo.MerchantCategoryVoteDboMg;
import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo.MerchantNameVoteDboMg;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MerchantVoteRepoMgImpl implements MerchantVoteRepo {
    private final MerchantCategoryVoteDaoMg merchantCategoryVoteDao;
    private final MerchantNameVoteDaoMg merchantNameVoteDao;

    public MerchantVoteRepoMgImpl(MerchantCategoryVoteDaoMg merchantCategoryVoteDao, MerchantNameVoteDaoMg merchantNameVoteDao) {
        this.merchantCategoryVoteDao = merchantCategoryVoteDao;
        this.merchantNameVoteDao = merchantNameVoteDao;
    }

    @Override
    public boolean containsNameVote(String name) {
        throw new NotYetImplementedException();
    }

    @Override
    public void removeName(String name) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean addNameVote(String merchantId, String userId, String name) {
        MerchantNameVoteDboMg newDbo = new MerchantNameVoteDboMg(merchantId, userId, name);

        // TODO remove this blocking code when messaging queue is implemented
        synchronized (newDbo.getId()) {
            Optional<MerchantNameVoteDboMg> dboOptional = this.merchantNameVoteDao.findById(newDbo.id());
            if (dboOptional.isPresent()) {
                return false;
            } else {
                this.merchantNameVoteDao.save(newDbo);
                return true;
            }
        }
    }

    @Override
    public Long getVotesForName(String merchantId, String name) {
        return this.merchantNameVoteDao.countByMerchantIdAndName(merchantId, name);
    }

    @Override
    public Map<String, Long> getAllNamesAndVotes(String merchantId) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean addCategoryVote(String merchantId, String userId, String categoryId) {
        MerchantCategoryVoteDboMg newDbo = new MerchantCategoryVoteDboMg(merchantId, userId, categoryId);

        // TODO remove this blocking code when messaging queue is implemented
        synchronized (newDbo.getId()) {
            Optional<MerchantCategoryVoteDboMg> dboOptional = this.merchantCategoryVoteDao.findById(newDbo.id());
            if (dboOptional.isPresent()) {
                return false;
            } else {
                this.merchantCategoryVoteDao.save(newDbo);
                return true;
            }
        }
    }

    @Override
    public Long getVotesForCategory(String merchantId, String categoryId) {
        return this.merchantCategoryVoteDao.countByMerchantIdAndCategoryId(merchantId, categoryId);
    }

    @Override
    public Map<Integer, Long> getAllCategoryAndVotes(String merchantId) {
        throw new NotYetImplementedException();
    }

    @Override
    public Optional<MerchantNameVoteDboMg> findNameByMerchantIdAndUserId(String merchantId, String userId) {
        return this.merchantNameVoteDao.findByMerchantIdAndUserId(merchantId, userId);
    }

    @Override
    public void removeVoteName(MerchantNameVoteDboMg merchantNameVoteDboMg) {
        this.merchantNameVoteDao.deleteById(merchantNameVoteDboMg.id());
    }
}
