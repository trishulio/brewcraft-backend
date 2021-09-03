package io.company.brewcraft.service.impl.procurement;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.service.procurement.ProcurementService;

@Transactional
public class ProcurementServiceImpl extends BaseService implements ProcurementService {
    private static final Logger log = LoggerFactory.getLogger(ProcurementServiceImpl.class);

    private final InvoiceService invoiceService;
    private final PurchaseOrderService purchaseOrderService;
    private final ShipmentService shipmentService;

    public ProcurementServiceImpl(final InvoiceService invoiceService, final PurchaseOrderService poService, final ShipmentService shipmentService) {
        this.invoiceService = invoiceService;
        this.purchaseOrderService = poService;
        this.shipmentService = shipmentService;
    }

    @Override
    public Procurement add(Procurement procurement) {
        PurchaseOrder order = procurement.getPurchaseOrder();
        Invoice invoice = procurement.getInvoice();

        if (order != null && invoice != null && invoice.getPurchaseOrder() != null) {
            throw new IllegalArgumentException("PurchaseOrder specified in invoice object and also provided as a separate payload to add.");
        }

        if (order == null && invoice != null) {
            order = invoice.getPurchaseOrder();
        }

        if (order != null && order.getId() == null) {
            order = this.purchaseOrderService.add(List.of(order)).get(0);
            invoice.setPurchaseOrder(order);
        }

        invoice = this.invoiceService.add(List.of(invoice)).get(0);
        Shipment shipment = new Shipment(invoice);
        shipment = this.shipmentService.add(List.of(shipment)).get(0);
        return new Procurement(order, invoice, shipment);
    }

}
