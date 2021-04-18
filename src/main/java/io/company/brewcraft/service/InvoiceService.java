package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class InvoiceService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

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
                                            .in(new String[] { Invoice.FIELD_PURCHASE_ORDER, PurchaseOrder.FIELD_SUPPLIER, Supplier.FIELD_ID }, supplierIds)
                                            .build();
        return repo.findAll(spec, pageRequest(sort, orderAscending, page, size));
    }

    public Invoice getInvoice(Long id) {
        log.debug("Retrieving invoice with Id: {}", id);
        Invoice invoice = null;
        Optional<Invoice> optional = repo.findById(id);
        if (optional.isPresent()) {
            log.debug("Found invoice with id: {}", id);
            invoice = optional.get();
        }

        return invoice;
    }

    public boolean exists(Long id) {
        return repo.existsById(id);
    }

    public void delete(Long id) {
        log.debug("Attempting to delete Invoice with Id: {}", id);
        if (!exists(id)) {
            log.error("Failed to delete non-existing Invoice with Id: {}", id);
            throw new EntityNotFoundException("Invoice", id.toString());
        }
        repo.deleteById(id);
    }

    public Invoice put(Long invoiceId, UpdateInvoice<? extends UpdateInvoiceItem> update) {
        log.debug("Updating the Invoice with Id: {}", invoiceId);

        Invoice existing = getInvoice(invoiceId);
        Class<? super Invoice> invoiceClz = BaseInvoice.class;

        if (existing == null) {
            existing = new Invoice(invoiceId); // Gotcha: The save function ignores this ID

        } else {
            existing.optimisicLockCheck(update);
            invoiceClz = UpdateInvoice.class;
        }

        log.debug("Invoice with Id: {} has {} existing items", existing == null ? null : invoiceId, existing.getItems() == null ? null : existing.getItems().size());
        log.debug("Update payload has {} item updates", update.getItems() == null ? null : update.getItems().size());

        List<InvoiceItem> updatedItems = itemService.getPutItems(existing.getItems(), update.getItems());
        log.debug("Total UpdateItems: {}", updatedItems == null ? null : updatedItems.size());

        Invoice temp = new Invoice();
        temp.override(update, getPropertyNames(invoiceClz));
        temp.setItems(updatedItems);
        repo.refresh(List.of(temp));

        existing.override(temp, getPropertyNames(invoiceClz));

        return repo.saveAndFlush(existing);
    }

    public Invoice patch(Long invoiceId, UpdateInvoice<? extends UpdateInvoiceItem> patch) {
        log.debug("Performing Patch on Invoice with Id: {}", invoiceId);

        Invoice existing = repo.findById(invoiceId).orElseThrow(() -> new EntityNotFoundException("Invoice", invoiceId.toString()));
        existing.optimisicLockCheck(patch);

        log.debug("Invoice with Id: {} has {} existing items", existing.getId(), existing.getItems() == null ? null : existing.getItems().size());
        log.debug("Update payload has {} item updates", patch.getItems() == null ? null : patch.getItems().size());

        List<InvoiceItem> updatedItems = itemService.getPatchItems(existing.getItems(), patch.getItems());
        log.debug("Total UpdateItems: {}", updatedItems.size());

        Invoice temp = new Invoice(invoiceId);
        temp.override(existing);
        temp.outerJoin(patch, getPropertyNames(UpdateInvoice.class));
        temp.setItems(updatedItems);
        repo.refresh(List.of(temp));

        existing.override(temp);

        return repo.saveAndFlush(existing);
    }

    public Invoice add(BaseInvoice<? extends BaseInvoiceItem> addition) {
        Invoice invoice = new Invoice();
        List<InvoiceItem> itemAdditions = itemService.getAddItems(addition.getItems());
        log.debug("Invoice has {} items", invoice.getItems() == null ? null : invoice.getItems().size());

        invoice.override(addition, getPropertyNames(BaseInvoice.class));
        invoice.setItems(itemAdditions);

        repo.refresh(List.of(invoice));

        return repo.saveAndFlush(invoice);
    }
}
