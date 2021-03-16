package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BaseModel;

public class ShipmentStatus extends BaseModel {
    private Long id;
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
