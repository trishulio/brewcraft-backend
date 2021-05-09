package io.company.brewcraft.model;

import javax.measure.Quantity;

import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.MaterialAccessor;
import io.company.brewcraft.service.StorageAccessor;

public interface BaseMaterialLot<T extends BaseShipment<? extends BaseMaterialLot<T>>> extends MaterialAccessor, InvoiceItemAccessor, StorageAccessor {
    final String ATTR_LOT_NUMBER = "lotNumber";
    final String ATTR_QUANTITY = "quantity";
    final String ATTR_SHIPMENT = "shipment";

    public String getLotNumber();

    public void setLotNumber(String lotNumber);

    public Quantity<?> getQuantity();

    public void setQuantity(Quantity<?> qty);

    public T getShipment();

    public void setShipment(T shipment);
}
