package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.UpdateProduct;
import io.company.brewcraft.pojo.Product;

public interface ProductService {

    public Page<Product> getProducts(Set<Long> ids, Set<Long> categoryIds, Set<String> categoryNames, int page, int size, Set<String> sort, boolean orderAscending);
    
    public Product getProduct(Long productId);

    public Product addProduct(Product product);
    
    public Product addProduct(Product product, Long categoryId);        
        
    public Product putProduct(Long productId, UpdateProduct product);
    
    public Product putProduct(Long productId, UpdateProduct product, Long categoryId);
    
    public Product patchProduct(Long productId, UpdateProduct product);
    
    public Product patchProduct(Long productId, UpdateProduct product, Long categoryId);

    public void deleteProduct(Long productId);
    
    public boolean productExists(Long productId);
    
 }
