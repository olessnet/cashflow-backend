package app.theblu.cashflow.cs.zone.org.repo.mongo.dbo;

import app.theblu.cashflow.cs.config.StaticContext;
import app.theblu.cashflow.cs.batteries.common.JPattern;
import app.theblu.cashflow.cs.domain.org.FiChannel;
import app.theblu.cashflow.cs.domain.template.MsgDataField;
import app.theblu.cashflow.cs.domain.template.MsgTemplate;
import app.theblu.cashflow.cs.domain.org.FiMsgIntent;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "msg_template")
public class MsgTemplateDboMg {
    @Id
    private String id;
    @Indexed
    private String orgId;
    private FiChannel fiChannel;
    private FiMsgIntent fiMsgIntent;
    private String patternExpression;
    private boolean patternCaseSensitive;
    private boolean patternFullTextSearch;
    private Map<MsgDataField, Integer> dataFieldMapping = new HashMap<>();
    private String dateFormat;
    private String timeFormat;
    private String sampleData;
    private boolean ignoreTemplate;
    private boolean ignoreMsg;
    private List<String> tags = new LinkedList<>();
    private long createdAt;

    public static MsgTemplateDboMg from(MsgTemplate other) {
        var temp = StaticContext.INSTANCE.getModelMapper().map(other, MsgTemplateDboMg.class);
        temp.setPatternExpression(other.getPattern().getExpression());
        temp.setPatternCaseSensitive(other.getPattern().getCaseSensitive());
        temp.setPatternFullTextSearch(other.getPattern().getFullTextSearch());
        return temp;
    }

    public MsgTemplate to() {
        var temp = StaticContext.INSTANCE.getModelMapper().map(this, MsgTemplate.class);
        temp.setPattern(new JPattern(this.patternExpression, this.patternCaseSensitive, this.patternFullTextSearch));
        return temp;
    }
}
