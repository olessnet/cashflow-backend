package app.theblu.cashflow.cs.zone.merchant.service;

import app.theblu.cashflow.cs.domain.Category;
import app.theblu.cashflow.cs.batteries.rocket.exception.BusinessValidationException;
import app.theblu.cashflow.cs.batteries.rocket.res.ErrorDetail;
import app.theblu.cashflow.cs.zone.bootstrap.service.CategoryService;
import app.theblu.cashflow.cs.zone.merchant.model.Merchant;
import app.theblu.cashflow.cs.zone.merchant.repo.MerchantRepo;
import app.theblu.cashflow.cs.zone.merchant.repo.MerchantVoteRepo;
import app.theblu.cashflow.cs.zone.merchant.repo.mongo.dbo.MerchantNameVoteDboMg;
import app.theblu.cashflow.cs.zone.merchant.dto.MerchantCategoryVoteReq;
import app.theblu.cashflow.cs.zone.merchant.dto.MerchantNameVoteReq;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantService {
    private final MerchantRepo repo;
    private final MerchantVoteRepo voteRepo;
    private final CategoryService categoryService;

    public MerchantService(MerchantRepo repo, MerchantVoteRepo voteRepo, CategoryService categoryService) {
        this.repo = repo;
        this.voteRepo = voteRepo;
        this.categoryService = categoryService;
    }

    public Merchant findByIdOrCreate(String fid) {
        Optional<Merchant> merchantOp = this.repo.findByFid(fid);
        if (merchantOp.isPresent()) return merchantOp.get();

        // if merchant does not exist, then create new one
        Merchant merchant = Merchant.Companion.fromFid(fid);
        return this.repo.save(merchant);
    }

    public void voteOnCategory(MerchantCategoryVoteReq req) {
        Merchant merchant = this.assertMerchantExists(req.getMerchantId());
        this.assertCategoryExists(req.getCategoryId());
        boolean isAdded = this.voteRepo.addCategoryVote(req.getMerchantId(), req.getUserId(), req.getCategoryId());
        if (!isAdded) return;

        // recalculate merchant suggested categories
        Long votes = this.voteRepo.getVotesForCategory(req.getMerchantId(), req.getCategoryId());
        merchant.getSuggestedCategories().put(req.getCategoryId(), votes);
        this.repo.save(merchant);
    }

    private Merchant assertMerchantExists(String merchantId) {
        // assert merchant id is exist
        Optional<Merchant> merchantOp = this.repo.findByFid(merchantId);
        if (merchantOp.isEmpty()) {
            List<ErrorDetail> errorDetails = new LinkedList<>();
            val ed = new ErrorDetail("merchantId", "merchant id not found", "merchant id not found. id: " + merchantId);
            errorDetails.add(ed);
            throw new BusinessValidationException(errorDetails);
        }
        return merchantOp.get();
    }

    private void assertCategoryExists(String categoryId) {
        Category category = categoryService.findById(categoryId);
        if (category == null) {
            List<ErrorDetail> errorDetails = new LinkedList<>();
            val ed = new ErrorDetail("categoryId", "category id not found", "category id not found. id: " + categoryId);
            errorDetails.add(ed);
            throw new BusinessValidationException(errorDetails);
        }
    }

    public void voteOnName(MerchantNameVoteReq req) {
        Merchant merchant = this.assertMerchantExists(req.getMerchantId());

        // if merchant suggested a different name in the past remove it
        Optional<MerchantNameVoteDboMg> pnvOptional = this.voteRepo.findNameByMerchantIdAndUserId(req.getMerchantId(), req.getUserId());
        if (pnvOptional.isPresent() && !pnvOptional.get().getName().equalsIgnoreCase(req.getName())) {
            MerchantNameVoteDboMg pnv = pnvOptional.get();
            // remove existing vote
            this.voteRepo.removeVoteName(pnv);
            // recalculate name votes
            Long votes = this.voteRepo.getVotesForName(pnv.getMerchantId(), pnv.getName());
            if (votes == 0) {
                merchant.getSuggestedNames().remove(pnv.getName());
            } else {
                merchant.getSuggestedNames().put(pnv.getName(), votes);
            }
            this.repo.save(merchant);
        }

        // add the new name vote.
        boolean isAdded = this.voteRepo.addNameVote(req.getMerchantId(), req.getUserId(), req.getName());
        if (!isAdded) return; // if the vote for name exits, do nothing return immediately

        // recalculate name votes
        Long votes = this.voteRepo.getVotesForName(req.getMerchantId(), req.getName());
        merchant.getSuggestedNames().put(req.getName(), votes);
        this.repo.save(merchant);
    }
}
