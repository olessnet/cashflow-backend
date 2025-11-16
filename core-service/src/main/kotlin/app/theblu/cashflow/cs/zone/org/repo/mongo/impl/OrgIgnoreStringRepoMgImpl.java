package app.theblu.cashflow.cs.zone.org.repo.mongo.impl;

import app.theblu.cashflow.cs.domain.org.OrgIgnoreString;
import app.theblu.cashflow.cs.zone.org.repo.OrgIgnoreStringRepo;
import app.theblu.cashflow.cs.zone.org.repo.mongo.dao.OrgIgnoreStringDaoMg;
import app.theblu.cashflow.cs.zone.org.repo.mongo.dbo.OrgIgnoreStringDboMg;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrgIgnoreStringRepoMgImpl implements OrgIgnoreStringRepo {
    private final OrgIgnoreStringDaoMg repo;

    public OrgIgnoreStringRepoMgImpl(OrgIgnoreStringDaoMg repo) {
        this.repo = repo;
    }

    @Override
    public void save(OrgIgnoreString orgIgnoreString) {
        var dbo = OrgIgnoreStringDboMg.from(orgIgnoreString);
        this.repo.save(dbo);
    }

    @Override
    public Optional<OrgIgnoreString> findById(String id) {
        Optional<OrgIgnoreStringDboMg> optional = this.repo.findById(id);
        return optional.map(OrgIgnoreStringDboMg::to);
    }

    @Override
    public void deleteById(String id) {
        this.repo.deleteById(id);
    }
}
