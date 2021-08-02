package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class PurchaseOrderService extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderService.class);

    private PurchaseOrderRepository repo;

    public PurchaseOrderService(PurchaseOrderRepository repo) {
        this.repo = repo;
    }

    public boolean exists(Set<Long> ids) {
        return this.repo.existsByIds(ids);
    }

    public boolean exists(Long id) {
        return this.repo.existsById(id);
    }
    
    public Page<PurchaseOrder> getAllPurchaseOrders(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> orderNumbers,
        Set<Long> supplierIds,
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size
    ) {
        Specification<PurchaseOrder> spec = SpecificationBuilder.builder()
                                                                .in(PurchaseOrder.FIELD_ID, ids)
                                                                .not().in(PurchaseOrder.FIELD_ID, excludeIds)
                                                                .in(PurchaseOrder.FIELD_ORDER_NUMBER, orderNumbers)
                                                                .in(new String[] { PurchaseOrder.FIELD_SUPPLIER, Supplier.FIELD_ID }, supplierIds)
                                                                .build();
        
        PageRequest pageRequest = pageRequest(sort, orderAscending, page, size);
        Page<PurchaseOrder> orders = this.repo.findAll(spec, pageRequest);
        
        return orders;
    }

    public PurchaseOrder getPurchaseOrder(Long id) {
        PurchaseOrder po = null;

        Optional<PurchaseOrder> opt = this.repo.findById(id);
        if (opt.isPresent()) {
            po = opt.get();
        }

        return po;
    }

    public PurchaseOrder add(BasePurchaseOrder addition) {
        PurchaseOrder order = new PurchaseOrder();

        order.override(addition, getPropertyNames(BasePurchaseOrder.class));

        repo.refresh(List.of(order));

        return repo.saveAndFlush(order);
    }
    
    public PurchaseOrder put(Long purchaseOrderId, UpdatePurchaseOrder update) {
        log.debug("Updating the PurchaseOrder with Id: {}", purchaseOrderId);

        PurchaseOrder existing = getPurchaseOrder(purchaseOrderId);
        Class<? super PurchaseOrder> purchaseOrderClz = BasePurchaseOrder.class;

        if (existing == null) {
            existing = new PurchaseOrder(purchaseOrderId); // TODO: The save function ignores this ID

        } else {
            existing.optimisticLockCheck(update);
            purchaseOrderClz = UpdatePurchaseOrder.class;
        }

        PurchaseOrder temp = new PurchaseOrder();
        temp.override(update, getPropertyNames(purchaseOrderClz));
        repo.refresh(List.of(temp));

        existing.override(temp, getPropertyNames(purchaseOrderClz));

        return repo.saveAndFlush(existing);
    }
    
    public PurchaseOrder patch(Long purchaseOrderId, UpdatePurchaseOrder patch) {
        log.debug("Performing Patch on PurchaseOrder with Id: {}", purchaseOrderId);

        PurchaseOrder existing = repo.findById(purchaseOrderId).orElseThrow(() -> new EntityNotFoundException("PurchaseOrder", purchaseOrderId.toString()));
        existing.optimisticLockCheck(patch);

        PurchaseOrder temp = new PurchaseOrder(purchaseOrderId);
        temp.override(existing);
        temp.outerJoin(patch, getPropertyNames(UpdatePurchaseOrder.class));
        repo.refresh(List.of(temp));

        existing.override(temp);

        return repo.saveAndFlush(existing);
    }
    
    public void delete(Set<Long> ids) {
        this.repo.deleteByIds(ids);
    }
}
