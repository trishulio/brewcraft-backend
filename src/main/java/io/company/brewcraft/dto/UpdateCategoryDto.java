package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class UpdateCategoryDto extends BaseDto {
    private Long parentCategoryId;

    @NullOrNotBlank
    private String name;

    @NotNull
    private Integer version;

    public UpdateCategoryDto() {
        super();
    }

    public UpdateCategoryDto(Long parentCategoryId, String name, Integer version) {
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
