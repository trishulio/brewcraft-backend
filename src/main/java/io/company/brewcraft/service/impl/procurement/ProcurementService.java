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
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.BaseProcurementItem;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementIdCollection;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.model.procurement.UpdateProcurementItem;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.CrudService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.impl.ShipmentService;

@Transactional
public class ProcurementService extends BaseService implements CrudService<ProcurementId, Procurement, BaseProcurement<? extends BaseProcurementItem>, UpdateProcurement<? extends UpdateProcurementItem>, ProcurementAccessor> {
    private static final Logger log = LoggerFactory.getLogger(ProcurementService.class);

    private final InvoiceService invoiceService;
    private final ShipmentService shipmentService;
    private final RepoService<ProcurementId, Procurement, ProcurementAccessor> repoService;

    public ProcurementService(final InvoiceService invoiceService, final ShipmentService shipmentService, RepoService<ProcurementId, Procurement, ProcurementAccessor> repoService) {
        this.invoiceService = invoiceService;
        this.shipmentService = shipmentService;
        this.repoService = repoService;
    }

    public Page<Procurement> getAll(
        // Shipment Filters
        Set<Long> shipmentIds,
        Set<Long> shipmentExcludeIds,
        Set<String> shipmentNumbers,
        Set<String> shipmentDescriptions,
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

        Specification<Procurement> spec = WhereClauseBuilder.builder()
                                                            // shipment filters
                                                            .in(new String[] { Procurement.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentIds)
                                                            .not().in(new String[] { Procurement.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentExcludeIds)
                                                            .in(new String[] {Procurement.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_NUMBER }, shipmentNumbers)
                                                            .in(new String[] {Procurement.FIELD_SHIPMENT, Shipment.FIELD_DESCRIPTION }, shipmentDescriptions)
                                                            .in(new String[] {Procurement.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_STATUS, ShipmentStatus.FIELD_ID}, shipmentStatusIds)
                                                            .between(new String[] {Procurement.FIELD_SHIPMENT, Shipment.FIELD_DELIVERY_DUE_DATE }, deliveryDueDateFrom, deliveryDueDateTo)
                                                            .between(new String[] {Procurement.FIELD_SHIPMENT, Shipment.FIELD_DELIVERED_DATE }, deliveredDateFrom, deliveredDateTo)
                                                            // invoice filters
                                                            .in(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_ID}, invoiceIds)
                                                            .not().in(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_ID}, invoiceExcludeIds)
                                                            .in(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_INVOICE_NUMBER}, invoiceNumbers)
                                                            .in(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_DESCRITION}, invoiceDescriptions)
                                                            .in(new String[] { Procurement.FIELD_INVOICE, Invoice.FIELD_ITEMS, InvoiceItem.FIELD_DESCRIPTION}, invoiceItemDescriptions)
                                                            .between(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_GENERATED_ON}, generatedOnFrom, generatedOnTo)
                                                            .between(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_RECEIVED_ON}, receivedOnFrom, receivedOnTo)
                                                            .between(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_PAYMENT_DUE_DATE}, paymentDueDateFrom, paymentDueDateTo)
                                                            .in(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_PURCHASE_ORDER, PurchaseOrder.FIELD_ID}, purchaseOrderIds)
                                                            .in(new String[] { Procurement.FIELD_INVOICE, Invoice.FIELD_ITEMS, InvoiceItem.FIELD_MATERIAL}, materialIds)
                                                            .between(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_AMOUNT, MoneyEntity.FIELD_AMOUNT }, amtFrom, amtTo)
                                                            .between(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_FREIGHT, Freight.FIELD_AMOUNT, MoneyEntity.FIELD_AMOUNT }, freightAmtFrom, freightAmtTo)
                                                            .in(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_INVOICE_STATUS, InvoiceStatus.FIELD_ID}, invoiceStatusIds)
                                                            .in(new String[] {Procurement.FIELD_INVOICE, Invoice.FIELD_PURCHASE_ORDER, PurchaseOrder.FIELD_SUPPLIER, Supplier.FIELD_ID}, supplierIds)
                                                            .build();

        return repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public boolean exists(Set<ProcurementId> ids) {
        return this.repoService.exists(ids);
    }

    @Override
    public boolean exist(ProcurementId id) {
        return this.repoService.exists(id);
    }

    @Override
    public int delete(Set<ProcurementId> ids) {
        int procurementsCount = repoService.delete(ids);

        ProcurementIdCollection idCollection = new ProcurementIdCollection(ids);
        int shipmentsCount = shipmentService.delete(idCollection.getShipmentIds());
        int invoicesCount = invoiceService.delete(idCollection.getInvoiceIds());

        log.info("Deleted - Shipments: {}; Invoices: {}; Procurements: {}", shipmentsCount, invoicesCount, procurementsCount);

        return procurementsCount;
    }

    @Override
    public int delete(ProcurementId id) {
        int procurementsCount = repoService.delete(id);
        int shipmentsCount = shipmentService.delete(id.getShipmentId());
        int invoicesCount = invoiceService.delete(id.getInvoiceId());

        log.info("Deleted - Shipments: {}; Invoices: {}; Procurements: {}", shipmentsCount, invoicesCount, procurementsCount);

        return procurementsCount;
    }

    @Override
    public Procurement get(ProcurementId id) {
        return repoService.get(id);
    }

    @Override
    public List<Procurement> getByIds(Collection<? extends Identified<ProcurementId>> idProviders) {
        return repoService.getByIds(idProviders);
    }

    @Override
    public List<Procurement> getByAccessorIds(Collection<? extends ProcurementAccessor> accessors) {
        return repoService.getByAccessorIds(accessors, accessor -> accessor.getProcurement());
    }

    @Override
    public List<Procurement> add(List<BaseProcurement<? extends BaseProcurementItem>> additions) {
        List<BaseInvoice<? extends BaseInvoiceItem<?>>> bInvoices = new ArrayList<>(additions.size());
        List<BaseShipment<? extends BaseMaterialLot<?>>> bShipments = new ArrayList<>(additions.size());
        additions.stream()
                 .filter(p -> p != null)
                 .forEach(addition -> {
                     bInvoices.add(addition.getInvoice());
                     bShipments.add(addition.getShipment());
                 });

        Iterator<Invoice> invoices = invoiceService.add(bInvoices).iterator();
        List<Procurement> procurements = shipmentService.add(bShipments)
                                                        .stream()
                                                        .map(shipment -> {
                                                            Invoice invoice = invoices.next();
                                                            shipment.setInvoiceItemsFromInvoice(invoice);
                                                            return new Procurement(shipment, invoice);
                                                        })
                                                        .collect(Collectors.toList());

        return repoService.saveAll(procurements);
    }

    @Override
    public List<Procurement> put(List<UpdateProcurement<? extends UpdateProcurementItem>> updates) {
        List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> uInvoices = new ArrayList<>(updates.size());
        List<UpdateShipment<? extends UpdateMaterialLot<?>>> uShipments = new ArrayList<>(updates.size());
        updates.stream()
               .filter(p -> p != null)
               .forEach(update -> {
                   uInvoices.add(update.getInvoice());
                   uShipments.add(update.getShipment());
               });

        Iterator<Invoice> invoices = invoiceService.put(uInvoices).iterator();

        Map<ProcurementId, Procurement> idToExisting = repoService.getByIds(updates).stream().collect(Collectors.toMap(p -> p.getId(), Function.identity()));
        List<Procurement> procurements = shipmentService.put(uShipments)
                                                        .stream()
                                                        .map(shipment -> {
                                                            Invoice invoice = invoices.next();
                                                            shipment.setInvoiceItemsFromInvoice(invoice);

                                                            ProcurementId id = ProcurementIdFactory.INSTANCE.build(shipment, invoice);
                                                            Procurement procurement = idToExisting.get(id);
                                                            if (procurement == null) {
                                                                procurement = new Procurement(shipment, invoice);
                                                            }

                                                            return procurement;
                                                        })
                                                        .collect(Collectors.toList());

        return repoService.saveAll(procurements);
    }

    @Override
    public List<Procurement> patch(List<UpdateProcurement<? extends UpdateProcurementItem>> updates) {
        List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> uInvoices = new ArrayList<>(updates.size());
        List<UpdateShipment<? extends UpdateMaterialLot<?>>> uShipments = new ArrayList<>(updates.size());
        updates.stream()
               .filter(p -> p != null)
               .forEach(update -> {
                   uInvoices.add(update.getInvoice());
                   uShipments.add(update.getShipment());
               });

        Iterator<Invoice> invoices = invoiceService.patch(uInvoices).iterator();

        Map<ProcurementId, Procurement> idToExisting = repoService.getByIds(updates).stream().collect(Collectors.toMap(p -> p.getId(), Function.identity()));
        List<Procurement> procurements = shipmentService.patch(uShipments)
                                                        .stream()
                                                        .map(shipment -> {
                                                            Invoice invoice = invoices.next();
                                                            ProcurementId id = ProcurementIdFactory.INSTANCE.build(shipment, invoice);
                                                            Procurement procurement = idToExisting.get(id);
                                                            if (procurement == null) {
                                                                throw new EntityNotFoundException("Procurement", id);
                                                            }

                                                            shipment.setInvoiceItemsFromInvoice(invoice);

                                                            return procurement;
                                                        })
                                                        .collect(Collectors.toList());

        return repoService.saveAll(procurements);
    }
}
