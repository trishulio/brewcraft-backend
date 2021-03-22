package io.company.brewcraft.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "qty")
public class QuantityEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_UNIT = "unit";
    public static final String FIELD_VALUE = "value";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qty_generator")
    @SequenceGenerator(name = "qty_generator", sequenceName = "qty_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_symbol", referencedColumnName = "symbol")
    private UnitEntity unit;
    
    @Column(name = "value", precision = 20, scale = 4)
    private BigDecimal value;

    public QuantityEntity() {
        this(null);
    }

    public QuantityEntity(Long id) {
        this(id, null, null);
    }

    public QuantityEntity(Long id, UnitEntity unit, BigDecimal value) {
        setId(id);
        setUnit(unit);
        setValue(value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitEntity getUnit() {
        return unit;
    }

    public void setUnit(UnitEntity unit) {
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
