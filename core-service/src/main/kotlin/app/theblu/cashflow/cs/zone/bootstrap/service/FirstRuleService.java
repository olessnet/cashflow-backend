package app.theblu.cashflow.cs.zone.bootstrap.service;

import app.theblu.cashflow.cs.domain.FirstRule;
import app.theblu.cashflow.cs.batteries.common.ClasspathUtil;
import app.theblu.cashflow.cs.batteries.common.JsonUtil;
import org.springframework.stereotype.Service;

@Service
public class FirstRuleService {
    private FirstRule firstRule;

    public FirstRule getFirstRule() {
        if (firstRule == null) {
            synchronized (FirstRuleService.class) {
                String yaml = ClasspathUtil.INSTANCE.readPathAsString("config/first-rule.yaml");
                String json = JsonUtil.INSTANCE.fromYamlToJson(yaml);
                firstRule = JsonUtil.INSTANCE.fromJson(json, FirstRule.class);
            }
        }
        return firstRule;
    }
}
