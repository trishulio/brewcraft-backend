package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BaseModel;

public class Shipment extends BaseModel {
    private Long id;
    private String shipmentNumber;
    private String lotNumber;

    public Shipment() {
    }

    public Shipment(Long id) {
        this();
        setId(id);
    }

    public Shipment(Long id, String shipmentNumber, String lotNumber) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setLotNumber(lotNumber);
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

}
