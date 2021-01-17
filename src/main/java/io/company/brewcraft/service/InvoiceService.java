package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.FreightEntity;
import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.PurchaseOrderEntity;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.PurchaseOrder;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.InvoiceMapper;

@Transactional
public class InvoiceService {

    private static final InvoiceMapper INVOICE_MAPPER = InvoiceMapper.INSTANCE;

    private InvoiceRepository repo;
    private PurchaseOrderService poService;
    private InvoiceStatusService statusService;

    private SupplierService supplierService;

    public InvoiceService() {
    }

    public InvoiceService(InvoiceRepository repo, InvoiceStatusService statusService, PurchaseOrderService poService, SupplierService supplierService) {
        this();
        this.repo = repo;
        this.poService = poService;
        this.supplierService = supplierService;
        this.statusService = statusService;
    }

    public Page<Invoice> getInvoices(
            Set<Long> ids,
            Set<Long> excludeIds,
            Set<String> invoiceNumbers,
            LocalDateTime generatedOnFrom,
            LocalDateTime generatedOnTo,
            LocalDateTime receivedOnFrom,
            LocalDateTime receivedOnTo,
            LocalDateTime paymentDueDateFrom,
            LocalDateTime paymentDueDateTo,
            Set<Long> purchaseOrderIds,
            BigDecimal freightAmtFrom,
            BigDecimal freightAmtTo,
            Set<String> status,
            Set<Long> supplierIds,
            Set<String> sort,
            boolean orderAscending,
            int page,
            int size
         ) {
//        if (from != null || to != null) {
//            if (from == null) {
//                from = LocalDateTime.MIN;
//            }
//
//            if (to == null) {
//                to = LocalDateTime.MAX;
//            }
//        }

        Specification<InvoiceEntity> spec = SpecificationBuilder
                                            .builder()
                                            .in(InvoiceEntity.FIELD_ID, ids)
                                            .not().in(InvoiceEntity.FIELD_ID, excludeIds)
                                            .in(InvoiceEntity.FIELD_INVOICE_NUMBER, invoiceNumbers)
                                            .between(InvoiceEntity.FIELD_GENERATED_ON, generatedOnFrom, generatedOnTo)
                                            .between(InvoiceEntity.FIELD_RECEIVED_ON, receivedOnFrom, receivedOnTo)
                                            .between(InvoiceEntity.FIELD_PAYMENT_DUE_DATE, paymentDueDateFrom, paymentDueDateTo)
                                            .in(new String[] { InvoiceEntity.FIELD_PURCHASE_ORDER, PurchaseOrderEntity.FIELD_ID }, purchaseOrderIds)
                                            .between(new String[] { InvoiceEntity.FIELD_FREIGHT, FreightEntity.FIELD_AMOUNT, MoneyEntity.FIELD_AMOUNT }, freightAmtFrom, freightAmtTo)
                                            .in(new String[] { InvoiceEntity.FIELD_STATUS, InvoiceStatusEntity.FIELD_NAME }, status)
                                            .in(new String[] { InvoiceEntity.FIELD_SUPPLIER, Supplier.FIELD_ID }, supplierIds)
                                            .build();
        Page<InvoiceEntity> entityPage = repo.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return entityPage.map(INVOICE_MAPPER::fromEntity);
    }

    public Invoice getInvoice(Long id) {
        Invoice invoice = null;
        Optional<InvoiceEntity> optional = repo.findById(id);
        if (optional.isPresent()) {
            invoice = INVOICE_MAPPER.fromEntity(optional.get());
        }

        return invoice;
    }

    public boolean exists(Long id) {
        return repo.existsById(id);
    }

    public void delete(Long id) {
        if (!exists(id)) {
            throw new EntityNotFoundException("Invoice", id.toString());
        }
        repo.deleteById(id);
    }

    public Invoice update(Long supplierId, Long invoiceId, Invoice invoice) {
        InvoiceEntity entity = INVOICE_MAPPER.toEntity(invoice);

        InvoiceEntity existing = repo.findById(invoiceId).orElse(null);
        if (existing != null) {
            entity.outerJoin(existing);
        }

        entity.setId(invoiceId);

        return add(supplierId, entity);
    }

    public Invoice add(Long supplierId, Invoice invoice) {
        if (invoice.getPurchaseOrder().getId() != null) {
            PurchaseOrder po = poService.getPurchaseOrder(invoice.getPurchaseOrder().getId());
            invoice.setPurchaseOrder(po);
        }

        String status = invoice.getStatus().getName() == null ? InvoiceStatusEntity.DEFAULT_STATUS_NAME : invoice.getStatus().getName();
        invoice.setStatus(statusService.getInvoiceStatus(status));

        return add(supplierId, INVOICE_MAPPER.toEntity(invoice));
    }

    private Invoice add(Long supplierId, InvoiceEntity invoice) {
        if (invoice.getSupplier() == null || invoice.getSupplier().getId() != supplierId) {
            Supplier supplier = supplierService.getSupplier(supplierId);
            if (supplier == null) {
                // SupplierService takes care of throwing exception but just in
                // case that changes, this will act as a safeguard
                throw new EntityNotFoundException("Supplier", supplierId.toString());
            }
            invoice.setSupplier(supplier);
        }

        InvoiceEntity saved = repo.save(invoice);

        return INVOICE_MAPPER.fromEntity(saved);
    }
}
