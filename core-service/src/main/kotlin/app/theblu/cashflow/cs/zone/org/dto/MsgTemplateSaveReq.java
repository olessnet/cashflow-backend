package app.theblu.cashflow.cs.zone.org.dto;


import app.theblu.cashflow.cs.config.StaticContext;
import app.theblu.cashflow.cs.batteries.common.JPattern;
import app.theblu.cashflow.cs.domain.org.FiChannel;
import app.theblu.cashflow.cs.domain.template.MsgDataField;
import app.theblu.cashflow.cs.domain.template.MsgTemplate;
import app.theblu.cashflow.cs.domain.org.FiMsgIntent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class MsgTemplateSaveReq {
    private String id;
    @NotBlank(message = "you must specify an organization for this template")
    private String orgId;
    @NotNull(message = "you must specify an fiChannel")
    private FiChannel fiChannel;
    @NotNull(message = "you must specify message intent")
    private FiMsgIntent fiMsgIntent;
    @NotBlank(message = "you must specify pattern expression")
    private String patternExpression;
    private boolean patternCaseSensitive;
    private boolean patternFullTextSearch;
    @NotNull(message = "data field mapping should not be null")
    private Map<MsgDataField, Integer> dataFieldMapping;
    private String dateFormat;
    private String timeFormat;
    @NotBlank(message = "you must provide sample data")
    private String sampleData;
    private boolean ignoreTemplate;
    private boolean ignoreMsg;
    private List<String> tags = new LinkedList<>();


    public JPattern getPattern(){
        return new JPattern(patternExpression, patternCaseSensitive, patternFullTextSearch);
    }

    public static MsgTemplateSaveReq from(MsgTemplate template){
        return StaticContext.INSTANCE.getModelMapper().map(template, MsgTemplateSaveReq.class);
    }

    public MsgTemplate to(){
        return StaticContext.INSTANCE.getModelMapper().map(this, MsgTemplate.class);
    }
}
