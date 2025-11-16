package app.theblu.cashflow.cs.zone.bootstrap.web;

import app.theblu.cashflow.cs.batteries.common.ClasspathUtil;
import app.theblu.cashflow.cs.batteries.common.JsonUtil;
import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/first-rule")
public class FirstRuleCtrl {
    @GetMapping
    public ResponseEntity<Object> get() {
        String yaml = ClasspathUtil.INSTANCE.readPathAsString("config/first-rule.yaml");
        String json = JsonUtil.INSTANCE.fromYamlToJson(yaml);
        return ApiResBuilder.INSTANCE.buildRes(json, null);
    }
}
