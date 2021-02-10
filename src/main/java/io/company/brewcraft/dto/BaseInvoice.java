package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.pojo.Freight;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.InvoiceStatus;

public interface BaseInvoice {
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

    LocalDateTime getLastUpdated();

    void setLastUpdated(LocalDateTime lastUpdated);

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    InvoiceStatus getStatus();

    public void setStatus(InvoiceStatus status);

    List<InvoiceItem> getItems();

    public void setItems(List<InvoiceItem> items);

    Freight getFreight();

    public void setFreight(Freight freight);
}
