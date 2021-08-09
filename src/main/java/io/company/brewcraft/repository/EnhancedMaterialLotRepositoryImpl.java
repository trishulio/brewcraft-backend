package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.MaterialLotAccessor;

public class EnhancedMaterialLotRepositoryImpl implements EnhancedMaterialLotRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMaterialLotRepositoryImpl.class);
    
    private InvoiceItemRepository itemRepo;
    private StorageRepository storageRepo;
    
    private final AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> refresher;

    public EnhancedMaterialLotRepositoryImpl(InvoiceItemRepository itemRepo, StorageRepository storageRepo, AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> refresher) {
        this.itemRepo = itemRepo;
        this.storageRepo = storageRepo;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<MaterialLot> lots) {
        this.itemRepo.refreshAccessors(lots);
        this.storageRepo.refreshAccessors(lots);
    }
    
    @Override
    public void refreshAccessors(Collection<? extends MaterialLotAccessor> accessors) {
        refresher.refreshAccessors(accessors);
    }
}
