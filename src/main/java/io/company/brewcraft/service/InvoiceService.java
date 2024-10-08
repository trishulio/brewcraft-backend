package io.company.brewcraft.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.Amount;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.TaxAmount;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class InvoiceService extends BaseService implements CrudService<Long, Invoice, BaseInvoice<? extends BaseInvoiceItem<?>>, UpdateInvoice<? extends UpdateInvoiceItem<?>>, InvoiceAccessor> {
    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final UpdateService<Long, Invoice, BaseInvoice<? extends BaseInvoiceItem<?>>, UpdateInvoice<? extends UpdateInvoiceItem<?>>> updateService;
    private final InvoiceItemService invoiceItemService;
    private final RepoService<Long, Invoice, InvoiceAccessor> repoService;

    public InvoiceService(UpdateService<Long, Invoice, BaseInvoice<? extends BaseInvoiceItem<?>>, UpdateInvoice<? extends UpdateInvoiceItem<?>>> updateService, InvoiceItemService invoiceItemService, RepoService<Long, Invoice, InvoiceAccessor> repoService) {
        this.updateService = updateService;
        this.invoiceItemService = invoiceItemService;
        this.repoService = repoService;
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
            Set<Long> materialIds,
            BigDecimal totalAmountFrom,
            BigDecimal totalAmountTo,
            BigDecimal subTotalAmountFrom,
            BigDecimal subTotalAmountTo,
            BigDecimal pstAmountFrom,
            BigDecimal pstAmountTo,
            BigDecimal gstAmountFrom,
            BigDecimal gstAmountTo,
            BigDecimal hstAmountFrom,
            BigDecimal hstAmountTo,
            BigDecimal totalTaxAmountFrom,
            BigDecimal totalTaxAmountTo,
            BigDecimal invoiceItemTotalAmountFrom,
            BigDecimal invoiceItemTotalAmountTo,
            BigDecimal invoiceItemSubTotalAmountFrom,
            BigDecimal invoiceItemSubTotalAmountTo,
            BigDecimal invoiceItemPstAmountFrom,
            BigDecimal invoiceItemPstAmountTo,
            BigDecimal invoiceItemGstAmountFrom,
            BigDecimal invoiceItemGstAmountTo,
            BigDecimal invoiceItemHstAmountFrom,
            BigDecimal invoiceItemHstAmountTo,
            BigDecimal invoiceItemTotalTaxAmountFrom,
            BigDecimal invoiceItemTotalTaxAmountTo,
            BigDecimal freightAmtFrom,
            BigDecimal freightAmtTo,
            Set<Long> invoiceStatusIds,
            Set<Long> supplierIds,
            SortedSet<String> sort,
            boolean orderAscending,
            int page,
            int size
         ) {
        final Specification<Invoice> spec = WhereClauseBuilder
                                            .builder()
                                            .in(Invoice.FIELD_ID, ids)
                                            .not().in(Invoice.FIELD_ID, excludeIds)
                                            .in(Invoice.FIELD_INVOICE_NUMBER, invoiceNumbers)
                                            .like(Invoice.FIELD_DESCRITION, invoiceDescriptions)
                                            .like(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_DESCRIPTION }, invoiceItemDescriptions)
                                            .between(Invoice.FIELD_GENERATED_ON, generatedOnFrom, generatedOnTo)
                                            .between(Invoice.FIELD_RECEIVED_ON, receivedOnFrom, receivedOnTo)
                                            .between(Invoice.FIELD_PAYMENT_DUE_DATE, paymentDueDateFrom, paymentDueDateTo)
                                            .in(new String[] { Invoice.FIELD_PURCHASE_ORDER, PurchaseOrder.FIELD_ID }, purchaseOrderIds)
                                            .in(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_MATERIAL, Material.FIELD_ID }, materialIds)
                                            // amount filter
                                            .between(new String[] { Invoice.FIELD_AMOUNT, Amount.FIELD_TOTAL, MoneyEntity.FIELD_AMOUNT }, totalAmountFrom, totalAmountTo)
                                            .between(new String[] { Invoice.FIELD_AMOUNT, Amount.FIELD_SUB_TOTAL, MoneyEntity.FIELD_AMOUNT }, subTotalAmountFrom, subTotalAmountTo)
                                            .between(new String[] { Invoice.FIELD_AMOUNT, Amount.FIELD_TAX_AMOUNT, TaxAmount.FIELD_PST_AMOUNT, MoneyEntity.FIELD_AMOUNT }, pstAmountFrom, pstAmountTo)
                                            .between(new String[] { Invoice.FIELD_AMOUNT, Amount.FIELD_TAX_AMOUNT, TaxAmount.FIELD_GST_AMOUNT, MoneyEntity.FIELD_AMOUNT }, gstAmountFrom, gstAmountTo)
                                            .between(new String[] { Invoice.FIELD_AMOUNT, Amount.FIELD_TAX_AMOUNT, TaxAmount.FIELD_HST_AMOUNT, MoneyEntity.FIELD_AMOUNT }, hstAmountFrom, hstAmountTo)
                                            .between(new String[] { Invoice.FIELD_AMOUNT, Amount.FIELD_TAX_AMOUNT, TaxAmount.FIELD_TOTAL_TAX_AMOUNT, MoneyEntity.FIELD_AMOUNT }, totalTaxAmountFrom, totalTaxAmountTo)
                                            // invoiceItem amount filter
                                            .between(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_AMOUNT, Amount.FIELD_TOTAL, MoneyEntity.FIELD_AMOUNT }, invoiceItemTotalAmountFrom, invoiceItemTotalAmountTo)
                                            .between(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_AMOUNT, Amount.FIELD_SUB_TOTAL, MoneyEntity.FIELD_AMOUNT }, invoiceItemSubTotalAmountFrom, invoiceItemSubTotalAmountTo)
                                            .between(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_AMOUNT, Amount.FIELD_TAX_AMOUNT, TaxAmount.FIELD_PST_AMOUNT, MoneyEntity.FIELD_AMOUNT }, invoiceItemPstAmountFrom, invoiceItemPstAmountTo)
                                            .between(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_AMOUNT, Amount.FIELD_TAX_AMOUNT, TaxAmount.FIELD_GST_AMOUNT, MoneyEntity.FIELD_AMOUNT }, invoiceItemGstAmountFrom, invoiceItemGstAmountTo)
                                            .between(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_AMOUNT, Amount.FIELD_TAX_AMOUNT, TaxAmount.FIELD_HST_AMOUNT, MoneyEntity.FIELD_AMOUNT }, invoiceItemHstAmountFrom, invoiceItemHstAmountTo)
                                            .between(new String[] { Invoice.FIELD_ITEMS, InvoiceItem.FIELD_AMOUNT, Amount.FIELD_TAX_AMOUNT, TaxAmount.FIELD_TOTAL_TAX_AMOUNT, MoneyEntity.FIELD_AMOUNT }, invoiceItemTotalTaxAmountFrom, invoiceItemTotalTaxAmountTo)
                                            .between(new String[] { Invoice.FIELD_FREIGHT, Freight.FIELD_AMOUNT, MoneyEntity.FIELD_AMOUNT }, freightAmtFrom, freightAmtTo)
                                            .in(new String[] { Invoice.FIELD_INVOICE_STATUS, InvoiceStatus.FIELD_ID }, invoiceStatusIds)
                                            .in(new String[] { Invoice.FIELD_PURCHASE_ORDER, PurchaseOrder.FIELD_SUPPLIER, Supplier.FIELD_ID }, supplierIds)
                                            .build();

        return this.repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public Invoice get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<Invoice> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<Invoice> getByAccessorIds(Collection<? extends InvoiceAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getInvoice());
    }

    @Override
    public boolean exists(Set<Long> ids) {
        return this.repoService.exists(ids);
    }

    @Override
    public boolean exist(Long id) {
        return this.repoService.exists(id);
    }

    @Override
    public long delete(Set<Long> ids) {
        return this.repoService.delete(ids);
    }

    @Override
    public long delete(Long id) {
        return this.repoService.delete(id);
    }

    @Override
    public List<Invoice> add(final List<BaseInvoice<? extends BaseInvoiceItem<?>>> additions) {
        if (additions == null) {
            return null;
        }

        final List<Invoice> entities = this.updateService.getAddEntities(additions);

        for (int i = 0; i < additions.size(); i++) {
            final List<InvoiceItem> invoiceItems = this.invoiceItemService.getAddEntities((List<BaseInvoiceItem<?>>) additions.get(i).getInvoiceItems());
            entities.get(i).setInvoiceItems(invoiceItems);
        }

        return this.repoService.saveAll(entities);
    }

    @Override
    public List<Invoice> put(List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> updates) {
        if (updates == null) {
            return null;
        }

        final List<Invoice> existing = this.repoService.getByIds(updates);
        final List<Invoice> updated = this.updateService.getPutEntities(existing, updates);

        final int length = Math.max(existing.size(), updates.size());
        for (int i = 0; i < length; i++) {
            final List<InvoiceItem> existingInvoiceItems = i < existing.size() ? existing.get(i).getInvoiceItems() : null;
            final List<? extends UpdateInvoiceItem<?>> updateInvoiceItems = i < updates.size() ? updates.get(i).getInvoiceItems() : null;

            final List<InvoiceItem> updatedInvoiceItems = this.invoiceItemService.getPutEntities(existingInvoiceItems, (List<UpdateInvoiceItem<?>>) updateInvoiceItems);

            updated.get(i).setInvoiceItems(updatedInvoiceItems);
        }

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<Invoice> patch(List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> patches) {
        if (patches == null) {
            return null;
        }

        final List<Invoice> existing = this.repoService.getByIds(patches);

        if (existing.size() != patches.size()) {
            final Set<Long> existingIds = existing.stream().map(invoice -> invoice.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patches.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find invoices with Ids: %s", nonExistingIds));
        }

        final List<Invoice> updated = this.updateService.getPatchEntities(existing, patches);

        for (int i = 0; i < existing.size(); i++) {
            final List<InvoiceItem> existingInvoiceItems = existing.get(i).getInvoiceItems();
            final List<? extends UpdateInvoiceItem<?>> updateInvoiceItems = patches.get(i).getInvoiceItems();

            final List<InvoiceItem> updatedInvoiceItems = this.invoiceItemService.getPatchEntities(existingInvoiceItems, (List<UpdateInvoiceItem<?>>) updateInvoiceItems);

            updated.get(i).setInvoiceItems(updatedInvoiceItems);
        }

        return this.repoService.saveAll(updated);
    }
}
