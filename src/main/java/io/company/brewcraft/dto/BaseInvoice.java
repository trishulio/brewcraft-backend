package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.service.InvoiceStatusAccessor;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public interface BaseInvoice<T extends BaseInvoiceItem<? extends BaseInvoice<T>>> extends InvoiceStatusAccessor, PurchaseOrderAccessor {
    final String ATTR_INVOICE_NUMBER = "invoiceNumber";
    final String ATTR_DESCRIPTION = "description";
    final String ATTR_GENERATED_ON = "generatedOn";
    final String ATTR_RECEIVED_ON = "receivedOn";
    final String ATTR_PAYMENT_DUE_DATE = "paymentDueDate";
    final String ATTR_INVOICE_ITEMS = "invoiceItems";
    final String ATTR_FREIGHT = "freight";

    String getInvoiceNumber();

    void setInvoiceNumber(String invoiceNumber);

    String getDescription();

    void setDescription(String description);

    LocalDateTime getGeneratedOn();

    void setGeneratedOn(LocalDateTime generatedOn);

    LocalDateTime getReceivedOn();

    void setReceivedOn(LocalDateTime receivedOn);

    LocalDateTime getPaymentDueDate();

    void setPaymentDueDate(LocalDateTime paymentDueDate);

    List<T> getInvoiceItems();

    void setInvoiceItems(List<T> invoiceItems);

    Freight getFreight();

    void setFreight(Freight freight);
}
