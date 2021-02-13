package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SUPPLIER")
public class SupplierEntity extends BaseEntity {
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
    @JsonBackReference
    private List<SupplierContactEntity> contacts;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private SupplierAddressEntity address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public SupplierEntity() {

    }

    public SupplierEntity(Long id, String name, List<SupplierContactEntity> contacts, SupplierAddressEntity address, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        setId(id);
        setName(name);
        setContacts(contacts);
        setAddress(address);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
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

    public List<SupplierContactEntity> getContacts() {
        return contacts;
    }

    public void setContacts(List<SupplierContactEntity> contacts) {
        if (contacts != null) {
            contacts.stream().forEach(contact -> contact.setSupplier(this));
        }
        
        if (this.getContacts() != null) {
            this.getContacts().clear();
            this.getContacts().addAll(contacts);
        } else {
            this.contacts = contacts;
        }
    }

    public SupplierAddressEntity getAddress() {
        return address;
    }

    public void setAddress(SupplierAddressEntity address) {
        this.address = address;
    }

    public void addContact(SupplierContactEntity contact) {
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
    
    public void removeContect(SupplierContactEntity contact) {
        if (contacts != null) {
            contact.setSupplier(null);
            contacts.remove(contact);
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
