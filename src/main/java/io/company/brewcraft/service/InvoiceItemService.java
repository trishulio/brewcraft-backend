package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.pojo.BaseInvoiceItem;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.UpdateInvoiceItem;
import io.company.brewcraft.util.validator.Validator;

public class InvoiceItemService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceItemService.class);

    public List<InvoiceItem> mergePut(Validator validator, List<InvoiceItem> existingItems, List<? extends UpdateInvoiceItem> updates) {
        final List<InvoiceItem> targetItems = new ArrayList<>();

        // Separating the ItemUpdate items into 'additions' and 'updates'
        Set<UpdateInvoiceItem> itemUpdates = new HashSet<>(updates.size());
        Set<BaseInvoiceItem> itemAdditions = new HashSet<>(updates.size());
        for (UpdateInvoiceItem item : updates) {
            if (item.getId() != null) {
                itemUpdates.add(item);
            } else {
                itemAdditions.add(item);
            }
        }

        existingItems = existingItems != null ? existingItems : new ArrayList<>(0);
        Map<Long, InvoiceItem> existingItemsMap = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        for (UpdateInvoiceItem itemUpdate: itemUpdates) {
            InvoiceItem targetItem = existingItemsMap.get(itemUpdate.getId());
            boolean isNotNull = validator.rule(targetItem != null, "No existing invoice item found with Id: %s. To add a new item to the invoice, don't include the version and id in the payload.", itemUpdate.getId());
            if (isNotNull) {
                targetItem.override(itemUpdate, getPropertyNames(UpdateInvoiceItem.class));
                targetItems.add(targetItem);
            }
        }

        for (BaseInvoiceItem itemAddition : itemAdditions) {
            InvoiceItem targetItem = new InvoiceItem();
            targetItem.override(itemAddition, getPropertyNames(BaseInvoiceItem.class));
            targetItems.add(targetItem);
        }
        
        return targetItems;
    }
    
    public List<InvoiceItem> mergePatch(Validator validator, List<InvoiceItem> existingItems, List<? extends UpdateInvoiceItem> patches) {
        final List<InvoiceItem> targetItems = new ArrayList<>();

        Set<UpdateInvoiceItem> itemUpdates = new HashSet<>(patches.size());
        AtomicInteger additionCount = new AtomicInteger(0);

        for (UpdateInvoiceItem item : patches) {
            if (item.getId() != null) {
                itemUpdates.add(item);
            } else {
                additionCount.getAndIncrement();
            }
        }
        validator.rule(additionCount.intValue() <= 0, "%s InvoiceItem payloads with no Id found. Patch can only be used to modify existing InvoiceItems.", additionCount.intValue());

        existingItems = existingItems != null ? existingItems : new ArrayList<>(0);
        Map<Long, InvoiceItem> existingItemsMap = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        for (UpdateInvoiceItem itemUpdate : itemUpdates) {
            InvoiceItem targetItem = existingItemsMap.get(itemUpdate.getId());
            validator.rule(targetItem != null, "No existing invoice item found with Id: %s.", itemUpdate.getId());
            if (targetItem != null) {
                targetItem.outerJoin(itemUpdate, getPropertyNames(UpdateInvoiceItem.class));
                targetItems.add(targetItem);
            }
        }
        
        return targetItems;
    }

    public List<InvoiceItem> addList(Validator validator, List<? extends BaseInvoiceItem> additions) {
        List<InvoiceItem> targetItems = null;
        if (additions != null) {
            targetItems = additions.stream().map(i -> {
                InvoiceItem item = new InvoiceItem();
                log.info("Applying properties of InvoiceItem: {} to new item", i);
                item.override(i, getPropertyNames(BaseInvoiceItem.class));
                return item;
            }).collect(Collectors.toList());
        }

        return targetItems;
    }
}
