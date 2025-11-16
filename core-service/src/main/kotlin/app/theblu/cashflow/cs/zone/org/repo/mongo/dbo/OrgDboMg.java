package app.theblu.cashflow.cs.zone.org.repo.mongo.dbo;

import app.theblu.cashflow.cs.config.StaticContext;
import app.theblu.cashflow.cs.domain.org.Org;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;


@Data
@Document(collection = "org")
public class OrgDboMg {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    @Indexed(unique = false)
    private String shortName;
    @Indexed(sparse = true)
    private Set<String> transactionalHeaders = new HashSet<>();
    @Indexed(sparse = true)
    private Set<String> promotionalHeaders = new HashSet<>();
    private boolean banking;
    private boolean verified;
    private boolean ignore;
    private Map<String, String> tags = new HashMap<>();

    public static OrgDboMg from(Org other) {
        return StaticContext.INSTANCE.getModelMapper().map(other, OrgDboMg.class);
    }

    public Org to() {
        return StaticContext.INSTANCE.getModelMapper().map(this, Org.class);
    }
}
