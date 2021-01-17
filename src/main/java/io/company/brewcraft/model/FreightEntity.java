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

@Entity(name = "FREIGHT")
@Table
public class FreightEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_AMOUNT = "amount";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freight_generator")
    @SequenceGenerator(name = "freight_generator", sequenceName = "freight_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amount_id", referencedColumnName = "id")
    private MoneyEntity amount;

    public FreightEntity() {

    }

    public FreightEntity(Long id) {
        this();
        setId(id);
    }

    public FreightEntity(Long id, MoneyEntity amount) {
        this(id);
        setAmount(amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoneyEntity getAmount() {
        return amount;
    }

    public void setAmount(MoneyEntity amount) {
        this.amount = amount;
    }

}
