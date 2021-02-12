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
@Table(name="FACILITY")
public class FacilityEntity extends BaseEntity {
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
    @JsonBackReference
    private List<EquipmentEntity> equipment;
    
    @OneToMany(mappedBy="facility", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<StorageEntity> storages;
        
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;
    
    public FacilityEntity() {
        
    }
    
    public FacilityEntity(Long id, String name, FacilityAddress address, String phoneNumber, String faxNumber, List<EquipmentEntity> equipment, List<StorageEntity> storages, LocalDateTime created, LocalDateTime lastUpdated, Integer version) {
        setId(id);
        setName(name);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setFaxNumber(faxNumber);
        setEquipment(equipment);
        setStorages(storages);
        setCreated(created);
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
    
    public FacilityAddress getAddress() {
        return address;
    }

    public void setAddress(FacilityAddress address) {
        this.address = address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }
    
    public List<EquipmentEntity> getEquipment() {
        return equipment;
    }
    
    public void setEquipment(List<EquipmentEntity> equipment) {
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
    
    public void addEquipment(EquipmentEntity equipment) {
        if (this.equipment == null) {
            this.equipment = new ArrayList<>();
        }
        equipment.setFacility(this);
        this.equipment.add(equipment);
    }
    
    public void removeEquipment(EquipmentEntity equipment) {
        if (this.equipment != null) {
            equipment.setFacility(null);
            this.equipment.remove(equipment);
        }
    }
    
    public List<StorageEntity> getStorages() {
        return storages;
    }
    
    public void setStorages(List<StorageEntity> storages) {
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
    
    public void addStorage(StorageEntity storage) {
        if (this.storages == null) {
            this.storages = new ArrayList<>();
        }
        storage.setFacility(this);
        storages.add(storage);
    }
    
    public void removeStorage(StorageEntity storage) {
        if (storages != null) {
            storage.setFacility(null);
            storages.remove(storage);
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