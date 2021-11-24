package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.service.ShipmentStatusAccessor;

public interface BaseShipment<T extends BaseMaterialLot<? extends BaseShipment<T>>> extends ShipmentStatusAccessor {
    final String ATTR_SHIPMENT_NUMBER = "shipmentNumber";
    final String ATTR_DESCRIPTION = "description";
    final String ATTR_DELIVERY_DUE_DATE = "deliveryDueDate";
    final String ATTR_DELIVERED_DATE = "deliveredDate";
    final String ATTR_LOTS = "lots";

    String getShipmentNumber();

    void setShipmentNumber(String shipmentNumber);

    String getDescription();

    void setDescription(String description);

    LocalDateTime getDeliveryDueDate();

    void setDeliveryDueDate(LocalDateTime deliveryDueDate);

    LocalDateTime getDeliveredDate();

    void setDeliveredDate(LocalDateTime deliveredDate);

    List<T> getLots();

    void setLots(List<T> lots);

    void setInvoiceItemsFromInvoice(Invoice invoice);
}
