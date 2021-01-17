package io.company.brewcraft.service;

import io.company.brewcraft.model.PurchaseOrderEntity;
import io.company.brewcraft.pojo.PurchaseOrder;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;

public class PurchaseOrderService {
    private static final PurchaseOrderMapper mapper = PurchaseOrderMapper.INSTANCE;

    private PurchaseOrderRepository repo;

    public PurchaseOrderService(PurchaseOrderRepository repo) {
        this.repo = repo;
    }

    public PurchaseOrder getPurchaseOrder(Long id) {
        PurchaseOrderEntity po = this.repo.findById(id).orElseThrow(() -> new EntityNotFoundException("PurchaseOrder", id.toString()));
        return mapper.fromEntity(po);
    }
}
