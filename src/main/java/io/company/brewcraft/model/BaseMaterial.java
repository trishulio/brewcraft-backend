package io.company.brewcraft.model;

public interface BaseMaterial extends BaseQuantityUnitAccessor {
    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    MaterialCategory getCategory();

    void setCategory(MaterialCategory category);

    String getUPC();

    void setUPC(String upc);

    String getImageSrc();

    void setImageSrc(String imageSrc);
}
