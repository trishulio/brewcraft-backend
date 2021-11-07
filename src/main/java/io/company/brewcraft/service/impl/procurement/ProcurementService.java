package io.company.brewcraft.service.impl.procurement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.CrudService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.TransactionService;
import io.company.brewcraft.service.impl.ShipmentService;

@Transactional
// TODO: Test partial POST call after removing the @Transaction annotation
public class ProcurementService extends BaseService implements CrudService<ProcurementId, Procurement, BaseProcurement<InvoiceItem, MaterialLot, ProcurementItem>, UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>, ProcurementAccessor> {
    private static final Logger log = LoggerFactory.getLogger(ProcurementService.class);

    private final InvoiceService invoiceService;
    private final PurchaseOrderService purchaseOrderService;
    private final ShipmentService shipmentService;
    private final TransactionService transactionService;

    public ProcurementService(final InvoiceService invoiceService, final PurchaseOrderService poService, final ShipmentService shipmentService, final TransactionService transactionService) {
        this.invoiceService = invoiceService;
        this.purchaseOrderService = poService;
        this.shipmentService = shipmentService;
        this.transactionService = transactionService;
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

        List<Procurement> procurements = shipments.stream().map(shipment -> new Procurement(shipment)).collect(Collectors.toList());

        return new PageImpl<>(procurements, shipments.getPageable(), shipments.getTotalElements());
    }

    @Override
    public boolean exists(Set<ProcurementId> ids) {
        Set<Long> invoiceIds = new HashSet<>(ids.size());
        Set<Long> shipmentIds = new HashSet<>(ids.size());

        ids.forEach(id -> {
            invoiceIds.add(id.getInvoiceId());
            shipmentIds.add(id.getShipmentId());
        });

        return invoiceService.exists(invoiceIds) && shipmentService.exists(shipmentIds);
    }

    @Override
    public boolean exist(ProcurementId id) {
        return invoiceService.exist(id.getInvoiceId()) && shipmentService.exist(id.getShipmentId());
    }

    @Override
    public int delete(Set<ProcurementId> ids) {
        Set<Long> invoiceIds = new HashSet<>(ids.size());
        Set<Long> shipmentIds = new HashSet<>(ids.size());

        ids.forEach(id -> {
            invoiceIds.add(id.getInvoiceId());
            shipmentIds.add(id.getShipmentId());
        });

        int shipmentCount = shipmentService.delete(shipmentIds);
        int invoiceCount = invoiceService.delete(invoiceIds);

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
        Invoice invoice = invoiceService.get(id.getInvoiceId());

        Procurement procurement = null;

        if (shipment != null && invoice != null) {
            procurement = new Procurement(shipment, invoice);
        }

        return procurement;
    }

    @Override
    public List<Procurement> getByIds(Collection<? extends Identified<ProcurementId>> idProviders) {
        Collection<? extends Identified<Long>> invoiceIdProviders = idProviders
                                                                          .stream()
                                                                          .map(provider -> new Identified<Long>() {
                                                                              @Override
                                                                              public Long getId() {
                                                                                  return provider.getId().getInvoiceId();
                                                                              }}
                                                                          ).collect(Collectors.toList());
        Collection<? extends Identified<Long>> shipmentIdProviders = idProviders
                                                                           .stream()
                                                                           .map(provider -> new Identified<Long>() {
                                                                               @Override
                                                                               public Long getId() {
                                                                                   return provider.getId().getShipmentId();
                                                                               }}
                                                                           ).collect(Collectors.toList());

        List<Procurement> procurements = new ArrayList<>(idProviders.size());

        List<Invoice> invoices = invoiceService.getByIds(invoiceIdProviders);
        List<Shipment> shipments = shipmentService.getByIds(shipmentIdProviders);

        if (invoices.size() != shipments.size()) {
            String msg = String.format("Expected same number of invoices to be deleted as the shipments. But found, InvoiceCount=%s and ShipmentCount=%s. Shipments and Invoices might be out of sync.", invoices.size(), shipments.size());
            throw new IllegalStateException(msg);
        }

        for (int i = 0; i < invoices.size(); i++) {
            procurements.add(new Procurement(shipments.get(i), invoices.get(i)));
        }

        return procurements;
    }

    @Override
    public List<Procurement> getByAccessorIds(Collection<? extends ProcurementAccessor> accessors) {
        Collection<Identified<ProcurementId>> idProviders = accessors.stream().map(accessor -> accessor.getProcurement()).collect(Collectors.toList());

        return getByIds(idProviders);
    }

