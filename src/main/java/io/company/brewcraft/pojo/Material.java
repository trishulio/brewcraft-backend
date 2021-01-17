package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BaseModel;

public class Material extends BaseModel {
    private Long id;

    public Material() {
        this(null);
    }

    public Material(Long id) {
        setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
