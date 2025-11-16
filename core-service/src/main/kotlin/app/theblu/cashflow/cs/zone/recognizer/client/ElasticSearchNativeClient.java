//package app.theblu.cashflow.cs.zone.recognizer.client;
//
//import app.theblu.cashflow.core.web.reqres.unknownmsg.AggregateHeaderGroupReq;
//import app.theblu.cashflow.core.web.reqres.unknownmsg.SearchUnknownMsgHeaderReq;
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
//import co.elastic.clients.elasticsearch._types.query_dsl.Query;
//import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
//import co.elastic.clients.elasticsearch.core.SearchRequest;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import lombok.SneakyThrows;
//import lombok.val;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ElasticSearchNativeClient {
//    private final ElasticsearchClient esClient;
//
//    public ElasticSearchNativeClient(ElasticsearchClient esClient) {
//        this.esClient = esClient;
//    }
//
//    /**
//     * filters all the unknown messages and aggregates header by message count
//     */
//    @SneakyThrows
//    public List aggregateMsgByHeader(AggregateHeaderGroupReq req) {
//        Query possibleTransaction = MatchQuery.of(m -> m
//                .field("possibleTransaction")
//                .query(req.isPossibleTransaction())
//        )._toQuery();
//        Query headerVerified = MatchQuery.of(m -> m
//                .field("headerVerified")
//                .query(req.isHeaderVerified())
//        )._toQuery();
//        Query bankingHeader = MatchQuery.of(m -> m
//                .field("bankingHeader")
//                .query(req.isBankingHeader())
//        )._toQuery();
//
//        Query boolQuery = QueryBuilders.bool(bq -> {
//            bq.must(possibleTransaction);
//            bq.must(headerVerified);
//            bq.must(bankingHeader);
//            return bq;
//        });
//
//        SearchRequest.Builder builder = new SearchRequest.Builder();
//        builder
//                .index("msg-unknown")
//                .size(0)
//                .query(boolQuery)
//                .aggregations("group_by_header", a -> a.terms(t -> t.field("msg.header.keyword").size(10000)));
//
//        val searchRequest = builder.build();
//        SearchResponse<Map> response = esClient.search(searchRequest, Map.class);
//        List arg = response.aggregations().get("group_by_header").sterms().buckets().array().stream().map(tb -> Map.of(tb.key().stringValue(), tb.docCount())).toList();
//        return arg;
//    }
//
//    @SneakyThrows
//    public List filterMsgByHeader(SearchUnknownMsgHeaderReq req) {
//        Query possibleTransaction = MatchQuery.of(m -> m
//                .field("possibleTransaction")
//                .query(req.isPossibleTransaction())
//        )._toQuery();
//        Query headerVerified = MatchQuery.of(m -> m
//                .field("headerVerified")
//                .query(req.isHeaderVerified())
//        )._toQuery();
//        Query bankingHeader = MatchQuery.of(m -> m
//                .field("bankingHeader")
//                .query(req.isBankingHeader())
//        )._toQuery();
//
//        Query byHeader = MatchQuery.of(m -> m.field("msg.header").query(req.getHeader()))._toQuery();
//
//        Query boolQuery = QueryBuilders.bool(bq -> {
//            bq.must(possibleTransaction);
//            bq.must(headerVerified);
//            bq.must(bankingHeader);
//            bq.must(byHeader);
//            return bq;
//        });
//
//
//        SearchRequest.Builder builder = new SearchRequest.Builder();
//        builder
//                .index("msg-unknown")
//                .size(100)
//                .query(boolQuery);
//
//        SearchResponse<Map> response = esClient.search(builder.build(), Map.class);
//        List hits = response.hits().hits().stream().map(h -> h.source()).toList();
//        return hits;
//    }
//}
