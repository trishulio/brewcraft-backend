package io.company.brewcraft.model;

import java.util.Collection;

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

import io.company.brewcraft.service.MoneyService;
import io.company.brewcraft.service.MoneySupplier;
import io.company.brewcraft.service.mapper.MoneyMapper;

@Entity(name = "TAX")
@Table
public class Tax extends BaseModel implements MoneySupplier {
    public static final String FIELD_AMOUNT = "amount";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_generator")
    @SequenceGenerator(name = "tax_generator", sequenceName = "tax_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amount_id", referencedColumnName = "id")
    private MoneyEntity amount;

    public Tax() {
    }

    public Tax(Money amount) {
        this();
        setAmount(amount);
    }

    @Override
    public Money getAmount() {
        return MoneyMapper.INSTANCE.fromEntity(amount);
    }

    public void setAmount(Money amount) {
        this.amount = MoneyMapper.INSTANCE.toEntity(amount);
    }

    public static Tax total(Collection<Tax> taxes) {
        Tax combined = null;
        Money total = MoneyService.total(taxes);
        if (total != null) {
            combined = new Tax(total);
        }
        return combined;
    }
}
