package io.company.brewcraft.model;

import java.util.List;

import io.company.brewcraft.service.ProductAccessor;

public interface BaseSku<T extends BaseSkuMaterial<? extends BaseSku<T>>> extends ProductAccessor, QuantityAccessor {
    final String ATTR_MATERIALS = "materials";
    
    public String getName();
    
    public void setName(String name);
    
    public String getDescription();
    
    public void setDescription(String description);

    public List<T> getMaterials();

    public void setMaterials(List<T> materials);

}
