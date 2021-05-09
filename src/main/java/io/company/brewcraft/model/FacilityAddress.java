package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FACILITY_ADDRESS")
public class FacilityAddress extends AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_address_generator")
    @SequenceGenerator(name = "facility_address_generator", sequenceName = "facility_address_sequence", allocationSize = 1)
    private Long id;

    public FacilityAddress() {
    }

    public FacilityAddress(Long id) {
        this.id = id;
    }

    public FacilityAddress(Long id, String addressLine1, String addressLine2, String country, String province, String city, String postalCode, LocalDateTime created, LocalDateTime lastUpdated) {
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
