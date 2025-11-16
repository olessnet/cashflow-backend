package app.theblu.cashflow.cs.zone.recognizer.web;


import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder;
import app.theblu.cashflow.cs.zone.recognizer.service.MsgTemplatePerformanceMonitor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/msg-template-performance-monitor")
@Slf4j
public class MsgTemplatePerformanceMonitorCtrl {
    private final MsgTemplatePerformanceMonitor msgTemplatePerformanceMonitor;

    public MsgTemplatePerformanceMonitorCtrl(MsgTemplatePerformanceMonitor msgTemplatePerformanceMonitor) {
        this.msgTemplatePerformanceMonitor = msgTemplatePerformanceMonitor;
    }

    @GetMapping
    public @NotNull ResponseEntity<Object> monitor() {
        var data = this.msgTemplatePerformanceMonitor.getMetrics();
        return ApiResBuilder.INSTANCE.buildRes(data);
    }
}
