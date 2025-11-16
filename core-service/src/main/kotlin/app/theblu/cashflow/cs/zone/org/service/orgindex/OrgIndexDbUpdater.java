package app.theblu.cashflow.cs.zone.org.service.orgindex;

import app.theblu.cashflow.cs.batteries.common.BatchUtil;
import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.zone.org.repo.OrgRepo;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class OrgIndexDbUpdater {
    private Map<String, Org> orgByIdMap = new HashMap<>();
    private Map<String, Org> orgByHeaderMap = new HashMap<>();
    private List<Org> orgUpdateList = new LinkedList<>();

    public void update(List<Org> orgs, OrgRepo orgRepo) {
//        prepareExistingOrgCache(orgRepo);
        for (Org org : orgs) {
            update(org);
        }
        long start = System.currentTimeMillis();
        log.info("updating database with org details. size: {}", orgUpdateList.size());
//        BatchUtil.processParallel(orgUpdateList, 5000, orgRepo::saveAll);
        log.info("database update took: {}", System.currentTimeMillis() - start);
    }

    private void prepareExistingOrgCache(OrgRepo orgRepo) {
        this.orgByIdMap = orgRepo.findAll().stream().collect(Collectors.toMap(Org::getId, item -> item));
        for (Org org : orgByIdMap.values()) {
            for (String header : org.getHeaders()) {
                orgByHeaderMap.put(header, org);
            }
        }
    }

    private void update(Org org) {
        assertHeaders(org);

        var oldOrg = orgByIdMap.get(org.getId());
        if (oldOrg == null) {
            this.orgByIdMap.put(org.getId(), org);
            org.getHeaders().forEach(header -> orgByHeaderMap.put(header, org));
            orgUpdateList.add(org);
        } else {
            var isHeadersChange = org.isHeadersChanged(oldOrg);
            if (isHeadersChange) {
                org.updateFrom(oldOrg);
                orgUpdateList.add(org);
                this.orgByIdMap.put(org.getId(), org);
                org.getHeaders().forEach(header -> orgByHeaderMap.put(header, org));
            }
        }
    }

    protected void assertHeaders(Org org) {
        for (String header : org.getHeaders()) {
            if (this.orgByHeaderMap.containsKey(header)) {
                var otherOrg = this.orgByHeaderMap.get(header);
                if (otherOrg == null) continue;
                if (!org.getId().equals(otherOrg.getId())) {
                    log.error("Duplicate header in existing org. header: {} is in both orgs new/old -> {}/{} names:{}/{}", header, org.getId(), otherOrg.getId(), org.getName(), otherOrg.getName());
                    throw new RuntimeException("Duplicate Header: " + header);
                }
            }
        }
    }
}
