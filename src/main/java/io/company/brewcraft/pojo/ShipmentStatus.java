package io.company.brewcraft.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import io.company.brewcraft.model.BaseModel;

public class ShipmentStatus extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_status_generator")
    @SequenceGenerator(name = "shipment_status_generator", sequenceName = "shipment_status_sequence", allocationSize = 1)
    private Long id;
    
    @Column(name = "name")
    private String name;

    public ShipmentStatus() {
    }

    public ShipmentStatus(String name) {
        this();
        setName(name);
    }

    public ShipmentStatus(Long id, String name) {
        this(name);
        setId(id);
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
