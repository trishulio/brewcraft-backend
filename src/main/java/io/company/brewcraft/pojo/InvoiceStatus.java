package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BaseModel;

public class InvoiceStatus extends BaseModel {
    private Long id;

    private String name;

    public InvoiceStatus() {
    }

    public InvoiceStatus(Long id) {
        setId(id);
    }

    public InvoiceStatus(Long id, String name) {
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
