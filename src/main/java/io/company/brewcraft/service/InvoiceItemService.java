package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.UpdateInvoiceItem;

public class InvoiceItemService extends BaseService implements UpdateService<Long, InvoiceItem, BaseInvoiceItem<?>, UpdateInvoiceItem<?>> {
    private static final Logger log = LoggerFactory.getLogger(InvoiceItemService.class);

    private final UpdateService<Long, InvoiceItem, BaseInvoiceItem<?>, UpdateInvoiceItem<?>> updateService;

    public InvoiceItemService(UpdateService<Long, InvoiceItem, BaseInvoiceItem<?>, UpdateInvoiceItem<?>> updateService) {
        this.updateService = updateService;
    }

    @Override
    public List<InvoiceItem> getAddEntities(List<BaseInvoiceItem<?>> additions) {
        return this.updateService.getAddEntities(additions);
    }

    @Override
    public List<InvoiceItem> getPutEntities(List<InvoiceItem> existingItems, List<UpdateInvoiceItem<?>> updates) {
        return this.updateService.getPutEntities(existingItems, updates);
    }

    @Override
    public List<InvoiceItem> getPatchEntities(List<InvoiceItem> existingItems, List<UpdateInvoiceItem<?>> patches) {
        return this.updateService.getPatchEntities(existingItems, patches);
    }
}
