package io.company.brewcraft.dto;

import javax.validation.constraints.NotEmpty;

public class AddCategoryDto extends BaseDto {
        
    private Long parentCategoryId;
    
    @NotEmpty
    private String name;
        
    public AddCategoryDto() {
        super();
    }
    
    public AddCategoryDto(Long parentCategoryId, String name) {
        super();
        this.name = name;
        this.parentCategoryId = parentCategoryId;
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
}
