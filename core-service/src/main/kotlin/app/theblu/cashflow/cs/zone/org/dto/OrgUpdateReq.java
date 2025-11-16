package app.theblu.cashflow.cs.zone.org.dto;

import app.theblu.cashflow.cs.config.StaticContext;
import app.theblu.cashflow.cs.domain.org.Org;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.*;

@Data
public class OrgUpdateReq {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    private String shortName;
    @NotNull
    private Set<String> transactionalHeaders = new HashSet<>();
    @NotNull
    private Set<String> promotionalHeaders = new HashSet<>();
    private boolean banking;
    private boolean verified;
    private boolean ignore;
    @NotNull
    private Map<String, String> tags = new HashMap<>();

    public static OrgUpdateReq from(Org other){
        return StaticContext.INSTANCE.getModelMapper().map(other, OrgUpdateReq.class);
    }

    public Org to(){
        return StaticContext.INSTANCE.getModelMapper().map(this, Org.class);
    }
}
