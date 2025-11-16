package app.theblu.cashflow.cs.zone.org.web.org;

import app.theblu.cashflow.cs.zone.org.service.orgindex.OrgIndexService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/org-index")
public class OrgIndexCtrl {
    private final OrgIndexService indexService;

    public OrgIndexCtrl(OrgIndexService indexService) {
        this.indexService = indexService;
    }

    @PostMapping
    public void reIndexDatabase() {
        this.indexService.index();
    }

    @PostMapping("/download")
    public void download() {
        this.indexService.download();
    }

    @PostMapping("/read")
    public void read() {
        this.indexService.read();
    }

    @PostMapping("/update")
    public void update() {
        this.indexService.update();
    }

    @PostMapping("/delete")
    public void delete() {
        this.indexService.delete();
    }
}
