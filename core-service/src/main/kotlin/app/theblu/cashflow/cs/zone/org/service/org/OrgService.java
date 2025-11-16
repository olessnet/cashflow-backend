package app.theblu.cashflow.cs.zone.org.service.org;

import app.theblu.cashflow.cs.batteries.rocket.exception.BusinessValidationException;
import app.theblu.cashflow.cs.batteries.rocket.res.ErrorDetail;
import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.zone.org.repo.OrgRepo;
import app.theblu.cashflow.cs.zone.org.dto.OrgUpdateReq;
import app.theblu.cashflow.cs.zone.recognizer.service.CacheClearService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrgService {
    private final OrgRepo orgRepo;
    private final CacheClearService cacheClearService;
    private final CacheManager cacheManager;

    private int cacheHit = 0;
    private int cacheMiss = 0;


    public OrgService(OrgRepo orgMongoRepo, CacheClearService cacheClearService, CacheManager cacheManager) {
        this.orgRepo = orgMongoRepo;
        this.cacheManager = cacheManager;
        this.cacheClearService = cacheClearService;
    }

    //    @Cacheable(value = "org_by_header_cache")
    public Optional<Org> findById(String id) {
        log.debug("Cache {}/{}", cacheHit, cacheMiss);
        var cache = cacheManager.getCache("org_by_id_cache");
        var cached = cache.get(id);
        if (cached != null) {
            cacheHit++;
            return (Optional<Org>) cached.get();
        } else {
            cacheMiss++;
            var data = this.orgRepo.findById(id);
            cache.put(id, data);
            return data;
        }
//       return  this.orgRepo.findById(id);
    }

    @Cacheable(value = "org_cache")
    public Optional<Org> findByHeader(String header) {
//        header = header.toUpperCase();
        Optional<Org> orgOptional = this.orgRepo.findByHeader(header);
        return orgOptional;
    }

    public List<Org> findByName(@PathVariable String name) {
        return this.orgRepo.findByNameLikeIgnoreCase(name, 100);
    }

    public List<Org> findShortName(@PathVariable String shortName) {
        return this.orgRepo.findAllByShortName(shortName);
    }

    public Org update(OrgUpdateReq req) {
        List<ErrorDetail> errorDetails = validateOrgUpdateReq(req);
        if (!errorDetails.isEmpty()) throw new BusinessValidationException(errorDetails);
        cacheClearService.clearCache();
        return this.orgRepo.save(req.to());
    }

    private List<ErrorDetail> validateOrgUpdateReq(OrgUpdateReq req) {
        List<ErrorDetail> errorDetails = new LinkedList<>();
        Optional<Org> orgOptional = this.orgRepo.findById(req.getId());
        if (orgOptional.isEmpty()) {
            val detail = new ErrorDetail("id", "no org found for the given id", null);
            errorDetails.add(detail);
            return errorDetails;
        }

        orgOptional = this.orgRepo.findByName(req.getName());
        if (orgOptional.isPresent() && !req.getId().equals(orgOptional.get().getId())) {
            val detail = new ErrorDetail("name", "name must be uniq. already an org has this name", null);
            errorDetails.add(detail);
        }

        if (!StringUtils.hasText(req.getShortName())) {
            val detail = new ErrorDetail("shortName", "value must not be null or empty", null);
            errorDetails.add(detail);
        }
        return errorDetails;
    }


}
