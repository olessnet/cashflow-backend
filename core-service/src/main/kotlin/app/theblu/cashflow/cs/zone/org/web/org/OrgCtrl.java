package app.theblu.cashflow.cs.zone.org.web.org;

import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder;
import app.theblu.cashflow.cs.zone.org.service.org.OrgService;
import app.theblu.cashflow.cs.zone.org.dto.OrgUpdateReq;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/org")
public class OrgCtrl {
    private final OrgService orgService;

    public OrgCtrl(OrgService orgService) {
        this.orgService = orgService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        var optional = this.orgService.findById(id);
        return ApiResBuilder.INSTANCE.buildRes(optional);
    }

    @GetMapping("/searchByName/{name}")
    public ResponseEntity<Object> findByName(@PathVariable String name) {
        var data = this.orgService.findByName(name);
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @GetMapping("/searchByShortName/{shortName}")
    public ResponseEntity<Object> findShortName(@PathVariable String shortName) {
        var data = this.orgService.findShortName(shortName);
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @GetMapping("/searchByHeader/{header}")
    public ResponseEntity<Object> findByHeader(@PathVariable String header) {
        var data = this.orgService.findByHeader(header);
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @PutMapping
    public ResponseEntity<Object> update(@Valid @RequestBody OrgUpdateReq req) {
        var org = this.orgService.update(req);
        return ApiResBuilder.INSTANCE.buildRes(org);
    }
}
