package app.theblu.cashflow.cs.domain.template;

import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.batteries.common.JPattern;
import app.theblu.cashflow.cs.domain.org.FiChannel;
import app.theblu.cashflow.cs.domain.org.FiMsgIntent;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Data
public class MsgTemplate {
    private String id;
    private String orgId;
    private FiChannel fiChannel;
    private FiMsgIntent fiMsgIntent;
    private JPattern pattern;
    private Map<MsgDataField, Integer> dataFieldMapping;
    private String dateFormat;
    private String timeFormat;
    private String sampleData;
    private boolean ignoreTemplate;
    private List<String> tags = new LinkedList<>();
    private long createdAt;


    public Map<MsgDataField, String> data(String text) {
        Map<MsgDataField, String> map = new HashMap<>();
        List<String> data = this.pattern.data(text);
        for (MsgDataField field : dataFieldMapping.keySet()) {
            map.put(field, data.get(dataFieldMapping.get(field)));
        }
        return map;
    }

    public boolean hasMatch(Msg msg) {
        return pattern.hasMatch(msg.getBody());
    }
}
