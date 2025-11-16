package app.theblu.cashflow.cs.zone.org.service.org;

import app.theblu.cashflow.cs.batteries.rocket.exception.BusinessValidationException;
import app.theblu.cashflow.cs.domain.org.OrgIgnoreString;
import app.theblu.cashflow.cs.zone.org.repo.OrgIgnoreStringRepo;
import app.theblu.cashflow.cs.zone.recognizer.service.CacheClearService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrgIgnoreStringService {
    private final OrgService orgService;
    private final OrgIgnoreStringRepo repo;
    private final CacheClearService cacheClearService;

    public OrgIgnoreStringService(OrgService orgService, OrgIgnoreStringRepo repo, CacheClearService cacheClearService) {
        this.orgService = orgService;
        this.repo = repo;
        this.cacheClearService = cacheClearService;
    }

    public void addIgnoreString(String header, List<String> ignoreString) {
        var orgOptional = this.orgService.findByHeader(header);
        if (orgOptional.isEmpty()) {
            throw BusinessValidationException
                    .Companion
                    .withError("header", "No Org found for given header: " + header, null);
        }
        var orgId = orgOptional.get().getId();

        var optional = this.repo.findById(orgId);
        OrgIgnoreString ois = null;
        if (optional.isPresent()) {
            ois = optional.get();
        } else {
            ois = new OrgIgnoreString();
            ois.setId(orgId);
        }
        ois.getStringSet().addAll(ignoreString);
        repo.save(ois);
        this.cacheClearService.clearCache();
    }

    public void removeIgnoreString(String header, List<String> ignoreString) {
        var orgOptional = this.orgService.findByHeader(header);
        if (orgOptional.isEmpty()) {
            throw BusinessValidationException
                    .Companion
                    .withError("header", "No Org found for given header: " + header, null);
        }
        var orgId = orgOptional.get().getId();

        var optional = this.repo.findById(orgId);
        if (optional.isEmpty()) return;

        OrgIgnoreString ois = optional.get();
        ignoreString.forEach(ois.getStringSet()::remove);
        repo.save(ois);
        this.cacheClearService.clearCache();
    }

    @Cacheable(value = "org_ingore_string_cache")
    public Optional<OrgIgnoreString> getIgnoreStringList(String header) {
        var orgOptional = this.orgService.findByHeader(header);
        if (orgOptional.isEmpty()) {
            throw BusinessValidationException
                    .Companion
                    .withError("header", "No Org found for given header: " + header, null);
        }
        var orgId = orgOptional.get().getId();

        return repo.findById(orgId);
    }

    public void deleteIgnoreStringList(String header) {
        var orgOptional = this.orgService.findByHeader(header);
        if (orgOptional.isEmpty()) {
            throw BusinessValidationException
                    .Companion
                    .withError("header", "No Org found for given header: " + header, null);
        }
        var orgId = orgOptional.get().getId();

        this.repo.deleteById(orgId);
    }
}
