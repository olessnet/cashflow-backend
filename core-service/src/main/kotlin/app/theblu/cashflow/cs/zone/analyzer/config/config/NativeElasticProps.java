package app.theblu.cashflow.cs.zone.analyzer.config.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("services")
@Component
@Data
public class NativeElasticProps {
    private Config elasticSearch;

    @Data
    public static class Config {
        private String baseUrl;
    }
}
