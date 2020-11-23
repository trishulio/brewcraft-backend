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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="SUPPLIER")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_generator")
    @SequenceGenerator(name="supplier_generator", sequenceName = "supplier_sequence", allocationSize = 1)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy="supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<SupplierContact> contacts;
    
    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private SupplierAddress address;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;
    
    public Supplier() {
        
    }
    
    public Supplier(Long id, String name, List<SupplierContact> contacts, SupplierAddress address,
            LocalDateTime created, LocalDateTime lastUpdated, Integer version) {
        this.id = id;
        this.name = name;
        this.contacts = contacts;
        this.address = address;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.version = version;
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

    public List<SupplierContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<SupplierContact> contacts) {
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
    
    public SupplierAddress getAddress() {
        return address;
    }

    public void setAddress(SupplierAddress address) {
        this.address = address;
    }

    public void addContact(SupplierContact contact) {
        if (contacts == null) {
            contacts = new ArrayList<>();
        }
        contact.setSupplier(this);
        contacts.add(contact);
    }
    
    public void removeContect(SupplierContact contact) {
        if (contacts != null) {
            contact.setSupplier(null);
            contacts.remove(contact);
        }
    }
    
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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
