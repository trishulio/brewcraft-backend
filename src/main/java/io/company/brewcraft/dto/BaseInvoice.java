package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.service.InvoiceStatusAccessor;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public interface BaseInvoice<T extends BaseInvoiceItem> extends InvoiceStatusAccessor, PurchaseOrderAccessor {
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

    List<T> getItems();

    void setItems(List<T> items);

    Freight getFreight();

    void setFreight(Freight freight);
}
