package io.company.brewcraft.service.factory;


import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ShipmentFactory {

    public Shipment createFromInvoice(final Invoice invoice) {
        Shipment shipment = new Shipment();
        List<MaterialLot> lots = invoice.getItems().stream().map(item -> createMaterialLot(item, invoice.getInvoiceNumber())).collect(Collectors.toList());
        shipment.setLots(lots);
        shipment.setShipmentNumber(invoice.getInvoiceNumber());
        final ShipmentStatus shipmentStatus = new ShipmentStatus(invoice.getStatus().getId());
        shipment.setStatus(shipmentStatus);
        shipment.setDeliveredDate(invoice.getReceivedOn());
        shipment.setDeliveryDueDate(invoice.getPaymentDueDate());
        return shipment;
    }

    private MaterialLot createMaterialLot(final InvoiceItem item, final String lotNumber) {
        final MaterialLot materialLot = new MaterialLot();
        materialLot.setInvoiceItem(item);
        materialLot.setLotNumber(lotNumber);
        materialLot.setQuantity(item.getQuantity());
        return materialLot;
    }
}
