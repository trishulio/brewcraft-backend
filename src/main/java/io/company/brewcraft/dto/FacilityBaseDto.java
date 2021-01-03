package io.company.brewcraft.dto;

public class FacilityBaseDto extends BaseDto {

    private Long id;
    
    private String name;
    
    private AddressDto address;
        
    private Integer version;
    
    public FacilityBaseDto() {
        super();
    }
    
    public FacilityBaseDto(Long id, String name, AddressDto address, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}