    @Override
    public List<Procurement> add(List<BaseProcurement<InvoiceItem, MaterialLot, ProcurementItem>> additions) {
        List<BaseInvoice<? extends BaseInvoiceItem<?>>> bInvoices = additions
                                                                    .stream()
                                                                    .filter(p -> p != null)
                                                                    .map(p -> ((Procurement) p).buildInvoice())
                                                                    .collect(Collectors.toList());

        List<BasePurchaseOrder> bPos = bInvoices
                                      .stream()
                                      .map(invoice -> invoice.getPurchaseOrder())
                                      .filter(po -> po != null)
                                      .collect(Collectors.toList());

        Iterator<PurchaseOrder> pOs = purchaseOrderService.add(bPos).iterator();
        for (int i = 0; i < bInvoices.size(); i++) {
            if (bInvoices.get(i).getPurchaseOrder() != null) {
                bInvoices.get(i).setPurchaseOrder(pOs.next());
            }
        }

        List<Invoice> invoices = this.invoiceService.add(bInvoices);

        List<BaseShipment<? extends BaseMaterialLot<?>>> bShipments = additions
                                                                      .stream()
                                                                      .filter(p -> p != null)
                                                                      .map(p -> ((Procurement) p).buildShipmentWithoutInvoice())
                                                                      .collect(Collectors.toList());

        for (int i = 0; i < bShipments.size(); i++) {
            BaseShipment<? extends BaseMaterialLot<?>> bShipment = bShipments.get(i);
            Invoice invoice = invoices.get(i);

            List<? extends BaseMaterialLot<?>> lots = bShipment.getLots();
            List<InvoiceItem> items = invoice.getItems();
            if (lots != null) {
                for (int j = 0; j < lots.size(); j++) {
                    lots.get(i).setInvoiceItem(items.get(i));
                }
            }
        }

        Iterator<Shipment> shipments = this.shipmentService.add(bShipments).iterator();
        return invoices
                .stream()
                .map(invoice -> new Procurement(shipments.next(), invoice))
                .collect(Collectors.toList());
    }

    @Override
    public List<Procurement> put(List<UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>> updates) {
        List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> uInvoices = updates
                                                                        .stream()
                                                                        .filter(p -> p != null)
                                                                        .map(p -> ((Procurement) p).buildInvoice())
                                                                        .collect(Collectors.toList());

        List<UpdatePurchaseOrder> uPos = uInvoices
                                        .stream()
                                        .map(invoice -> invoice.getPurchaseOrder())
                                        .filter(po -> po != null)
                                        .collect(Collectors.toList());

        Iterator<PurchaseOrder> pOs = purchaseOrderService.put(uPos).iterator();
        for (int i = 0; i < uInvoices.size(); i++) {
            if (uInvoices.get(i).getPurchaseOrder() != null) {
                uInvoices.get(i).setPurchaseOrder(pOs.next());
            }
        }

        List<Invoice> invoices = this.invoiceService.put(uInvoices);

        List<UpdateShipment<? extends UpdateMaterialLot<?>>> uShipments = updates
                                                                          .stream()
                                                                          .filter(p -> p != null)
                                                                          .map(p -> ((Procurement) p).buildShipmentWithoutInvoice())
                                                                          .collect(Collectors.toList());

        for (int i = 0; i < uShipments.size(); i++) {
            UpdateShipment<? extends UpdateMaterialLot<?>> uShipment = uShipments.get(i);
            Invoice invoice = invoices.get(i);

            List<? extends UpdateMaterialLot<?>> lots = uShipment.getLots();
            List<InvoiceItem> items = invoice.getItems();
            if (lots != null) {
                for (int j = 0; j < lots.size(); j++) {
                    lots.get(i).setInvoiceItem(items.get(i));
                }
            }
        }

        Iterator<Shipment> shipments = this.shipmentService.put(uShipments).iterator();
        return invoices
                .stream()
                .map(invoice -> new Procurement(shipments.next(), invoice))
                .collect(Collectors.toList());
    }

    @Override
    public List<Procurement> patch(List<UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>> updates) {
        List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> uInvoices = updates
                                                                        .stream()
                                                                        .filter(p -> p != null)
                                                                        .map(p -> ((Procurement) p).buildInvoice())
                                                                        .collect(Collectors.toList());

        List<UpdatePurchaseOrder> uPos = uInvoices
                                        .stream()
                                        .map(invoice -> invoice.getPurchaseOrder())
                                        .filter(po -> po != null)
                                        .collect(Collectors.toList());

        Iterator<PurchaseOrder> pOs = purchaseOrderService.patch(uPos).iterator();
        for (int i = 0; i < uInvoices.size(); i++) {
            if (uInvoices.get(i).getPurchaseOrder() != null) {
                uInvoices.get(i).setPurchaseOrder(pOs.next());
            }
        }

        List<Invoice> invoices = this.invoiceService.patch(uInvoices);

        List<UpdateShipment<? extends UpdateMaterialLot<?>>> uShipments = updates
                                                                            .stream()
                                                                            .filter(p -> p != null)
                                                                            .map(p -> ((Procurement) p).buildShipmentWithoutInvoice())
                                                                            .collect(Collectors.toList());

        for (int i = 0; i < uShipments.size(); i++) {
            UpdateShipment<? extends UpdateMaterialLot<?>> uShipment = uShipments.get(i);
            Invoice invoice = invoices.get(i);

            List<? extends UpdateMaterialLot<?>> lots = uShipment.getLots();
            List<InvoiceItem> items = invoice.getItems();
            if (lots != null) {
                for (int j = 0; j < lots.size(); j++) {
                    lots.get(i).setInvoiceItem(items.get(i));
                }
            }
        }

        Iterator<Shipment> shipments = this.shipmentService.patch(uShipments).iterator();
        return invoices
                .stream()
                .map(invoice -> new Procurement(shipments.next(), invoice))
                .collect(Collectors.toList());
    }
}
