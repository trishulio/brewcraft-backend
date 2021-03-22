package io.company.brewcraft.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.joda.money.Money;

import io.company.brewcraft.service.mapper.MoneyMapper;

@Entity(name = "freight")
@Table
public class Freight extends BaseModel {
    public static final String FIELD_ID = "id";
    public static final String FIELD_AMOUNT = "amount";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freight_generator")
    @SequenceGenerator(name = "freight_generator", sequenceName = "freight_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amount_id", referencedColumnName = "id")
    private MoneyEntity amount;

    public Freight() {
    }

    public Freight(Long id) {
        setId(id);
    }

    public Freight(Long id, Money amount) {
        this(id);
        setAmount(amount);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getAmount() {
        return MoneyMapper.INSTANCE.fromEntity(this.amount);
    }

    public void setAmount(Money amount) {
        this.amount = MoneyMapper.INSTANCE.toEntity(amount);
    }
}
