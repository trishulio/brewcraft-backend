package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.dto.UpdateSupplier;

@Entity
@Table(name = "SUPPLIER")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Supplier extends BaseEntity implements UpdateSupplier, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CONTACTS = "contacts";
    public static final String FIELD_ADDRESS = "address";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_generator")
    @SequenceGenerator(name = "supplier_generator", sequenceName = "supplier_sequence", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SupplierContact> contacts;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private SupplierAddress address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public Supplier() {
    }

    public Supplier(Long id) {
        setId(id);
    }

    public Supplier(Long id, String name, List<SupplierContact> contacts, SupplierAddress address, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setName(name);
        setContacts(contacts);
        setAddress(address);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<SupplierContact> getContacts() {
        return contacts;
    }

    @Override
    public void setContacts(List<SupplierContact> contacts) {
        if (contacts != null) {
            contacts.forEach(contact -> contact.setSupplier(this));
        }

        if (this.getContacts() != null) {
            this.getContacts().clear();
            this.getContacts().addAll(contacts);
        } else {
            this.contacts = contacts;
        }
    }

    @Override
    public SupplierAddress getAddress() {
        return address;
    }

    @Override
    public void setAddress(SupplierAddress address) {
        this.address = address;
    }

    public void addContact(SupplierContact contact) {
        if (contacts == null) {
            contacts = new ArrayList<>();
        }

        if (contact.getSupplier() != this) {
            contact.setSupplier(this);
        }

        if (!contacts.contains(contact)) {
            this.contacts.add(contact);
        }
    }

    public void removeContect(SupplierContact contact) {
        if (contacts != null) {
            contact.setSupplier(null);
            contacts.remove(contact);
        }
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

}
