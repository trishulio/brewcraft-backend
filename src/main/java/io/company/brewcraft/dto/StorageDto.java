package io.company.brewcraft.dto;

public class StorageDto extends BaseDto {

    private Long id;
   
    private FacilityBaseDto facility;
    
    private String name;
    
    private String type;
    
    private Integer version;
    
    public StorageDto() {
        
    }
    
    public StorageDto(Long id, FacilityBaseDto facility, String name, String type, Integer version) {
        this.id = id;
        this.facility = facility;
        this.name = name;
        this.type = type;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public FacilityBaseDto getFacility() {
        return facility;
    }

    public void setFacility(FacilityBaseDto facility) {
        this.facility = facility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}