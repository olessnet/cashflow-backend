package app.theblu.cashflow.cs.domain;

import lombok.Data;

@Data
public class Category {
    private String id;
    private String parentId;
    private String name;
    private String description;
    private String icon;
    private TrxType trxType;
    private String examples;

    public enum TrxType {
        EXPENSE, INCOME
    }
}
