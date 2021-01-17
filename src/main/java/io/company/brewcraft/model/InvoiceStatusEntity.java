package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "INVOICE_STATUS")
@Table
public class InvoiceStatusEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    
    public static final String DEFAULT_STATUS_NAME = "Pending";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_status_generator")
    @SequenceGenerator(name = "invoice_status_generator", sequenceName = "invoice_status_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public InvoiceStatusEntity() {
    }

    public InvoiceStatusEntity(Long id) {
        setId(id);
    }

    public InvoiceStatusEntity(Long id, String name) {
        this(id);
        setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
