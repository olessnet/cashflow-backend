package app.theblu.cashflow.cs.domain.org;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.*;

// https://rbi.org.in/commonman/English/Scripts/BanksInIndia.aspx#IB
@Data
public class Org {
    private String id;
    private String name;
    private String shortName;
    private Set<String> transactionalHeaders = new HashSet<>();
    private Set<String> promotionalHeaders = new HashSet<>();
    private boolean banking;
    private boolean verified;
    private boolean ignore;
    private Map<String, String> tags = new HashMap<>();

    public boolean isHeadersChanged(Org other) {
        Set<String> th = new HashSet<>(transactionalHeaders);
        Set<String> ph = new HashSet<>(promotionalHeaders);
        Set<String> oth = new HashSet<>(other.transactionalHeaders);
        Set<String> oph = new HashSet<>(other.promotionalHeaders);

        return !th.equals(oth) || !ph.equals(oph);
    }

    @JsonIgnore
    public Set<String> getHeaders() {
        var set = new HashSet<String>();
        set.addAll(transactionalHeaders);
        set.addAll(promotionalHeaders);
        return set;
    }

    public void updateFrom(Org other) {
        this.transactionalHeaders.addAll(other.getTransactionalHeaders());
        this.promotionalHeaders.addAll(other.getPromotionalHeaders());
    }

    public boolean isTransactionalHeader(String header) {
        return this.transactionalHeaders.contains(header);
    }

    public boolean isPromotionalHeader(String header) {
        return this.promotionalHeaders.contains(header);
    }
}
