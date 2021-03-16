package io.company.brewcraft.service;

import java.util.Optional;

import io.company.brewcraft.pojo.PurchaseOrder;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;

public class PurchaseOrderService {
    private static final PurchaseOrderMapper mapper = PurchaseOrderMapper.INSTANCE;

    private PurchaseOrderRepository repo;

    public PurchaseOrderService(PurchaseOrderRepository repo) {
        this.repo = repo;
    }

    public PurchaseOrder getPurchaseOrder(Long id) {
        PurchaseOrder po = null;
        Optional<PurchaseOrder> opt = this.repo.findById(id);
        if (opt.isPresent()) {
            po = opt.get();
        }
        return po;
    }
}
