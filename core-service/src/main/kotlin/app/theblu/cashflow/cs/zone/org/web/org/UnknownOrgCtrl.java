package app.theblu.cashflow.cs.zone.org.web.org;

import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.zone.org.repo.OrgRepo;
import app.theblu.cashflow.cs.zone.recognizer.service.CacheClearService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1/unknown-org")
public class UnknownOrgCtrl {
    private final OrgRepo orgRepo;
    private final CacheClearService cacheClearService;

    public UnknownOrgCtrl(OrgRepo orgRepo, CacheClearService cacheClearService) {
        this.orgRepo = orgRepo;
        this.cacheClearService = cacheClearService;
    }

    @PostMapping("/add-header/{header}")
    public void addHeaderToUnknownOrg(@PathVariable @NotBlank String header) {
        var orgOptional = this.orgRepo.findById("000");
        if (orgOptional.isPresent()) {
            orgOptional.get().getTransactionalHeaders().add(header);
            this.orgRepo.save(orgOptional.get());
        } else {
            Org org = new Org();
            org.setId("000");
            org.setName("Unknown Org");
            org.setShortName("UnknownOrg");
            org.setVerified(true);
            org.setBanking(true);
            org.setIgnore(true);
            org.getPromotionalHeaders().add(header);
            this.orgRepo.save(org);
        }
        cacheClearService.clearCache();
    }
}
