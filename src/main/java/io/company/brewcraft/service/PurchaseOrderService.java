package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class PurchaseOrderService extends BaseService implements CrudService<Long, PurchaseOrder, BasePurchaseOrder, UpdatePurchaseOrder, PurchaseOrderAccessor> {
    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderService.class);

    private final UpdateService<Long, PurchaseOrder, BasePurchaseOrder, UpdatePurchaseOrder> updateService;
    private final RepoService<Long, PurchaseOrder, PurchaseOrderAccessor> repoService;

    public PurchaseOrderService(UpdateService<Long, PurchaseOrder, BasePurchaseOrder, UpdatePurchaseOrder> updateService, RepoService<Long, PurchaseOrder, PurchaseOrderAccessor> repoService) {
        this.updateService = updateService;
        this.repoService = repoService;
    }

    public Page<PurchaseOrder> getPurchaseOrders(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> orderNumbers,
        Set<Long> supplierIds,
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size
    ) {
        final Specification<PurchaseOrder> spec= WhereClauseBuilder.builder()
                                                                     .in(PurchaseOrder.FIELD_ID, ids)
                                                                     .not().in(PurchaseOrder.FIELD_ID, excludeIds)
                                                                     .in(PurchaseOrder.FIELD_ORDER_NUMBER, orderNumbers)
                                                                     .in(new String[] { PurchaseOrder.FIELD_SUPPLIER, Supplier.FIELD_ID }, supplierIds)
                                                                     .build();

        return this.repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public PurchaseOrder get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<PurchaseOrder> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<PurchaseOrder> getByAccessorIds(Collection<? extends PurchaseOrderAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getPurchaseOrder());
    }

    @Override
    public boolean exists(Set<Long> ids) {
        return this.repoService.exists(ids);
    }

    @Override
    public boolean exist(Long id) {
        return this.repoService.exists(id);
    }

    @Override
    public long delete(Set<Long> ids) {
        return this.repoService.delete(ids);
    }

    @Override
    public long delete(Long id) {
        return this.repoService.delete(id);
    }

    @Override
    public List<PurchaseOrder> add(final List<BasePurchaseOrder> additions) {
        if (additions == null) {
            return null;
        }

        final List<PurchaseOrder> entities = this.updateService.getAddEntities(additions);

        return this.repoService.saveAll(entities);
    }

    @Override
    public List<PurchaseOrder> put(List<UpdatePurchaseOrder> updates) {
        if (updates == null) {
            return null;
        }

        final List<PurchaseOrder> existing = this.repoService.getByIds(updates);
        final List<PurchaseOrder> updated = this.updateService.getPutEntities(existing, updates);

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<PurchaseOrder> patch(List<UpdatePurchaseOrder> patches) {
        if (patches == null) {
            return null;
        }

        final List<PurchaseOrder> existing = this.repoService.getByIds(patches);
        if (existing.size() != patches.size()) {
            final Set<Long> existingIds = existing.stream().map(shipment -> shipment.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patches.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find purchaseOrder with Ids: %s", nonExistingIds));
        }

        final List<PurchaseOrder> updated = this.updateService.getPatchEntities(existing, patches);

        return this.repoService.saveAll(updated);
    }

    @Deprecated
    public List<PurchaseOrder> putBySupplierAndOrderNumber(List<BasePurchaseOrder> updates) {
        if (updates == null) {
            return null;
        }

        Set<Long> supplierIds = updates.stream()
                                       .filter(Objects::nonNull)
                                       .map(update -> update.getSupplier())
                                       .filter(Objects::nonNull)
                                       .map(supplier -> supplier.getId())
                                       .filter(Objects::nonNull)
                                       .collect(Collectors.toSet());
        Set<String> orderNumbers = updates.stream()
                                          .filter(Objects::nonNull)
                                          .map(update -> update.getOrderNumber())
                                          .filter(Objects::nonNull)
                                          .collect(Collectors.toSet());

        Specification<PurchaseOrder> spec = WhereClauseBuilder.builder()
                                                              .in(PurchaseOrder.FIELD_ORDER_NUMBER, orderNumbers)
                                                              .in(new String[] { PurchaseOrder.FIELD_SUPPLIER, Supplier.FIELD_ID }, supplierIds)
                                                              .build();
        final Map<PurchaseOrderNumberSupplierIdKey, PurchaseOrder> orderNumberToEntity = this.repoService.getAll(spec).stream()
                                                                                                                      .collect(Collectors.toMap(po -> new PurchaseOrderNumberSupplierIdKey(po), Function.identity()));

        List<PurchaseOrder> pOs = updates.stream()
                                         .filter(Objects::nonNull)
                                         .map(update -> {
                                            PurchaseOrder purchaseOrder = new PurchaseOrder();
                                            Class<? super PurchaseOrder> clz = BasePurchaseOrder.class;
                                            PurchaseOrder existing = orderNumberToEntity.get(new PurchaseOrderNumberSupplierIdKey(update));
                                            if (existing != null) {
                                                // Note: RISK. OptimisticLockCheck cannot be performed because the UPDATE object is
                                                // not expected to have a version. Solution: Remove this method altogether.
                                                purchaseOrder.setId(existing.getId());
                                                purchaseOrder.setVersion(existing.getVersion());
                                                clz = UpdatePurchaseOrder.class;
                                            }
                                            purchaseOrder.override(update, getPropertyNames(clz, Set.of(PurchaseOrder.ATTR_ID, PurchaseOrder.ATTR_VERSION)));
                                            return purchaseOrder;
                                         })
                                         .toList();

        return this.repoService.saveAll(pOs);
    }
}

@Deprecated
class PurchaseOrderNumberSupplierIdKey extends BaseModel {
    private String orderNumber;
    private Long supplierId;

    public  <P extends BasePurchaseOrder> PurchaseOrderNumberSupplierIdKey(P purchaseOrder) {
        this.orderNumber = purchaseOrder.getOrderNumber();
        Supplier supplier = purchaseOrder.getSupplier();
        if (supplier != null) {
            this.supplierId = supplier.getId();
        }
    }
}
