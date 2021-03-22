package io.company.brewcraft.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import io.company.brewcraft.model.BaseShipmentItem;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.UpdateShipmentItem;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.util.validator.Validator;

public class ShipmentItemService extends BaseService {

    public Collection<ShipmentItem> getAddItems(Validator validator, Collection<? extends BaseShipmentItem> additionItems) {
        Collection<ShipmentItem> targetItems = null;
        if (additionItems != null) {
            targetItems = additionItems.stream().map(addition -> {
                ShipmentItem item = new ShipmentItem();
                item.override(addition, getPropertyNames(BaseShipmentItem.class));
                return item;
            }).collect(Collectors.toSet());
        }
        
        validator.raiseErrors();
        return targetItems;
    }
    
    public Collection<ShipmentItem> getPutItems(Validator validator, Collection<ShipmentItem> existingItems, Collection<? extends UpdateShipmentItem> updateItems) {
        if (updateItems == null) {
            return null;
        }

        int maxPossibleTargetSize = existingItems == null ? 0 : existingItems.size() + updateItems.size();
        Collection<ShipmentItem> targetItems = new HashSet<>(maxPossibleTargetSize);

        // Separating the put payloads into additions (without Id param) and updates (with Ids that match and existing item)
        Collection<UpdateShipmentItem> updates = new HashSet<UpdateShipmentItem>(updateItems.size());
        for (UpdateShipmentItem update : updateItems) {
            if (update.getId() != null) {
                updates.add(update);
            } else {
                ShipmentItem item = new ShipmentItem();
                item.override(update, getPropertyNames(BaseShipmentItem.class));
                targetItems.add(item);
            }
        }
        
        existingItems = existingItems != null ? existingItems : new HashSet<>(0);
        Map<Long, ShipmentItem> existingItemsLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        updates.forEach(update -> {
            ShipmentItem item = existingItemsLookup.get(update.getId());
            if (validator.rule(item != null, "No existing item found with Id: %s. For adding a new item, don't include Id in the payload.", update.getId())) {
                item.override(update, getPropertyNames(UpdateShipmentItem.class));
                targetItems.add(item);
            }
        });
        
        validator.raiseErrors();
        return targetItems;
   }
    
    public Collection<ShipmentItem> getPatchItems(Validator validator, Collection<ShipmentItem> existingItems, Collection<? extends UpdateShipmentItem> updateItems) {
        existingItems = existingItems != null ? existingItems : new HashSet<>(0);
        Map<Long, ShipmentItem> existingItemsLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        Collection<ShipmentItem> targetItems = new HashSet<>(existingItems.size());

        updateItems.forEach(update -> {
            ShipmentItem item = existingItemsLookup.get(update.getId());
            if (validator.rule(item != null, "No existing item found with Id: %s. Use the put method to add new payload item. Patch can only perform patches on existing items.", update.getId())) {
                item.outerJoin(update, getPropertyNames(UpdateShipmentItem.class));
                targetItems.add(item);
            }
        });
        
        validator.raiseErrors();
        return targetItems;
    }
}
