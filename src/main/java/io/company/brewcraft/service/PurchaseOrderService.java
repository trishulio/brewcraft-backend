package io.company.brewcraft.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;

public class PurchaseOrderService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    private static final PurchaseOrderMapper mapper = PurchaseOrderMapper.INSTANCE;

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
}
