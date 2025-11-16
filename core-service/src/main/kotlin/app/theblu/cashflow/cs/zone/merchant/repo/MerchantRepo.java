package app.theblu.cashflow.cs.zone.merchant.repo;

import app.theblu.cashflow.cs.zone.merchant.model.Merchant;

import java.util.Optional;

public interface MerchantRepo {
    Merchant save(Merchant merchant);
    Optional<Merchant> findByFid(String merchantId);
}
