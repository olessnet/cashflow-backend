package app.theblu.cashflow.cs.zone.org.repo.mongo.dbo;

import app.theblu.cashflow.cs.config.StaticContext;
import app.theblu.cashflow.cs.domain.org.OrgIgnoreString;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "orgIgnoreString")
public class OrgIgnoreStringDboMg {
    @Id
    private String id;
    private Set<String> stringSet = new HashSet<>();

    public static OrgIgnoreStringDboMg from(OrgIgnoreString other) {
        return StaticContext.INSTANCE.getModelMapper().map(other, OrgIgnoreStringDboMg.class);
    }

    public OrgIgnoreString to() {
        return StaticContext.INSTANCE.getModelMapper().map(this, OrgIgnoreString.class);
    }

}
