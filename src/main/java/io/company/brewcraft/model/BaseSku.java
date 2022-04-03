package io.company.brewcraft.model;

import io.company.brewcraft.service.ProductAccessor;
import io.company.brewcraft.service.SkuMaterialAccessor;

public interface BaseSku<T extends BaseSkuMaterial<? extends BaseSku<T>>> extends ProductAccessor, QuantityAccessor, SkuMaterialAccessor {
    final String ATTR_MATERIALS = "materials";

    String getNumber();

    void setNumber(String number);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    boolean getIsPackageable();

    void setIsPackageable(boolean isPackageable);

}
