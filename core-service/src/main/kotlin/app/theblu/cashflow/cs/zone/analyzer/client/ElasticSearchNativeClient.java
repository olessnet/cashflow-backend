package app.theblu.cashflow.cs.zone.analyzer.client;

import app.theblu.cashflow.cs.zone.analyzer.dto.AggregateHeaderGroupReq;
import app.theblu.cashflow.cs.zone.analyzer.dto.SearchUnknownMsgHeaderReq;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Conflicts;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ElasticSearchNativeClient {
    private final ElasticsearchClient esClient;

    public ElasticSearchNativeClient(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    @SneakyThrows
    public void deleteUnknownMsg(String header, String bodyExpression) {
        Query byOrgName = TermQuery.of(m -> m
                .field("msg.header.keyword")
                .value(header)
        )._toQuery();

        Query byRegex = RegexpQuery.of(m -> m
                .field("msg.body.keyword")
                .value(escapeRegex(bodyExpression))
        )._toQuery();


        DeleteByQueryRequest dbyquery = DeleteByQueryRequest
                .of(fn -> fn.query(q -> q.bool(bq -> bq.must(byOrgName).must(byRegex))).index("msg-unknown"));

        DeleteByQueryResponse response = esClient.deleteByQuery(dbyquery);
        System.out.println(response);
    }

    /**
     * filters all the unknown messages and aggregates header by message count
     */
    @SneakyThrows
    public List aggregateMsgByHeader(AggregateHeaderGroupReq req) {
        Query possibleTransaction = MatchQuery.of(m -> m
                .field("possibleTransaction")
                .query(req.isPossibleTransaction())
        )._toQuery();
        Query headerVerified = MatchQuery.of(m -> m
                .field("headerVerified")
                .query(req.isHeaderVerified())
        )._toQuery();
        Query bankingHeader = MatchQuery.of(m -> m
                .field("bankingHeader")
                .query(req.isBankingHeader())
        )._toQuery();

        Query boolQuery = QueryBuilders.bool(bq -> {
            if (req.isPossibleTransaction()) {
                bq.must(possibleTransaction);
            }
            if (req.isHeaderVerified()) {
                bq.must(headerVerified);
            }
            if (req.isBankingHeader()) {
                bq.must(bankingHeader);
            }
            return bq;
        });

        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder
                .index("msg-unknown")
                .size(0)
                .query(boolQuery)
                .aggregations("group_by_header", a -> a.terms(t -> t.field("msg.header.keyword").size(10000)));

        val searchRequest = builder.build();
        SearchResponse<Map> response = esClient.search(searchRequest, Map.class);
        List arg = response.aggregations().get("group_by_header").sterms().buckets().array().stream().map(tb -> Map.of(tb.key().stringValue(), tb.docCount())).toList();
        return arg;
    }

    @SneakyThrows
    public List filterMsgByHeader(SearchUnknownMsgHeaderReq req) {
        Query possibleTransaction = MatchQuery.of(m -> m
                .field("possibleTransaction")
                .query(req.isPossibleTransaction())
        )._toQuery();

        Query byHeader = MatchQuery.of(m -> m.field("msg.header").query(req.getHeader()))._toQuery();

        Query boolQuery = QueryBuilders.bool(bq -> {
            if (req.isPossibleTransaction()) {
                bq.must(possibleTransaction);
            }
            bq.must(byHeader);
            return bq;
        });

        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder
                .index("msg-unknown")
                .size(9999)
                .query(boolQuery);

        var ereq = builder.build();
        SearchResponse<Map> response = esClient.search(ereq, Map.class);
        List hits = response.hits().hits().stream().map(h -> h.source()).toList();
        return hits;
    }

    public void deleteMsgByHeader(String header) throws IOException {
        Query byHeader = TermQuery.of(m -> m
                .field("msg.header.keyword")
                .value(header)
        )._toQuery();

        DeleteByQueryRequest dbyquery = DeleteByQueryRequest
                .of(fn -> fn.query(q -> q.bool(bq -> bq.must(byHeader))).index("msg-unknown"));

        DeleteByQueryResponse response = esClient.deleteByQuery(dbyquery);
    }

    // todo refractor code
    // sometimes produces URI [/msg-unknown/_delete_by_query], status line [HTTP/1.1 409 Conflict]
    public void deleteMsgById(String msgId) throws IOException {
        Query byHeader = TermQuery.of(m -> m
                .field("msg.id.keyword")
                .value(msgId)
        )._toQuery();

        DeleteByQueryRequest dbyquery = DeleteByQueryRequest.of(fn -> fn.query(q -> q.bool(bq -> bq.must(byHeader))).conflicts(Conflicts.Proceed).index("msg-unknown"));
        DeleteByQueryResponse response = esClient.deleteByQuery(dbyquery);
    }

    private static String escapeRegex(String regex) {
        regex = regex.replaceAll("#", "\\\\#");
        regex = regex.replaceAll("@", "\\\\@");
        regex = regex.replaceAll("&", "\\\\&");
        regex = regex.replaceAll("<", "\\\\<");
        regex = regex.replaceAll(">", "\\\\>");
        regex = regex.replaceAll("~", "\\\\~");
        return regex;
    }
}
