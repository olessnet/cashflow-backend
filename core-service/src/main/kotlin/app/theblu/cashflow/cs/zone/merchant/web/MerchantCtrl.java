package app.theblu.cashflow.cs.zone.merchant.web;

import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder;
import app.theblu.cashflow.cs.zone.merchant.model.Merchant;
import app.theblu.cashflow.cs.zone.merchant.service.MerchantService;
import app.theblu.cashflow.cs.zone.merchant.dto.MerchantCategoryVoteReq;
import app.theblu.cashflow.cs.zone.merchant.dto.MerchantGetOrCreateReq;
import app.theblu.cashflow.cs.zone.merchant.dto.MerchantNameVoteReq;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/merchant")
public class MerchantCtrl {
    private final MerchantService merchantService;

    public MerchantCtrl(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping("get-or-create")
    public ResponseEntity<Object> getOrCreate(@RequestBody @Valid MerchantGetOrCreateReq req) {
        Merchant merchant = this.merchantService.findByIdOrCreate(req.getMerchantId());
        return ApiResBuilder.INSTANCE.buildRes(merchant);
    }

    @PostMapping("vote-on-category")
    public void voteOnCategory(@RequestBody @Valid MerchantCategoryVoteReq req) {
        req.setUserId("user1");
        this.merchantService.voteOnCategory(req);
    }

    @PostMapping("vote-on-name")
    public void voteOnName(@RequestBody @Valid MerchantNameVoteReq req) {
        req.setUserId("user1");
        this.merchantService.voteOnName(req);
    }
}
