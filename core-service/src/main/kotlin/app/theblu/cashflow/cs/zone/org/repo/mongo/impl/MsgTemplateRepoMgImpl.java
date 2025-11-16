package app.theblu.cashflow.cs.zone.org.repo.mongo.impl;


import app.theblu.cashflow.cs.domain.template.MsgTemplate;
import app.theblu.cashflow.cs.zone.org.repo.MsgTemplateRepo;
import app.theblu.cashflow.cs.zone.org.repo.mongo.dao.MsgTemplateDaoMg;
import app.theblu.cashflow.cs.zone.org.repo.mongo.dbo.MsgTemplateDboMg;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MsgTemplateRepoMgImpl implements MsgTemplateRepo {
    private final MsgTemplateDaoMg msgTemplateDao;

    public MsgTemplateRepoMgImpl(MsgTemplateDaoMg msgTemplateDao) {
        this.msgTemplateDao = msgTemplateDao;
    }

    @Override
    public List<MsgTemplate> findAllByOrgId(String orgId) {
        List<MsgTemplateDboMg> list = this.msgTemplateDao.findAllByOrgId(orgId);
        return list.stream().map(MsgTemplateDboMg::to).toList();
    }

    @Override
    public List<MsgTemplate> findAll() {
        var list = this.msgTemplateDao.findAll();
        return list.stream().map(MsgTemplateDboMg::to).toList();
    }

    @Override
    public Optional<MsgTemplate> findById(String id) {
        Optional<MsgTemplateDboMg> optional = this.msgTemplateDao.findById(id);
        return optional.map(MsgTemplateDboMg::to);
    }

    @Override
    public MsgTemplate save(MsgTemplate template) {
        var temp1 = MsgTemplateDboMg.from(template);
        return this.msgTemplateDao.save(temp1).to();
    }

    @Override
    public void deleteById(String id) {
        this.msgTemplateDao.deleteById(id);
    }
}
