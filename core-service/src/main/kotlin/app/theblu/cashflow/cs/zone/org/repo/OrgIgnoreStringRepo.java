package app.theblu.cashflow.cs.zone.org.repo;

import app.theblu.cashflow.cs.domain.org.OrgIgnoreString;
import app.theblu.cashflow.cs.zone.org.repo.mongo.dbo.OrgIgnoreStringDboMg;

import java.util.Optional;

public interface OrgIgnoreStringRepo {
    void save(OrgIgnoreString orgIgnoreString) ;
    Optional<OrgIgnoreString> findById(String id);
    void deleteById(String id);
}
