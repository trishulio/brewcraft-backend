package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.company.brewcraft.pojo.UpdateInvoiceItem;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.InvoiceMapper;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class InvoiceService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);
    private static final InvoiceMapper INVOICE_MAPPER = InvoiceMapper.INSTANCE;

    private InvoiceRepository repo;
    private InvoiceItemService itemService;

    public InvoiceService() {
        super();
    }

    public InvoiceService(InvoiceRepository repo, InvoiceItemService itemService) {
        this();
        this.repo = repo;
        this.itemService = itemService;
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

        return entityPage.map(invoice -> INVOICE_MAPPER.fromEntity(invoice, new CycleAvoidingMappingContext()));
    }

    public Invoice getInvoice(Long id) {
        log.info("Retrieving invoice with Id: {}", id);
        Invoice invoice = null;
        Optional<InvoiceEntity> optional = repo.findById(id);
        if (optional.isPresent()) {
            log.info("Found invoice with id: {}", id);
            invoice = INVOICE_MAPPER.fromEntity(optional.get(), new CycleAvoidingMappingContext());
        }

        return invoice;
    }

    public boolean exists(Long id) {
        return repo.existsById(id);
    }

    public void delete(Long id) {
        log.info("Attempting to delete Invoice with Id: {}", id);
        if (!exists(id)) {
            log.error("Failed to delete non-existing Invoice with Id: {}", id);
            throw new EntityNotFoundException("Invoice", id.toString());
        }
        repo.deleteById(id);
    }

    public Invoice put(Long purchaseOrderId, Long invoiceId, UpdateInvoice<? extends UpdateInvoiceItem> update) {
        log.info("Updating the Invoice with Id: {}", invoiceId);
        Validator validator = validator();
        Invoice existing = getInvoice(invoiceId);

        if (existing == null) {
            log.info("Invoice with Id: {} not found. New Invoice will be created", invoiceId);
            existing = new Invoice(invoiceId);
            LocalDateTime now = now();
            log.info("Setting the creation timestamp for the new Invoice to: {}", now.toString());
            existing.setCreatedAt(now);
            // TODO: This is a hack. Need a fix at hibernate
            // level to avoid any hibernate issues.
        }

        log.info("Invoice with Id: {} has {} existing items", existing.getId(), existing.getItems() == null ? null : existing.getItems().size());
        log.info("Update payload has {} item updates", update.getItems() == null ? null : update.getItems().size());

        List<InvoiceItem> updatedItems = itemService.mergePut(validator, existing.getItems(), update.getItems());
        log.info("Total UpdateItems: {}", updatedItems.size());
        
        existing.override(update, getPropertyNames(UpdateInvoice.class));
        existing.setItems(updatedItems);

        validator.raiseErrors();
        return save(purchaseOrderId, existing);
    }

    public Invoice patch(Long purchaseOrderId, Long invoiceId, UpdateInvoice<? extends UpdateInvoiceItem> patch) {
        log.info("Performing Patch on Invoice with Id: {}", invoiceId);
        Validator validator = validator();

        Invoice existing = getInvoice(invoiceId);
        validator.assertion(existing != null, EntityNotFoundException.class, "Invoice", invoiceId.toString());

        log.info("Invoice with Id: {} has {} existing items", existing.getId(), existing.getItems() == null ? null : existing.getItems().size());
        log.info("Update payload has {} item updates", patch.getItems() == null ? null : patch.getItems().size());

        List<InvoiceItem> updatedItems = itemService.mergePatch(validator, existing.getItems(), patch.getItems());
        log.info("Total UpdateItems: {}", updatedItems.size());
        
        existing.outerJoin(patch, getPropertyNames(UpdateInvoice.class));
        existing.setItems(updatedItems);
        
        validator.raiseErrors();
        return save(purchaseOrderId, existing);
    }

    public Invoice add(Long purchaseOrderId, BaseInvoice<? extends BaseInvoiceItem> addition) {
        Validator validator = validator();
        log.info("Attempting to add a new Invoice under the Purchase Order with Id: {}", purchaseOrderId);
        Invoice invoice = new Invoice();
        List<InvoiceItem> itemAdditions = itemService.addList(validator, addition.getItems());
        log.info("Invoice has {} items", invoice.getItems() == null ? null : invoice.getItems().size());
        invoice.override(addition, getPropertyNames(BaseInvoice.class));
        invoice.setItems(itemAdditions);

        validator.raiseErrors();

        return save(purchaseOrderId, invoice);
    }
    
    private Invoice save(Long purchaseOrderId, Invoice invoice) {
        InvoiceEntity entity = INVOICE_MAPPER.toEntity(invoice, new CycleAvoidingMappingContext());
        InvoiceEntity added = repo.refreshAndAdd(purchaseOrderId, entity);
        return INVOICE_MAPPER.fromEntity(added, new CycleAvoidingMappingContext());
    }
}
