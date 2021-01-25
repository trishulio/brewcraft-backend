package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BaseModel;

public class Shipment extends BaseModel {
    private Long id;
    private String shipmentNumber;

    public Shipment() {
    }

    public Shipment(Long id) {
        this();
        setId(id);
    }

    public Shipment(Long id, String shipmentNumber) {
        this(id);
        setShipmentNumber(shipmentNumber);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }
}
