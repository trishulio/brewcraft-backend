package io.company.brewcraft.model;

import javax.measure.Quantity;

import io.company.brewcraft.pojo.Material;

public interface BaseShipmentItem {
    public Quantity<?> getQuantity();

    public void setQuantity(Quantity<?> qty);

    public Material getMaterial();

    public void setMaterial(Material material);

    public Shipment getShipment();

    public void setShipment(Shipment shipment);
}
