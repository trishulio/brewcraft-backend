package io.company.brewcraft.model;

import javax.measure.Quantity;

import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.MaterialAccessor;

public interface BaseMaterialLot extends MaterialAccessor, InvoiceItemAccessor {
    public String getLotNumber();

    public void setLotNumber(String lotNumber);

    public Quantity<?> getQuantity();

    public void setQuantity(Quantity<?> qty);

    public Shipment getShipment();

    public void setShipment(Shipment shipment);
}
