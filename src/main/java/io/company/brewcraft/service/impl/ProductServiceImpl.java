package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class ProductServiceImpl extends BaseService implements ProductService {
        
    private ProductRepository productRepository;
    
    private ProductCategoryService productCategoryService;
        
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryService productCategoryService) {
        this.productRepository = productRepository;        
        this.productCategoryService = productCategoryService;
    }

    @Override
    public Page<Product> getProducts(Set<Long> ids, Set<Long> categoryIds, Set<String> categoryNames, int page, int size, Set<String> sort, boolean orderAscending) {
        Set<Long> categoryIdsAndDescendantIds = new HashSet<Long>();
        if (categoryIds != null || categoryNames != null) {           
            Page<ProductCategory> categories = productCategoryService.getCategories(categoryIds, categoryNames, null, null, 0, Integer.MAX_VALUE, Set.of("id"), true);            
            
            if (categories.getTotalElements() == 0) {
                //If no categories are found then there can be no products with those categories assigned
                return Page.empty();
            }
            
            categories.forEach(category -> { 
                categoryIdsAndDescendantIds.add(category.getId());
                categoryIdsAndDescendantIds.addAll(category.getDescendantCategoryIds());
            });
        }
                    
        Specification<Product> spec = SpecificationBuilder
            .builder()
            .in(Product.FIELD_ID, ids)
            .in(new String[] { Product.FIELD_CATEGORY, ProductCategory.FIELD_ID }, categoryIdsAndDescendantIds.isEmpty() ? null : categoryIdsAndDescendantIds)
            .isNull(Product.FIELD_DELETED_AT)
            .build();
        
        Page<Product> productPage = productRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return productPage;
    }

    @Override
    public Product getProduct(Long productId) {
        Product product = productRepository.findByIdsExcludeDeleted(Set.of(productId)).orElse(null);

        return product;
    }
    
    @Override
    public Product addProduct(Product product, Long categoryId) {               
        mapChildEntites(product, categoryId);
       
        return addProduct(product);
    }

    @Override
    public Product addProduct(Product product) {                                              
        Product savedEntity = productRepository.saveAndFlush(product);
        
        return savedEntity;
    }
    
    @Override
    public Product putProduct(Long productId, Product product, Long categoryId) {                
        mapChildEntites(product, categoryId);
        
        return putProduct(productId, product);
    }

    @Override
    public Product putProduct(Long productId, Product product) {                
        product.setId(productId);
        
        return addProduct(product);        
    }
    
    @Override
    public Product patchProduct(Long productId, Product productPatch) {                           
        Product product = Optional.ofNullable(getProduct(productId)).orElseThrow(() -> new EntityNotFoundException("Product", productId.toString()));     
                
        productPatch.copyToNullFields(product);     
        
        return addProduct(productPatch);
    }

    @Override
    public Product patchProduct(Long productId, Product productPatch, Long categoryId) {        
        mapChildEntites(productPatch, categoryId);

        return patchProduct(productId, productPatch);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);                        
    }
    
    @Override
    public void softDeleteProduct(Long productId) {
        productRepository.softDeleteByIds(Set.of(productId));                        
    }

    @Override
    public boolean productExists(Long productId) {
        return productRepository.existsById(productId);
    }
    
    private void mapChildEntites(Product product, Long categoryId) {
        if (categoryId != null) {
            ProductCategory category = Optional.ofNullable(productCategoryService.getCategory(categoryId)).orElseThrow(() -> new EntityNotFoundException("ProductCategory", categoryId.toString()));
            product.setCategory(category);
        }
    }
}
