package io.company.brewcraft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.company.brewcraft.model.BaseShipmentItem;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.UpdateShipmentItem;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public class ShipmentItemService extends BaseService {

    private UtilityProvider utilProvider;

    public ShipmentItemService(UtilityProvider utilProvider) {
        this.utilProvider = utilProvider;
    }

    public List<ShipmentItem> getAddItems(List<? extends BaseShipmentItem> additionItems) {
        List<ShipmentItem> targetItems = null;
        if (additionItems != null) {
            targetItems = additionItems.stream().map(addition -> {
                ShipmentItem item = new ShipmentItem();
                item.override(addition, getPropertyNames(BaseShipmentItem.class));
                return item;
            }).collect(Collectors.toList());
        }

        return targetItems;
    }

    public List<ShipmentItem> getPutItems(List<ShipmentItem> existingItems, List<? extends UpdateShipmentItem> updateItems) {
        Validator validator = this.utilProvider.getValidator();

        if (updateItems == null) {
            return null;
        }

        int maxPossibleTargetSize = existingItems == null ? 0 : existingItems.size() + updateItems.size();
        List<ShipmentItem> targetItems = new ArrayList<>(maxPossibleTargetSize);

        // Separating the put payloads into additions (without Id param) and updates
        // (with Ids that match and existing item)
        List<UpdateShipmentItem> updates = new ArrayList<UpdateShipmentItem>(updateItems.size());
        for (UpdateShipmentItem update : updateItems) {
            if (update.getId() != null) {
                updates.add(update);
            } else {
                ShipmentItem item = new ShipmentItem();
                item.override(update, getPropertyNames(BaseShipmentItem.class));
                targetItems.add(item);
            }
        }

        existingItems = existingItems != null ? existingItems : new ArrayList<>(0);
        Map<Long, ShipmentItem> existingItemsLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        updates.forEach(update -> {
            ShipmentItem item = existingItemsLookup.get(update.getId());
            if (validator.rule(item != null, "No existing item found with Id: %s. For adding a new item, don't include Id in the payload.", update.getId())) {
                item.optimisicLockCheck(update);
                item.override(update, getPropertyNames(UpdateShipmentItem.class));
                targetItems.add(item);
            }
        });

        validator.raiseErrors();
        return targetItems;
    }

    public List<ShipmentItem> getPatchItems(List<ShipmentItem> existingItems, List<? extends UpdateShipmentItem> updateItems) {
        Validator validator = this.utilProvider.getValidator();

        existingItems = existingItems == null ? new ArrayList<>() : existingItems;
        List<ShipmentItem> targetItems = existingItems.stream().collect(Collectors.toList());

        if (updateItems != null) {
            Map<Long, ShipmentItem> existingItemsLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

            updateItems.forEach(update -> {
                ShipmentItem item = existingItemsLookup.get(update.getId());
                if (validator.rule(item != null, "No existing item found with Id: %s. Use the put method to add new payload item. Patch can only perform patches on existing items.", update.getId())) {
                    item.optimisicLockCheck(update);
                    item.outerJoin(update, getPropertyNames(UpdateShipmentItem.class));
                }
            });
        }

        validator.raiseErrors();
        return targetItems;
    }
}
