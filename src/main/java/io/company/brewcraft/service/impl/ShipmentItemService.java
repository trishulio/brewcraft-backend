package io.company.brewcraft.service.impl;

import java.util.Collection;

import io.company.brewcraft.pojo.BaseShipmentItem;
import io.company.brewcraft.pojo.ShipmentItem;
import io.company.brewcraft.pojo.UpdateShipmentItem;
import io.company.brewcraft.util.validator.Validator;

public class ShipmentItemService {

    public Collection<ShipmentItem> getAddItems(Validator validator, Collection<? extends BaseShipmentItem> additionItems) {
        return null;
    }
    
    public Collection<ShipmentItem> getPutList(Validator validator, Collection<ShipmentItem> existingItems, Collection<? extends UpdateShipmentItem> updateItems) {
        return null;
    }
    
    public Collection<ShipmentItem> getPatchList(Validator validator, Collection<ShipmentItem> existingItems, Collection<? extends UpdateShipmentItem> updateItems) {
        return null;
    }
}
