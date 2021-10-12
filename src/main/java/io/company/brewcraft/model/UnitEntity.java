package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "qty_unit")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class UnitEntity extends BaseEntity {
    public static final String FIELD_SYMBOL = "symbol";
    public static final String FIELD_NAME = "name";

    @Id
    @Column(name = "symbol", unique = true, updatable = false, length = 4)
    private String symbol;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public UnitEntity() {
        this(null, null);
    }

    public UnitEntity(String symbol) {
        this(symbol, null);
    }

    public UnitEntity(String symbol, String name) {
        setName(name);
        setSymbol(symbol);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
