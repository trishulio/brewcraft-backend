package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.MaterialLotAccessor;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedMaterialLotRepositoryImpl implements EnhancedMaterialLotRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMaterialLotRepositoryImpl.class);
    
    private InvoiceItemRepository itemRepo;
    private MaterialRepository materialRepo;
    private MaterialLotRepository lotRepo;
    private StorageRepository storageRepo;
    
    public EnhancedMaterialLotRepositoryImpl(MaterialLotRepository lotRepo, InvoiceItemRepository itemRepo, MaterialRepository materialRepo, StorageRepository storageRepo) {
        this.lotRepo = lotRepo;
        this.itemRepo = itemRepo;
        this.materialRepo = materialRepo;
        this.storageRepo = storageRepo;
    }

    @Override
    public void refresh(Collection<MaterialLot> lots) {
        this.itemRepo.refreshAccessors(lots);
        this.materialRepo.refreshAccessors(lots);
        this.storageRepo.refreshAccessor(lots);
    }
    
    @Override
    public void refreshAccessors(Collection<? extends MaterialLotAccessor> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<Long, List<MaterialLotAccessor>> lookupAccessorsByMaterialId = accessors.stream().filter(accessor -> accessor != null && accessor.getMaterialLot() != null).collect(Collectors.groupingBy(accessor -> accessor.getMaterialLot().getId()));

            List<MaterialLot> lots = lotRepo.findAllById(lookupAccessorsByMaterialId.keySet());

            if (lookupAccessorsByMaterialId.keySet().size() != lots.size()) {
                List<Long> lotIds = lots.stream().map(material -> material.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all materials in Id-Set: %s. Materials found with Ids: %s", lookupAccessorsByMaterialId.keySet(), lotIds));
            }

            accessors.forEach(accessor -> accessor.setMaterialLot(null));
            lots.forEach(lot -> lookupAccessorsByMaterialId.get(lot.getId()).forEach(accessor -> accessor.setMaterialLot(lot)));
        }
    }
}
