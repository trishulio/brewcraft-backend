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

@Entity(name = "money")
public class MoneyEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_CURRENCY = "currency";
    public static final String FIELD_AMOUNT = "amount";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "money_generator")
    @SequenceGenerator(name = "money_generator", sequenceName = "money_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", referencedColumnName = "numeric_code")
    private Currency currency;

    @Column(name = "amount", precision = 20, scale = 4)
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