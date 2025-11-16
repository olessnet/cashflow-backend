package app.theblu.cashflow.cs.zone.org.repo;



import app.theblu.cashflow.cs.domain.org.Org;

import java.util.List;
import java.util.Optional;

public interface OrgRepo {
    Optional<Org> findByName(String name);
    List<Org> findAllByShortName(String shortName);
    Optional<Org> findByHeader(String header);
    Optional<Org> findById(String id);
    List<Org> findByNameLikeIgnoreCase(String term, int i);
    Org save(Org org);
    List<Org> findAll();
    void saveAll(List<Org> orgs);
}
