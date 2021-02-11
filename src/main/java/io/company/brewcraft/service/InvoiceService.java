package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.FreightEntity;
import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.PurchaseOrderEntity;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.pojo.BaseInvoiceItem;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.InvoiceStatus;
import io.company.brewcraft.pojo.PurchaseOrder;
import io.company.brewcraft.pojo.UpdateInvoiceItem;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.InvoiceMapper;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class InvoiceService extends BaseService {

    private static final InvoiceMapper INVOICE_MAPPER = InvoiceMapper.INSTANCE;

    private InvoiceRepository repo;
    private PurchaseOrderService poService;
    private InvoiceStatusService statusService;

    public InvoiceService() {
        super();
    }

    public InvoiceService(InvoiceRepository repo, InvoiceStatusService statusService, PurchaseOrderService poService) {
        this();
        this.repo = repo;
        this.poService = poService;
        this.statusService = statusService;
    }

    public Page<Invoice> getInvoices(
            Set<Long> ids,
            Set<Long> excludeIds,
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
            BigDecimal freightAmtFrom,
            BigDecimal freightAmtTo,
            Set<String> status,
            Set<Long> supplierIds,
            Set<String> sort,
            boolean orderAscending,
            int page,
            int size
         ) {
        Specification<InvoiceEntity> spec = SpecificationBuilder
                                            .builder()
                                            .in(InvoiceEntity.FIELD_ID, ids)
                                            .not().in(InvoiceEntity.FIELD_ID, excludeIds)
                                            .in(InvoiceEntity.FIELD_INVOICE_NUMBER, invoiceNumbers)
                                            .like(InvoiceEntity.FIELD_DESCRITION, invoiceDescriptions)
//                                            .like(new String[] { InvoiceEntity.FIELD_ITEMS, InvoiceItemEntity.FIELD_DESCRIPTION }, invoiceItemDescriptions)
                                            .between(InvoiceEntity.FIELD_GENERATED_ON, generatedOnFrom, generatedOnTo)
                                            .between(InvoiceEntity.FIELD_RECEIVED_ON, receivedOnFrom, receivedOnTo)
                                            .between(InvoiceEntity.FIELD_PAYMENT_DUE_DATE, paymentDueDateFrom, paymentDueDateTo)
                                            .in(new String[] { InvoiceEntity.FIELD_PURCHASE_ORDER, PurchaseOrderEntity.FIELD_ID }, purchaseOrderIds)
                                            .between(new String[] { InvoiceEntity.FIELD_FREIGHT, FreightEntity.FIELD_AMOUNT, MoneyEntity.FIELD_AMOUNT }, freightAmtFrom, freightAmtTo)
                                            .in(new String[] { InvoiceEntity.FIELD_STATUS, InvoiceStatusEntity.FIELD_NAME }, status)
                                            .in(new String[] { InvoiceEntity.FIELD_PURCHASE_ORDER, PurchaseOrderEntity.FIELD_SUPPLIER, Supplier.FIELD_ID }, supplierIds)
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

    public Invoice put(Long purchaseOrderId, Long invoiceId, UpdateInvoice<? extends UpdateInvoiceItem> update) {
        Invoice existing = getInvoice(invoiceId);

        if (existing == null) {
            existing = new Invoice(invoiceId);
            existing.setCreatedAt(LocalDateTime.now()); // TODO: This is a hack. Need a fix at hibernate level to avoid any hibernate issues.
        }

        existing.override(update, getPropertyNames(UpdateInvoice.class));
        // TODO: this logic needs to exist in the Mapper. When DTOs are following the same pattern then this can be achived by calling the override method in the Mapper.
        if (existing.getItems() != null) {
            List<InvoiceItem> items = existing.getItems().stream().map(i -> {
                InvoiceItem item = new InvoiceItem();
                item.override(i, getPropertyNames(UpdateInvoiceItem.class));
                return item;
            }).collect(Collectors.toList());
            existing.setItems(items);   
        }

        return add(purchaseOrderId, existing);
    }

    public Invoice patch(Long purchaseOrderId, Long invoiceId, UpdateInvoice<? extends UpdateInvoiceItem> patch) {
        Validator validator = new Validator();

        Invoice existing = getInvoice(invoiceId);
        validator.assertion(existing != null, EntityNotFoundException.class, "Invoice", invoiceId.toString());

        existing.outerJoin(patch, getPropertyNames(UpdateInvoice.class));
        
        if (existing.getItems() != null) {
            List<InvoiceItem> items = existing.getItems().stream().map(i -> {
                InvoiceItem item = new InvoiceItem();
                item.override(i, getPropertyNames(UpdateInvoiceItem.class));
                return item;
            }).collect(Collectors.toList());
            existing.setItems(items);            
        }

        return add(purchaseOrderId, existing);
    }

    public Invoice add(Long purchaseOrderId, BaseInvoice<? extends BaseInvoiceItem> addition) {
        Invoice invoice = new Invoice();
        invoice.override(addition, getPropertyNames(BaseInvoice.class));
        
        if (invoice.getItems() != null) {
            List<InvoiceItem> items = invoice.getItems().stream().map(i -> {
                InvoiceItem item = new InvoiceItem();
                item.override(i, getPropertyNames(BaseInvoiceItem.class));
                return item;
            }).collect(Collectors.toList());
            invoice.setItems(items);            
        }

        return add(purchaseOrderId, invoice);
    }

    private Invoice add(Long purchaseOrderId, Invoice invoice) {
        Validator validator = new Validator();

        PurchaseOrder po = poService.getPurchaseOrder(purchaseOrderId);
        validator.assertion(po != null, EntityNotFoundException.class, "Purchase Order", purchaseOrderId.toString());

        String statusName = invoice.getStatus() != null ? invoice.getStatus().getName() : null;
        statusName = statusName != null ? statusName : InvoiceStatusEntity.DEFAULT_STATUS_NAME;
        InvoiceStatus status = statusService.getInvoiceStatus(statusName);

        validator.rule(status != null, String.format("Invalid Status Name: %s", statusName));
        validator.raiseErrors();

        invoice.setPurchaseOrder(po);
        invoice.setStatus(status);
        // TODO: Do I need to replace the Empty Material objects with the material
        // objects from the DB? or should it just reference the ID since I am not
        // cascading the changes to the Material Entity.

        InvoiceEntity entity = INVOICE_MAPPER.toEntity(invoice);
        InvoiceEntity added = repo.save(entity);

        return INVOICE_MAPPER.fromEntity(added);
    }
}
