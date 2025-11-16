package app.theblu.cashflow.cs.zone.merchant.repo.mongo.impl;

import app.theblu.cashflow.cs.zone.merchant.model.Merchant;
import app.theblu.cashflow.cs.zone.merchant.repo.MerchantRepo;
import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dao.MerchantDaoMg;
import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo.MerchantDboMg;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantRepoMgImpl implements MerchantRepo {
    private final MerchantDaoMg merchantDao;

    public MerchantRepoMgImpl(MerchantDaoMg merchantDao) {
        this.merchantDao = merchantDao;
    }

    @Override
    public Merchant save(Merchant merchant) {
        var temp = this.merchantDao.save(MerchantDboMg.from(merchant));
        return temp.to();
    }

    @Override
    public Optional<Merchant> findByFid(String merchantId) {
        Optional<MerchantDboMg> optional = this.merchantDao.findByMerchantId(merchantId);
        return optional.map(MerchantDboMg::to);
    }
}
