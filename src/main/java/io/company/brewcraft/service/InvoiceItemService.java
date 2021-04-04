package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public class InvoiceItemService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceItemService.class);

    private UtilityProvider utilProvider;

    public InvoiceItemService(UtilityProvider utilProvider) {
        this.utilProvider = utilProvider;
    }

    public List<InvoiceItem> getPutItems(List<InvoiceItem> existingItems, List<? extends UpdateInvoiceItem> updates) {
        Validator validator = this.utilProvider.getValidator();

        if (updates == null) {
            return null;
        }

        List<InvoiceItem> targetItems = new ArrayList<>();        
        List<UpdateInvoiceItem> itemUpdates = new ArrayList<>(updates.size());
        
        updates.forEach(update -> {
            if (update.getId() == null) {
                InvoiceItem item = new InvoiceItem();
                item.override(update, getPropertyNames(BaseInvoiceItem.class));
                targetItems.add(item);

            } else {
                itemUpdates.add(update);
            }
        });
        
        existingItems = existingItems == null ? new ArrayList<>(0) : existingItems;
        Map<Long, InvoiceItem> idToItemLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
        
        itemUpdates.forEach(update -> {
            InvoiceItem targetItem = idToItemLookup.get(update.getId());
            if (validator.rule(targetItem != null, "No existing invoice item found with Id: %s. To add a new item to the invoice, don't include the version and id in the payload.", update.getId())) {
                targetItem.override(update, getPropertyNames(UpdateInvoiceItem.class));
                targetItems.add(targetItem);
            }
        });

        return targetItems;
    }

    public List<InvoiceItem> getPatchItems(List<InvoiceItem> existingItems, List<? extends UpdateInvoiceItem> patches) {
        Validator validator = this.utilProvider.getValidator();

        existingItems = existingItems == null ? new ArrayList<>() : existingItems;
        final List<InvoiceItem> targetItems = existingItems.stream().collect(Collectors.toList());

        if (patches != null) {
            Map<Long, InvoiceItem> idToItemLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

            patches.forEach(patch -> {
                InvoiceItem targetItem = idToItemLookup.get(patch.getId());
                if (validator.rule(targetItem != null, "No existing invoice item found with Id: %s.", patch.getId())) {
                    targetItem.outerJoin(patch, getPropertyNames(UpdateInvoiceItem.class));
                }
            });
        }

        validator.raiseErrors();
        return targetItems;
    }

    public List<InvoiceItem> getAddItems(Collection<? extends BaseInvoiceItem> additions) {
        Validator validator = this.utilProvider.getValidator();
        List<InvoiceItem> targetItems = null;
        if (additions != null) {
            targetItems = additions.stream().map(i -> {
                InvoiceItem item = new InvoiceItem();
                log.debug("Applying properties of InvoiceItem: {} to new item", i);
                item.override(i, getPropertyNames(BaseInvoiceItem.class));
                return item;
            }).collect(Collectors.toList());
        }

        return targetItems;
    }
}
