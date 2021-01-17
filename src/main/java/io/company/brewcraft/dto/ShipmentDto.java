package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentDto {
    private Long id;
    private String shipmentNumber;
    private String lotNumber;

    public ShipmentDto() {
    }

    public ShipmentDto(Long id) {
        this();
        setId(id);
    }

    public ShipmentDto(Long id, String shipmentNumber, String lotNumber) {
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
