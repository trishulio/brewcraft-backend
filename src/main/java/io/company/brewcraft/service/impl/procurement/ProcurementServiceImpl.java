package io.company.brewcraft.service.impl.procurement;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.factory.ShipmentFactory;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.service.procurement.ProcurementService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

@Transactional
public class ProcurementServiceImpl extends BaseService implements ProcurementService {

    private static final Logger logger = LoggerFactory.getLogger(ProcurementServiceImpl.class);

    private final InvoiceService invoiceService;

    private final PurchaseOrderService purchaseOrderService;

    private final ShipmentService shipmentService;

    private final ShipmentFactory shipmentFactory;

    private final UtilityProvider utilProvider;

    public ProcurementServiceImpl(final InvoiceService invoiceService, final PurchaseOrderService poService, final ShipmentService shipmentService, final ShipmentFactory shipmentFactory, final UtilityProvider utilProvider) {
        this.invoiceService = invoiceService;
        this.purchaseOrderService = poService;
        this.shipmentService = shipmentService;
        this.shipmentFactory = shipmentFactory;
        this.utilProvider = utilProvider;
    }

    public Procurement add(Procurement procurement) {
        Validator validator = this.utilProvider.getValidator();
        validator.rule(procurement != null, "Add Procurement Payload cannot be null");
        validator.rule(procurement.getInvoice() != null, "Add Invoice Payload cannot be null");
        validator.raiseErrors();

        final Invoice invoice = procurement.getInvoice();
        logger.debug("Attempting to persist Purchase Order {} If Not Exists", invoice.getPurchaseOrder());
        final PurchaseOrder addedPurchaseOrder = purchaseOrderService.addIfNotExists(invoice.getPurchaseOrder());
        invoice.setPurchaseOrder(addedPurchaseOrder);

        logger.debug("Attempting to persist Invoice");
        final Invoice addedInvoice = invoiceService.add(invoice);
        final Shipment shipmentFromInvoice = shipmentFactory.createFromInvoice(addedInvoice);

        logger.debug("Attempting to shipment created from Invoice");
        final Shipment addedShipment = shipmentService.add(shipmentFromInvoice);
        return new Procurement(addedInvoice, addedShipment);
    }

}
