package app.theblu.cashflow.cs.zone.org.repo.mongo.dao;

import app.theblu.cashflow.cs.zone.org.repo.mongo.dbo.OrgDboMg;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgDaoMg extends MongoRepository<OrgDboMg, String> {
    Optional<OrgDboMg> findByName(String name);
    List<OrgDboMg> findByNameLikeIgnoreCase(String name, int limit);
    List<OrgDboMg> findAllByShortName(String shortName);
    Optional<OrgDboMg> findByPromotionalHeadersContaining(String header);
    Optional<OrgDboMg> findByTransactionalHeadersContaining(String header);
}
