package app.theblu.cashflow.cs.zone.org.repo.mongo.dao;

import app.theblu.cashflow.cs.zone.org.repo.mongo.dbo.MsgTemplateDboMg;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsgTemplateDaoMg extends MongoRepository<MsgTemplateDboMg, String> {
    List<MsgTemplateDboMg> findAllByOrgId(String orgId);
}
