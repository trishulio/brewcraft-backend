package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

public interface BaseShipment<T extends BaseShipmentItem> {
    String getShipmentNumber();

    void setShipmentNumber(String shipmentNumber);

    String getLotNumber();

    void setLotNumber(String lotNumber);
    
    String getDescription();
    
    void setDescription(String description);

    ShipmentStatus getStatus();

    void setStatus(ShipmentStatus status);

    Invoice getInvoice();

    void setInvoice(Invoice invoice);

    LocalDateTime getDeliveryDueDate();

    void setDeliveryDueDate(LocalDateTime deliveryDueDate);

    LocalDateTime getDeliveredDate();

    void setDeliveredDate(LocalDateTime deliveredDate);

    List<T> getItems();

    void setItems(List<T> items);
}
