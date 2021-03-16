package io.company.brewcraft.repository;

import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.Shipment;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedShipmentRepositoryImpl implements EnhancedShipmentRepository {
    
    private InvoiceRepository invoiceRepo;
    private ShipmentRepository shipmentRepo;
    
    @Autowired
    public EnhancedShipmentRepositoryImpl(ShipmentRepository shipmentRepo, InvoiceRepository invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
        this.shipmentRepo = shipmentRepo;
    }
    
    
    @Override
    public Shipment save(Long invoiceId, Shipment shipment) {
        Invoice invoice = this.invoiceRepo.findById(invoiceId).orElseThrow(() -> new EntityNotFoundException("Invoice", invoiceId));
        shipment.setInvoice(invoice);

        return this.shipmentRepo.save(shipment);
    }

}
