package app.theblu.cashflow.cs.zone.org.repo;



import app.theblu.cashflow.cs.domain.template.MsgTemplate;

import java.util.List;
import java.util.Optional;

public interface MsgTemplateRepo {
    List<MsgTemplate> findAllByOrgId(String orgId);
    List<MsgTemplate> findAll();
    Optional<MsgTemplate> findById(String id);
    MsgTemplate save(MsgTemplate template);
    void deleteById(String id);
}
