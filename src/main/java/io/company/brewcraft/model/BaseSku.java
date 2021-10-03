package io.company.brewcraft.model;

import java.util.List;

import javax.measure.Quantity;

import io.company.brewcraft.service.ProductAccessor;

public interface BaseSku<T extends BaseSkuMaterial<? extends BaseSku<T>>> extends ProductAccessor {
    final String ATTR_MATERIALS = "materials";

    public List<T> getMaterials();

    public void setMaterials(List<T> materials);

    public Quantity<?> getQuantity();

    public void setQuantity(Quantity<?> quantity);

}
