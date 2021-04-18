package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderAccessor;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedPurchaseOrderRepositoryImpl implements EnhancedPurchaseOrderRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedPurchaseOrderRepositoryImpl.class);
    
    private PurchaseOrderRepository poRepo;
    
    @Autowired
    public EnhancedPurchaseOrderRepositoryImpl(PurchaseOrderRepository poRepo) {
        this.poRepo = poRepo;
    }

    @Override
    public void refreshAccessors(Collection<? extends PurchaseOrderAccessor> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<Long, List<PurchaseOrderAccessor>> lookupAccessorByPoId = accessors.stream().filter(accessor -> accessor != null && accessor.getPurchaseOrder() != null).collect(Collectors.groupingBy(accessor -> accessor.getPurchaseOrder().getId()));

            List<PurchaseOrder> pos = poRepo.findAllById(lookupAccessorByPoId.keySet());

            if (lookupAccessorByPoId.keySet().size() != pos.size()) {
                List<Long> poIds = pos.stream().map(po -> po.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all orders in Id-Set: %s. PurchaseOrders found with Ids: %s", lookupAccessorByPoId.keySet(), poIds));
            }

            accessors.forEach(accessor -> accessor.setPurchaseOrder(null));
            pos.forEach(po -> lookupAccessorByPoId.get(po.getId()).forEach(accessor -> accessor.setPurchaseOrder(po)));
        }
    }
}
