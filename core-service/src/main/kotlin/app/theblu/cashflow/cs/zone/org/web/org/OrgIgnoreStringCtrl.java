package app.theblu.cashflow.cs.zone.org.web.org;

import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder;
import app.theblu.cashflow.cs.zone.org.dto.OrgIgnoreStringAddOrRemoveReq;
import app.theblu.cashflow.cs.zone.org.service.org.OrgIgnoreStringService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/org-ignore-string")
public class OrgIgnoreStringCtrl {
    private final OrgIgnoreStringService service;

    public OrgIgnoreStringCtrl(OrgIgnoreStringService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public void add(@Valid @RequestBody OrgIgnoreStringAddOrRemoveReq req) {
        this.service.addIgnoreString(req.getHeader(), req.getIgnoreString());
    }

    @PostMapping("/remove")
    public void remove(@Valid @RequestBody OrgIgnoreStringAddOrRemoveReq req) {
        this.service.removeIgnoreString(req.getHeader(), req.getIgnoreString());
    }

    @GetMapping("/{header}")
    public ResponseEntity<Object> get(@PathVariable String header) {
        var data = this.service.getIgnoreStringList(header);
        return ApiResBuilder.INSTANCE.buildRes(data);
    }

    @DeleteMapping("/{header}")
    public void delete(@PathVariable String header) {
        this.service.deleteIgnoreStringList(header);
    }
}
