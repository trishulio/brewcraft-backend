package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.SupplierEntity;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class InvoiceService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private InvoiceRepository repo;
    private InvoiceItemService itemService;
    private UtilityProvider utilProvider;

    public InvoiceService() {
        super();
    }

    public InvoiceService(InvoiceRepository repo, InvoiceItemService itemService,UtilityProvider utilProvider) {
        this();
        this.repo = repo;
        this.itemService = itemService;
        this.utilProvider = utilProvider;
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
        Specification<Invoice> spec = SpecificationBuilder
                                            .builder()
                                            .in(Invoice.FIELD_ID, ids)
                                            .not().in(Invoice.FIELD_ID, excludeIds)
                                            .in(Invoice.FIELD_INVOICE_NUMBER, invoiceNumbers)
                                            .like(Invoice.FIELD_DESCRITION, invoiceDescriptions)
//                                            .like(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_DESCRIPTION }, invoiceItemDescriptions)
                                            .between(Invoice.FIELD_GENERATED_ON, generatedOnFrom, generatedOnTo)
                                            .between(Invoice.FIELD_RECEIVED_ON, receivedOnFrom, receivedOnTo)
                                            .between(Invoice.FIELD_PAYMENT_DUE_DATE, paymentDueDateFrom, paymentDueDateTo)
                                            .in(new String[] { Invoice.FIELD_PURCHASE_ORDER, PurchaseOrder.FIELD_ID }, purchaseOrderIds)
                                            .between(new String[] { Invoice.FIELD_FREIGHT, Freight.FIELD_AMOUNT, MoneyEntity.FIELD_AMOUNT }, freightAmtFrom, freightAmtTo)
                                            .in(new String[] { Invoice.FIELD_STATUS, InvoiceStatus.FIELD_NAME }, status)
                                            .in(new String[] { Invoice.FIELD_PURCHASE_ORDER, PurchaseOrder.FIELD_SUPPLIER, SupplierEntity.FIELD_ID }, supplierIds)
                                            .build();
        return repo.findAll(spec, pageRequest(sort, orderAscending, page, size));
    }

    public Invoice getInvoice(Long id) {
        log.info("Retrieving invoice with Id: {}", id);
        Invoice invoice = null;
        Optional<Invoice> optional = repo.findById(id);
        if (optional.isPresent()) {
            log.info("Found invoice with id: {}", id);
            invoice = optional.get();
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
        Validator validator = this.utilProvider.getValidator();
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

        Collection<InvoiceItem> updatedItems = itemService.getPutCollection(existing.getItems(), update.getItems());
        log.info("Total UpdateItems: {}", updatedItems.size());
        
        existing.override(update, getPropertyNames(UpdateInvoice.class));
        existing.setItems(updatedItems);

        validator.raiseErrors();
        return repo.save(purchaseOrderId, existing);
    }

    public Invoice patch(Long purchaseOrderId, Long invoiceId, UpdateInvoice<? extends UpdateInvoiceItem> patch) {
        log.info("Performing Patch on Invoice with Id: {}", invoiceId);
        Validator validator = this.utilProvider.getValidator();

        Invoice existing = repo.findById(invoiceId).orElseThrow(() -> new EntityNotFoundException("Invoice", invoiceId.toString()));

        log.info("Invoice with Id: {} has {} existing items", existing.getId(), existing.getItems() == null ? null : existing.getItems().size());
        log.info("Update payload has {} item updates", patch.getItems() == null ? null : patch.getItems().size());

        Collection<InvoiceItem> updatedItems = itemService.getPatchCollection(existing.getItems(), patch.getItems());
        log.info("Total UpdateItems: {}", updatedItems.size());
        
        existing.outerJoin(patch, getPropertyNames(UpdateInvoice.class));
        existing.setItems(updatedItems);
        
        validator.raiseErrors();
        return repo.save(purchaseOrderId, existing);
    }

    public Invoice add(Long purchaseOrderId, BaseInvoice<? extends BaseInvoiceItem> addition) {
        Validator validator = this.utilProvider.getValidator();
        log.info("Attempting to add a new Invoice under the Purchase Order with Id: {}", purchaseOrderId);
        Invoice invoice = new Invoice();
        Collection<InvoiceItem> itemAdditions = itemService.getAddCollection(addition.getItems());
        log.info("Invoice has {} items", invoice.getItems() == null ? null : invoice.getItems().size());
        invoice.override(addition, getPropertyNames(BaseInvoice.class));
        invoice.setItems(itemAdditions);

        validator.raiseErrors();

        return repo.save(purchaseOrderId, invoice);
    }
}
