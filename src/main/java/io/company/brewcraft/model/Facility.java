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

import io.company.brewcraft.dto.UpdateFacility;

@Entity
@Table(name="FACILITY")
public class Facility extends BaseModel implements UpdateFacility, Identified<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_PHONE_NUMBER = "phoneNumber";
    public static final String FIELD_FAX_NUMBER = "faxNumber";
    public static final String FIELD_EQUIPMENT = "equipment";
    public static final String FIELD_STORAGES = "storages";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_generator")
    @SequenceGenerator(name="facility_generator", sequenceName = "facility_sequence", allocationSize = 1)
    private Long id;
    
    private String name;
    
    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private FacilityAddress address;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "fax_number")
    private String faxNumber;
    
    @OneToMany(mappedBy="facility", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Equipment> equipment;
    
    @OneToMany(mappedBy="facility", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Storage> storages;
        
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;
    
    public Facility() {
    }

    public Facility(Long id) {
        setId(id);
    }
    
    public Facility(Long id, String name, FacilityAddress address, String phoneNumber, String faxNumber, List<Equipment> equipment, List<Storage> storages, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setName(name);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setFaxNumber(faxNumber);
        setEquipment(equipment);
        setStorages(storages);
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
    public FacilityAddress getAddress() {
        return address;
    }

    @Override
    public void setAddress(FacilityAddress address) {
        this.address = address;
    }
    
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getFaxNumber() {
        return faxNumber;
    }

    @Override
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }
    
    @Override
    public List<Equipment> getEquipment() {
        return equipment;
    }
    
    @Override
    public void setEquipment(List<Equipment> equipment) {
        if (equipment != null) {
            equipment.stream().forEach(eqpt -> eqpt.setFacility(this));
        }
        
        if (this.getEquipment() != null) {
            this.getEquipment().clear();
            this.getEquipment().addAll(equipment);
        } else {
            this.equipment = equipment;
        }
    }
        
    public void addEquipment(Equipment eq) {
        if (equipment == null) {
            equipment = new ArrayList<>();
        }
        
        if (eq.getFacility() != this) {
            eq.setFacility(this);
        }

        if (!equipment.contains(eq)) {
            this.equipment.add(eq);
        }
    }
    
    public void removeEquipment(Equipment equipment) {
        if (this.equipment != null) {
            equipment.setFacility(null);
            this.equipment.remove(equipment);
        }
    }
    
    @Override
    public List<Storage> getStorages() {
        return storages;
    }
    
    @Override
    public void setStorages(List<Storage> storages) {
        if (storages != null) {
            storages.stream().forEach(storage -> storage.setFacility(this));
        }
        
        if (this.getStorages() != null) {
            this.getStorages().clear();
            this.getStorages().addAll(storages);
        } else {
            this.storages = storages;
        }    
    }
    
    public void addStorage(Storage storage) {
        if (storages == null) {
            storages = new ArrayList<>();
        }
        
        if (storage.getFacility() != this) {
            storage.setFacility(this);
        }

        if (!storages.contains(storage)) {
            this.storages.add(storage);
        }
    }
    
    public void removeStorage(Storage storage) {
        if (storages != null) {
            storage.setFacility(null);
            storages.remove(storage);
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