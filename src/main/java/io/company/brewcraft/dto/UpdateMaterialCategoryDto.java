package io.company.brewcraft.dto;

public class UpdateMaterialCategoryDto extends BaseDto {
        
    private Long parentCategoryId;
    
    private String name;
    
    private Integer version;

    public UpdateMaterialCategoryDto() {
        super();
    }
    
    public UpdateMaterialCategoryDto(Long parentCategoryId, String name, Integer version) {
        super();
        this.parentCategoryId = parentCategoryId;
        this.name = name;
        this.version = version;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
   
}
