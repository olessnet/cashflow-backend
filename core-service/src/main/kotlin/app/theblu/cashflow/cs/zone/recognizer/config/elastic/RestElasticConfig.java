package app.theblu.cashflow.cs.zone.recognizer.config.elastic;

import app.theblu.cashflow.cs.zone.recognizer.client.ElasticSearchRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestElasticConfig {
    private final RestElasticProps serviceProps;

    public RestElasticConfig(RestElasticProps serviceProps) {
        this.serviceProps = serviceProps;
    }

    @Bean
    public ElasticSearchRestClient restClient() {
        return new ElasticSearchRestClient(serviceProps.getElasticSearch().getBaseUrl());
    }
}