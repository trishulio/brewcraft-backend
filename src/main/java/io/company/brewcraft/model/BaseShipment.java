package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.service.ShipmentStatusAccessor;

public interface BaseShipment<T extends BaseMaterialLot> extends ShipmentStatusAccessor {
    String getShipmentNumber();

    void setShipmentNumber(String shipmentNumber);

    String getDescription();

    void setDescription(String description);

    LocalDateTime getDeliveryDueDate();

    void setDeliveryDueDate(LocalDateTime deliveryDueDate);

    LocalDateTime getDeliveredDate();

    void setDeliveredDate(LocalDateTime deliveredDate);

    List<T> getLots();

    void setLots(List<T> items);
}
