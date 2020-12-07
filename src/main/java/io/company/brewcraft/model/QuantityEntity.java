package io.company.brewcraft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "QTY")
public class QuantityEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qty_generator")
    @SequenceGenerator(name = "qty_generator", sequenceName = "qty_sequence", allocationSize = 1)
    private Long id;
    private UnitEntity unit;
    private Number value;

    public QuantityEntity() {
        this(null);
    }

    public QuantityEntity(Long id) {
        this(id, null, null);
    }

    public QuantityEntity(Long id, UnitEntity unit, Number value) {
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

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }
}
