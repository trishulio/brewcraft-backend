package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.PurchaseOrder;

public interface BaseInvoice<T extends BaseInvoiceItem> {
    String getInvoiceNumber();

    void setInvoiceNumber(String invoiceNumber);

    String getDescription();

    void setDescription(String description);

    void setPurchaseOrder(PurchaseOrder purchaseOrder);

    PurchaseOrder getPurchaseOrder();

    LocalDateTime getGeneratedOn();

    void setGeneratedOn(LocalDateTime generatedOn);

    LocalDateTime getReceivedOn();

    void setReceivedOn(LocalDateTime receivedOn);

    LocalDateTime getPaymentDueDate();

    void setPaymentDueDate(LocalDateTime paymentDueDate);

    InvoiceStatus getStatus();

    void setStatus(InvoiceStatus status);

    List<T> getItems();

    void setItems(List<T> items);

    Freight getFreight();

    void setFreight(Freight freight);
}
