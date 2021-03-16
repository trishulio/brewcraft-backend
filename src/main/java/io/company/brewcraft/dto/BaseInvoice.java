package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import io.company.brewcraft.pojo.BaseInvoiceItem;
import io.company.brewcraft.pojo.Freight;
import io.company.brewcraft.pojo.InvoiceStatus;

public interface BaseInvoice<T extends BaseInvoiceItem> {
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

    InvoiceStatus getStatus();

    void setStatus(InvoiceStatus status);

    Collection<T> getItems();

    void setItems(Collection<T> items);

    Freight getFreight();

    void setFreight(Freight freight);
}
