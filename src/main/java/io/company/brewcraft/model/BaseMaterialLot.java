package io.company.brewcraft.model;

import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.StorageAccessor;

public interface BaseMaterialLot<T extends BaseShipment<? extends BaseMaterialLot<T>>> extends InvoiceItemAccessor, StorageAccessor, QuantityAccessor {
    final String ATTR_INDEX = "index";
    final String ATTR_LOT_NUMBER = "lotNumber";
    final String ATTR_QUANTITY = "quantity";
    final String ATTR_SHIPMENT = "shipment";

    Integer getIndex();

    void setIndex(Integer index);

    String getLotNumber();

    void setLotNumber(String lotNumber);

    T getShipment();

    void setShipment(T shipment);
}
