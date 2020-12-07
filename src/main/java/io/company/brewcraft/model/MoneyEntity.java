package io.company.brewcraft.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class MoneyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "money_generator")
    @SequenceGenerator(name = "money_generator", sequenceName = "money_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Currency currency;

    @Column(name = "amount")
    private BigDecimal amount;

    public MoneyEntity() {
        this(null);
    }

    public MoneyEntity(Long id) {
        this(id, null, null);
    }

    public MoneyEntity(Long id, Currency currency, BigDecimal amount) {
        setId(id);
        setCurrency(currency);
        setAmount(amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}