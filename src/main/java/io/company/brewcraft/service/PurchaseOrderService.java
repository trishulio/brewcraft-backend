package io.company.brewcraft.service;

import java.util.Optional;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.service.impl.procurement.ProcurementServiceImpl;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PurchaseOrderService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

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

    public PurchaseOrder addIfNotExists(PurchaseOrder order) {
        PurchaseOrder addedPurchaseOrder = order;
        if (!repo.existsById(order.getId())) {
            logger.debug("Attempting to persist Purchase Order since Purchase Order {} does not Exist", order.getId());
            addedPurchaseOrder = repo.saveAndFlush(order);
        }
        return addedPurchaseOrder;
    }
}
