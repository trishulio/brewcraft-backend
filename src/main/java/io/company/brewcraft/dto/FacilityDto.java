package io.company.brewcraft.dto;

public class FacilityDto extends BaseDto {
    private Long id;

    private String name;

    private AddressDto address;

    private String phoneNumber;

    private String faxNumber;

    private Integer version;

    public FacilityDto() {
    }

    public FacilityDto(Long id) {
        this();
        this.id = id;
    }

    public FacilityDto(Long id, String name, AddressDto address, String phoneNumber, String faxNumber, Integer version) {
        this(id);
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}