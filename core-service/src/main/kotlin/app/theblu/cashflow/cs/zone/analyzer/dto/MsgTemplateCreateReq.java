package app.theblu.cashflow.cs.zone.analyzer.dto;

import app.theblu.cashflow.cs.domain.org.FiChannel;
import app.theblu.cashflow.cs.domain.org.FiMsgIntent;
import app.theblu.cashflow.cs.domain.template.MsgDataField;
import app.theblu.cashflow.cs.zone.org.dto.MsgTemplateSaveReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class MsgTemplateCreateReq {
    @NotBlank(message = "you must specify Header")
    private String header;
    @NotNull(message = "you must specify channel")
    private FiChannel fiChannel;
    @NotNull(message = "you must specify intent")
    private FiMsgIntent fiMsgIntent;
    @NotBlank(message = "you must specify expression")
    private String patternExpression;
    private boolean patternCaseSensitive;
    private boolean patternFullTextSearch;
    @NotNull(message = "you must provide sample data")
    private Map<MsgDataField, Integer> dataFieldMapping;
    private String dateFormat;
    private String timeFormat;
    @NotBlank(message = "you must provide sample data")
    private String sampleData;
    private boolean ignoreTemplate;
    private boolean ignoreMsg;
    private List<String> tags = new LinkedList<>();


    public MsgTemplateSaveReq toSaveReq() {
        MsgTemplateSaveReq req = new MsgTemplateSaveReq();
        req.setFiChannel(fiChannel);
        req.setFiMsgIntent(fiMsgIntent);
        req.setPatternExpression(patternExpression);
        req.setPatternCaseSensitive(patternCaseSensitive);
        req.setPatternFullTextSearch(patternFullTextSearch);
        req.setDataFieldMapping(dataFieldMapping);
        req.setDateFormat(dateFormat);
        req.setTimeFormat(timeFormat);
        req.setSampleData(sampleData);
        req.setIgnoreTemplate(ignoreTemplate);
        req.setIgnoreMsg(ignoreMsg);
        req.setTags(tags);
        return req;
    }
}
