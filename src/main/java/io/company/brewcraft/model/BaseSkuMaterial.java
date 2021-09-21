package io.company.brewcraft.model;

import javax.measure.Quantity;

import io.company.brewcraft.service.MaterialAccessor;

public interface BaseSkuMaterial<T extends BaseSku<? extends BaseSkuMaterial<T>>> extends MaterialAccessor {
    final String ATTR_SKU = "sku";

    T getSku();

    void setSku(T sku);

    Quantity<?> getQuantity();

    void setQuantity(Quantity<?> quantity);

}
