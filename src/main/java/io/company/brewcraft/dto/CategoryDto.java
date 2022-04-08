package io.company.brewcraft.dto;

public class CategoryDto extends BaseDto {
    private Long id;

    private Long parentCategoryId;

    private String name;

    private Integer version;

    public CategoryDto() {
        super();
    }

    public CategoryDto(Long id) {
        this.id = id;
    }

    public CategoryDto(Long id, Long parentCategoryId, String name, Integer version) {
        this(id);
        this.parentCategoryId = parentCategoryId;
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

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
