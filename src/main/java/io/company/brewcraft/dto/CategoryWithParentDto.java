package io.company.brewcraft.dto;

public class CategoryWithParentDto extends BaseDto {
    
    private Long id;
    
    private CategoryDto parentCategory;
    
    private String name;
        
    private Integer version;

    public CategoryWithParentDto() {
        super();
    }
    
    public CategoryWithParentDto(Long id, CategoryDto parentCategory, String name, Integer version) {
        super();
        this.id = id;
        this.parentCategory = parentCategory;
        this.name = name;
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

    public CategoryDto getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryDto parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
   
}
