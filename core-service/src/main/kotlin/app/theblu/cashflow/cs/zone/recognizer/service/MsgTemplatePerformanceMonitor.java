package app.theblu.cashflow.cs.zone.recognizer.service;

import app.theblu.cashflow.cs.domain.template.MsgTemplate;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MsgTemplatePerformanceMonitor {
    private Map<String, MsgTemplateMetric> metrics = new HashMap<>();

    public void count(MsgTemplate msgTemplate, long time) {
        var metric = metrics.get(msgTemplate.getId());
        if (metric == null) {
            metric = new MsgTemplateMetric();
            metrics.put(msgTemplate.getId(), metric);
        }
        metric.count(time);
    }

    public Map<String, Object> getMetrics() {
        Map<String, Object> map = new HashMap<>();

        Map<String, MsgTemplateMetric> filteredMap = metrics.entrySet().stream()
                .filter(entry -> entry.getValue().getAvgTime() >= 0.1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        map.put("poor", filteredMap);

        filteredMap = metrics.entrySet().stream()
                .filter(entry -> entry.getValue().getAvgTime() < 0.1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        map.put("good", filteredMap);

        return map;
    }
}


@Data
class MsgTemplateMetric {
    private int count;
    private long totalTime;
    private double avgTime;

    public void count(long time) {
        count++;
        totalTime += time;
        avgTime = (double) totalTime / count;
    }
}