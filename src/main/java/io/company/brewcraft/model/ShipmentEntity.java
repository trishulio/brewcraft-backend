package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "SHIPMENT")
@Table
public class ShipmentEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_SHIPMENT_NUMBER = "shipmentNumber";
    public static final String FIELD_LOT_NUMBER = "lotNumber";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_generator")
    @SequenceGenerator(name = "shipment_generator", sequenceName = "shipment_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "shipment_number", unique = true, nullable = false)
    private String shipmentNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private InvoiceEntity invoice;

    public ShipmentEntity() {
    }

    public ShipmentEntity(Long id) {
        this();
        setId(id);
    }

    public ShipmentEntity(Long id, String shipmentNumber, InvoiceEntity invoice) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setInvoice(invoice);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }
}
