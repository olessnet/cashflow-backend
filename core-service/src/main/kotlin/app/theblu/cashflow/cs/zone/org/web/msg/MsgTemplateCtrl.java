package app.theblu.cashflow.cs.zone.org.web.msg;

import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder;
import app.theblu.cashflow.cs.domain.template.MsgTemplate;
import app.theblu.cashflow.cs.zone.org.service.msg.MsgTemplateService;
import app.theblu.cashflow.cs.zone.org.dto.MsgTemplateSaveReq;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/v1/msg-templates")
public class MsgTemplateCtrl {
    private final MsgTemplateService templateService;

    public MsgTemplateCtrl(MsgTemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        var data = this.templateService.getAll();
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        var data = this.templateService.getById(id);
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @GetMapping("searchByHeader/{header}")
    public ResponseEntity<Object> getByHeader(String header) {
        var data = this.templateService.getByHeader(header);
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @GetMapping("searchByOrgId/{orgIdorgId}")
    public List<MsgTemplate> getByOrgId(String orgId) {
        var temp = this.templateService.getByOrgId(orgId);
        return temp;
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody MsgTemplateSaveReq createReq) {
        var data = this.templateService.save(createReq);
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        this.templateService.delete(id);
    }
}
