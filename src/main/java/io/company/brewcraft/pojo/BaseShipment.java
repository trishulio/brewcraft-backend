package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.Collection;

import io.company.brewcraft.model.InvoiceEntity;

public interface BaseShipment<T extends BaseShipmentItem> {
    String getShipmentNumber();

    void setShipmentNumber(String shipmentNumber);

    String getLotNumber();

    void setLotNumber(String lotNumber);

    ShipmentStatus getStatus();

    void setStatus(ShipmentStatus status);

    InvoiceEntity getInvoice();

    void setInvoice(InvoiceEntity invoice);

    LocalDateTime getDeliveryDueDate();

    void setDeliveryDueDate(LocalDateTime deliveryDueDate);

    LocalDateTime getDeliveredDate();

    void setDeliveredDate(LocalDateTime deliveredDate);

    Collection<T> getItems();

    void setItems(Collection<T> items);
}
