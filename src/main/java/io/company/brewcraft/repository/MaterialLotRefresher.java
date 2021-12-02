package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.MaterialLotAccessor;
import io.company.brewcraft.service.StorageAccessor;

public class MaterialLotRefresher implements Refresher<MaterialLot, MaterialLotAccessor> {
    private static final Logger log = LoggerFactory.getLogger(MaterialLotRefresher.class);

    private final Refresher<InvoiceItem, InvoiceItemAccessor> itemRefresher;
    private final Refresher<Storage, StorageAccessor> storageRefresher;

    private final AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> refresher;

    public MaterialLotRefresher(Refresher<InvoiceItem, InvoiceItemAccessor> itemRefresher, Refresher<Storage, StorageAccessor> storageRefresher, AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> refresher) {
        this.itemRefresher = itemRefresher;
        this.storageRefresher = storageRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<MaterialLot> lots) {
        this.itemRefresher.refreshAccessors(lots);
        this.storageRefresher.refreshAccessors(lots);
    }

    @Override
    public void refreshAccessors(Collection<? extends MaterialLotAccessor> accessors) {
        refresher.refreshAccessors(accessors);
    }
}
