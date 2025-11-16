package app.theblu.cashflow.cs.zone.org.repo.mongo.dao;

import app.theblu.cashflow.cs.zone.org.repo.mongo.dbo.OrgIgnoreStringDboMg;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgIgnoreStringDaoMg extends MongoRepository<OrgIgnoreStringDboMg, String> {
}
