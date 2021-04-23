package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "invoice_status")
@Table
public class InvoiceStatus extends BaseModel implements UpdateInvoiceStatus, Audited, Identified<String> {
    public static final String FIELD_NAME = "name";

    public static final String DEFAULT_STATUS_NAME = "PENDING";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_status_generator")
    @SequenceGenerator(name = "invoice_status_generator", sequenceName = "invoice_status_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private Integer version;

    public InvoiceStatus() {
    }

    public InvoiceStatus(String name) {
        setName(name);
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public void setId(String id) {
        setName(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            name = DEFAULT_STATUS_NAME;
        }
        this.name = name;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
