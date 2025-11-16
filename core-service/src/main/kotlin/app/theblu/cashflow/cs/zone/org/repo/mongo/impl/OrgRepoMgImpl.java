package app.theblu.cashflow.cs.zone.org.repo.mongo.impl;


import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.zone.org.repo.OrgRepo;
import app.theblu.cashflow.cs.zone.org.repo.mongo.dao.OrgDaoMg;
import app.theblu.cashflow.cs.zone.org.repo.mongo.dbo.OrgDboMg;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrgRepoMgImpl implements OrgRepo {
    private final OrgDaoMg orgDao;

    public OrgRepoMgImpl(OrgDaoMg orgDao) {
        this.orgDao = orgDao;
    }

    @Override
    public Optional<Org> findByName(String name) {
        Optional<OrgDboMg> optional = this.orgDao.findByName(name);
        return optional.map(OrgDboMg::to);
    }

    @Override
    public List<Org> findAllByShortName(String shortName) {
        List<OrgDboMg> list = this.orgDao.findAllByShortName(shortName);
        return list.stream().map(OrgDboMg::to).toList();
    }

    @Override
    public Optional<Org> findByHeader(String header) {
        Optional<OrgDboMg> optional = this.orgDao.findByPromotionalHeadersContaining(header);
        if (optional.isEmpty()) {
            optional = this.orgDao.findByTransactionalHeadersContaining(header);
        }
        return optional.map(OrgDboMg::to);
    }

    @Override
    public Optional<Org> findById(String id) {
        Optional<OrgDboMg> optional = this.orgDao.findById(id);
        return optional.map(OrgDboMg::to);
    }

    @Override
    public List<Org> findByNameLikeIgnoreCase(String term, int i) {
        List<OrgDboMg> list = this.orgDao.findByNameLikeIgnoreCase(term, 100);
        return list.stream().map(OrgDboMg::to).toList();
    }

    @Override
    public Org save(Org org) {
        var temp = this.orgDao.save(OrgDboMg.from(org));
        return temp.to();
    }

    @Override
    public List<Org> findAll() {
        List<OrgDboMg> list = this.orgDao.findAll();
        return list.stream().map(OrgDboMg::to).toList();
    }

    @Override
    public void saveAll(List<Org> orgs) {
        var list = orgs.stream().filter(org -> org.getName().toLowerCase().contains("bank")).toList();
        for(Org org : list) {
            OrgDboMg dbo = OrgDboMg.from(org);
            System.out.println();
        }
        var temp = orgs.stream().map(OrgDboMg::from).toList();
        this.orgDao.saveAll(temp);
    }
}
