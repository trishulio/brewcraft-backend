package io.company.brewcraft.model;

import io.company.brewcraft.service.MaterialAccessor;

public interface BaseSkuMaterial<T extends BaseSku<? extends BaseSkuMaterial<T>>> extends MaterialAccessor, QuantityAccessor {
    final String ATTR_SKU = "sku";

    T getSku();

    void setSku(T sku);

}
