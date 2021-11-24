package io.company.brewcraft.service.impl.procurement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.BaseProcurementItem;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementIdCollection;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.model.procurement.UpdateProcurementItem;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.CrudService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.TransactionService;
import io.company.brewcraft.service.impl.ShipmentService;

@Transactional
public class ProcurementService extends BaseService implements CrudService<ProcurementId, Procurement, BaseProcurement<? extends BaseProcurementItem>, UpdateProcurement<? extends UpdateProcurementItem>, ProcurementAccessor> {
    private static final Logger log = LoggerFactory.getLogger(ProcurementService.class);

    private final InvoiceService invoiceService;
    private final PurchaseOrderService purchaseOrderService;
    private final ShipmentService shipmentService;
    private final TransactionService transactionService;
    private final ProcurementFactory procurementFactory;
    private final ProcurementItemFactory procurementItemFactory;

    public ProcurementService(final InvoiceService invoiceService, final PurchaseOrderService poService, final ShipmentService shipmentService, final TransactionService transactionService, final ProcurementFactory procurementFactory, ProcurementItemFactory procurementItemFactory) {
        this.invoiceService = invoiceService;
        this.purchaseOrderService = poService;
        this.shipmentService = shipmentService;
        this.transactionService = transactionService;
        this.procurementFactory = procurementFactory;
        this.procurementItemFactory = procurementItemFactory;
    }

    public Page<Procurement> getAll(
        // Shipment Filters
        Set<Long> shipmentIds,
        Set<Long> shipmentExcludeIds,
        Set<String> shipmentNumbers,
        Set<String> descriptions,
        Set<Long> shipmentStatusIds,
        LocalDateTime deliveryDueDateFrom,
        LocalDateTime deliveryDueDateTo,
        LocalDateTime deliveredDateFrom,
        LocalDateTime deliveredDateTo,
        // Invoice Filters
        Set<Long> invoiceIds,
        Set<Long> invoiceExcludeIds,
        Set<String> invoiceNumbers,
        Set<String> invoiceDescriptions,
        Set<String> invoiceItemDescriptions,
        LocalDateTime generatedOnFrom,
        LocalDateTime generatedOnTo,
        LocalDateTime receivedOnFrom,
        LocalDateTime receivedOnTo,
        LocalDateTime paymentDueDateFrom,
        LocalDateTime paymentDueDateTo,
        Set<Long> purchaseOrderIds,
        Set<Long> materialIds,
        BigDecimal amtFrom,
        BigDecimal amtTo,
        BigDecimal freightAmtFrom,
        BigDecimal freightAmtTo,
        Set<Long> invoiceStatusIds,
        Set<Long> supplierIds,
        // Misc
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size
    ) {
        Page<Shipment> shipments = this.shipmentService.getShipments(
            // shipment filters
            shipmentIds,
            shipmentExcludeIds,
            shipmentNumbers,
            descriptions,
            shipmentStatusIds,
            deliveryDueDateFrom,
            deliveryDueDateTo,
            deliveredDateFrom,
            deliveredDateTo,
            // invoice filters
            invoiceIds,
            invoiceExcludeIds,
            invoiceNumbers,
            invoiceDescriptions,
            invoiceItemDescriptions,
            generatedOnFrom,
            generatedOnTo,
            receivedOnFrom,
            receivedOnTo,
            paymentDueDateFrom,
            paymentDueDateTo,
            purchaseOrderIds,
            materialIds,
            amtFrom,
            amtTo,
            freightAmtFrom,
            freightAmtTo,
            invoiceStatusIds,
            supplierIds,
            // misc
            sort,
            orderAscending,
            page,
            size
        );

        List<Procurement> procurements = shipments.stream().map(shipment -> procurementFactory.buildFromShipment(shipment)).collect(Collectors.toList());

        return new PageImpl<>(procurements, shipments.getPageable(), shipments.getTotalElements());
    }

    @Override
    public boolean exists(Set<ProcurementId> ids) {
        ProcurementIdCollection idCollection = new ProcurementIdCollection(ids);
        return purchaseOrderService.exists(idCollection.getPurchaseOrderIds()) &&
               invoiceService.exists(idCollection.getInvoiceIds()) &&
               shipmentService.exists(idCollection.getShipmentIds());
    }

    @Override
    public boolean exist(ProcurementId id) {
        return purchaseOrderService.exist(id.getPurchaseOrderId()) &&
               invoiceService.exist(id.getInvoiceId()) &&
               shipmentService.exist(id.getShipmentId());
    }

    @Override
    public int delete(Set<ProcurementId> ids) {
        ProcurementIdCollection idCollection = new ProcurementIdCollection(ids);

        // Note: Purchase Orders are not deleted but only shipments and Invoices are deleted.
        // Because ProcurementService assumes/enforces 1-1 relationship between and Invoice
        // and a Shipment. But a 1-many relationship between a PurchaseOrder and Invoice is
        // protected.
        int shipmentCount = shipmentService.delete(idCollection.getShipmentIds());
        int invoiceCount = invoiceService.delete(idCollection.getInvoiceIds());

        if (shipmentCount != invoiceCount) {
            this.transactionService.setRollbackOnly();
            shipmentCount = 0;
        }

        return shipmentCount;
    }

