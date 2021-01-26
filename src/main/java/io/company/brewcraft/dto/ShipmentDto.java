package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentDto extends BaseDto {
    private Long id;
    private String shipmentNumber;

    public ShipmentDto() {
    }

    public ShipmentDto(Long id) {
        this();
        setId(id);
    }

    public ShipmentDto(Long id, String shipmentNumber) {
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
