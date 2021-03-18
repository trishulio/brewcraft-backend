package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SUPPLIER_ADDRESS")
public class SupplierAddress extends AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_address_generator")
    @SequenceGenerator(name="supplier_address_generator", sequenceName = "supplier_address_sequence", allocationSize = 1)
    private Long id;
    
    public SupplierAddress() {
        
    }
    
    public SupplierAddress(Long id, String addressLine1, String addressLine2, String country, String province, String city,
            String postalCode, LocalDateTime created, LocalDateTime lastUpdated) {
        super(addressLine1, addressLine2, country, province, city, postalCode, created, lastUpdated);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
