package io.company.brewcraft.dto;

import java.net.URI;
import java.util.List;

import io.company.brewcraft.model.DecoratedIaasObjectStoreFileAccessor;

public class ProductDto extends BaseDto implements DecoratedIaasObjectStoreFileAccessor {
    private Long id;

    private String name;

    private String description;

    private CategoryDto productClass;

    private CategoryDto type;

    private CategoryDto style;

    private List<ProductMeasureValueDto> targetMeasures;

    private URI imageSrc;

    private IaasObjectStoreFileDto objectStoreFile;

    private Integer version;

    public ProductDto() {
        super();
    }

    public ProductDto(Long id) {
        this();
        this.id = id;
    }

    public ProductDto(Long id, String name, String description, CategoryDto productClass, CategoryDto type,
            CategoryDto style, List<ProductMeasureValueDto> targetMeasures, URI imageSrc, Integer version) {
        this(id);
        this.name = name;
        this.description = description;
        this.productClass = productClass;
        this.type = type;
        this.style = style;
        this.targetMeasures = targetMeasures;
        this.imageSrc = imageSrc;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDto getProductClass() {
        return productClass;
    }

    public void setProductClass(CategoryDto productClass) {
        this.productClass = productClass;
    }

    public CategoryDto getType() {
        return type;
    }

    public void setType(CategoryDto type) {
        this.type = type;
    }

    public CategoryDto getStyle() {
        return style;
    }

    public void setStyle(CategoryDto style) {
        this.style = style;
    }

    public List<ProductMeasureValueDto> getTargetMeasures() {
        return targetMeasures;
    }

    public void setTargetMeasures(List<ProductMeasureValueDto> targetMeasures) {
        this.targetMeasures = targetMeasures;
    }

    @Override
    public URI getImageSrc() {
        return this.imageSrc;
    }

    public void setImageSrc(URI imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public void setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile) {
        this.objectStoreFile = objectStoreFile;
    }

    public IaasObjectStoreFileDto getObjectStoreFile() {
        return this.objectStoreFile;
    }
}
