package io.company.brewcraft.service;

import io.company.brewcraft.model.Product;

public interface ProductAccessor {
    final String ATTR_PRODUCT = "product";

    Product getProduct();

    void setProduct(Product product);
}
