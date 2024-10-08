package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Product;

public interface ProductService {
    public Page<Product> getProducts(Set<Long> ids, Set<Long> categoryIds, Set<String> categoryNames, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public Product getProduct(Long productId);

    public Product addProduct(Product product);

    public Product addProduct(Product product, Long categoryId);

    public Product putProduct(Long productId, Product product);

    public Product putProduct(Long productId, Product product, Long categoryId);

    public Product patchProduct(Long productId, Product product);

    public Product patchProduct(Long productId, Product product, Long categoryId);

    public void deleteProduct(Long productId);

    public void softDeleteProduct(Long productId);

    public boolean productExists(Long productId);

 }
