package app.theblu.cashflow.cs.domain.org;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class OrgIgnoreString {
    private String id;
    private Set<String> stringSet = new HashSet<>();
}