    @Override
    public int delete(ProcurementId id) {
        int sCount = shipmentService.delete(id.getShipmentId());
        int iCount = invoiceService.delete(id.getInvoiceId());

        if (sCount != iCount) {
            this.transactionService.setRollbackOnly();
            sCount = 0;
        }

        return sCount;
    }

    @Override
    public Procurement get(ProcurementId id) {
        Shipment shipment = shipmentService.get(id.getShipmentId());

        return procurementFactory.buildFromShipmentIfIdMatches(id, shipment);
    }

    @Override
    public List<Procurement> getByIds(Collection<? extends Identified<ProcurementId>> idProviders) {
        List<ProcurementId> procurementIds = idProviders.stream()
                                                .map(provider -> provider.getId())
                                                .collect(Collectors.toList());

        Collection<? extends Identified<Long>> shipmentIdProviders = procurementIds.stream()
                                                                       .map(id -> (Identified<Long>) () -> id.getShipmentId())
                                                                       .collect(Collectors.toList());

        Map<Long, Shipment> idToShipment = shipmentService.getByIds(shipmentIdProviders).stream().collect(Collectors.toMap(
            shipment -> shipment.getId(),
            shipment -> shipment
        ));

        return procurementIds.stream()
                      .map(id -> procurementFactory.buildFromShipmentIfIdMatches(id, idToShipment.get(id.getShipmentId())))
                      .filter(p -> p != null)
                      .collect(Collectors.toList());
    }

    @Override
    public List<Procurement> getByAccessorIds(Collection<? extends ProcurementAccessor> accessors) {
        Collection<Identified<ProcurementId>> idProviders = accessors.stream().map(accessor -> accessor.getProcurement()).collect(Collectors.toList());

        return getByIds(idProviders);
    }

    @Override
    public List<Procurement> add(List<BaseProcurement<? extends BaseProcurementItem>> additions) {
        List<BasePurchaseOrder> bPos = new ArrayList<>(additions.size());
        List<BaseInvoice<? extends BaseInvoiceItem<?>>> bInvoices = new ArrayList<>(additions.size());
        List<BaseShipment<? extends BaseMaterialLot<?>>> bShipments = new ArrayList<>(additions.size());
        additions.stream()
                .filter(p -> p != null)
                .forEach(addition -> {
                    bPos.add(addition.getPurchaseOrder());
                    bInvoices.add(addition.getInvoice());
                    bShipments.add(addition.getShipment());
                });

        Iterator<PurchaseOrder> purchaseOrders = purchaseOrderService.add(bPos).iterator();
        bInvoices.forEach(bInvoice -> bInvoice.setPurchaseOrder(purchaseOrders.next()));

        Iterator<Invoice> invoices = invoiceService.add(bInvoices).iterator();
        bShipments.forEach(bShipment -> bShipment.setInvoiceItemsFromInvoice(invoices.next()));

        return shipmentService.add(bShipments)
                       .stream()
                       .map(shipment -> procurementFactory.buildFromShipment(shipment))
                       .collect(Collectors.toList());
    }

    @Override
    public List<Procurement> put(List<UpdateProcurement<? extends UpdateProcurementItem>> updates) {
        List<UpdatePurchaseOrder> uPos = new ArrayList<>(updates.size());
        List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> uInvoices = new ArrayList<>(updates.size());
        List<UpdateShipment<? extends UpdateMaterialLot<?>>> uShipments = new ArrayList<>(updates.size());
        updates.stream()
                .filter(p -> p != null)
                .forEach(update -> {
                    uPos.add(update.getPurchaseOrder());
                    uInvoices.add(update.getInvoice());
                    uShipments.add(update.getShipment());
                });

        Iterator<PurchaseOrder> purchaseOrders = purchaseOrderService.put(uPos).iterator();
        uInvoices.forEach(bInvoice -> bInvoice.setPurchaseOrder(purchaseOrders.next()));

        Iterator<Invoice> invoices = invoiceService.put(uInvoices).iterator();
        uShipments.forEach(bShipment -> bShipment.setInvoiceItemsFromInvoice(invoices.next()));

        return shipmentService.put(uShipments)
                       .stream()
                       .map(shipment -> procurementFactory.buildFromShipment(shipment))
                       .collect(Collectors.toList());
    }

    @Override
    public List<Procurement> patch(List<UpdateProcurement<? extends UpdateProcurementItem>> updates) {
        List<UpdatePurchaseOrder> uPos = new ArrayList<>(updates.size());
        List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> uInvoices = new ArrayList<>(updates.size());
        List<UpdateShipment<? extends UpdateMaterialLot<?>>> uShipments = new ArrayList<>(updates.size());
        updates.stream()
                .filter(p -> p != null)
                .forEach(update -> {
                    uPos.add(update.getPurchaseOrder());
                    uInvoices.add(update.getInvoice());
                    uShipments.add(update.getShipment());
                });

        Iterator<PurchaseOrder> purchaseOrders = purchaseOrderService.patch(uPos).iterator();
        uInvoices.forEach(uInvoice -> uInvoice.setPurchaseOrder(purchaseOrders.next()));

        Iterator<Invoice> invoices = invoiceService.patch(uInvoices).iterator();
        uShipments.forEach(uShipment -> uShipment.setInvoiceItemsFromInvoice(invoices.next()));

        return shipmentService.patch(uShipments)
                       .stream()
                       .map(shipment -> procurementFactory.buildFromShipment(shipment))
                       .collect(Collectors.toList());
    }
}
