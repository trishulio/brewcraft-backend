package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.BaseProduct;
import io.company.brewcraft.dto.UpdateProduct;
import io.company.brewcraft.model.ProductCategoryEntity;
import io.company.brewcraft.model.ProductEntity;
import io.company.brewcraft.pojo.Product;
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.CategoryService;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.ProductMapper;

@Transactional
public class ProductServiceImpl extends BaseService implements ProductService {
    
    private ProductMapper productMapper = ProductMapper.INSTANCE;
    
    private ProductRepository productRepository;
    
    private CategoryService productCategoryService;
        
    public ProductServiceImpl(ProductRepository productRepository, CategoryService productCategoryService) {
        this.productRepository = productRepository;        
        this.productCategoryService = productCategoryService;
    }

    @Override
    public Page<Product> getProducts(Set<Long> ids, Set<Long> categoryIds, Set<String> categoryNames, int page, int size, Set<String> sort, boolean orderAscending) {
        
        Set<Long> categoryIdsAndDescendantIds = new HashSet<Long>();
        if (categoryIds != null || categoryNames != null) {           
            Page<Category> categories = productCategoryService.getCategories(categoryIds, categoryNames, null, null, 0, Integer.MAX_VALUE, Set.of("id"), true);            
            
            if (categories.getTotalElements() == 0) {
                //If no categories are found then there can be no products with those categories assigned
                return Page.empty();
            }
            
            categories.forEach(category -> { 
                categoryIdsAndDescendantIds.add(category.getId());
                categoryIdsAndDescendantIds.addAll(category.getDescendantCategoryIds());
            });
        }
                     
        Specification<ProductEntity> spec = SpecificationBuilder
                .builder()
                .in(ProductEntity.FIELD_ID, ids)
                .in(new String[] { ProductEntity.FIELD_CATEGORY, ProductCategoryEntity.FIELD_ID }, categoryIdsAndDescendantIds.isEmpty() ? null : categoryIdsAndDescendantIds)
                .in(new String[] { ProductEntity.FIELD_CATEGORY, ProductCategoryEntity.FIELD_NAME }, categoryIdsAndDescendantIds.isEmpty() ? categoryNames : null)
                .build();
        
        Page<ProductEntity> productPage = productRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return productPage.map(product -> productMapper.fromEntity(product, new CycleAvoidingMappingContext()));
    }

    @Override
    public Product getProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElse(null);
        
        return productMapper.fromEntity(product, new CycleAvoidingMappingContext());
    }
    
    @Override
    public Product addProduct(Product product, Long categoryId) {               
        mapChildEntites(product, categoryId);
       
        return addProduct(product);
    }

    @Override
    public Product addProduct(Product product) {                        
        ProductEntity productEntity = productMapper.toEntity(product, new CycleAvoidingMappingContext());
                                  
        ProductEntity savedEntity = productRepository.saveAndFlush(productEntity);
        
        return productMapper.fromEntity(savedEntity, new CycleAvoidingMappingContext());
    }
    
    @Override
    public Product putProduct(Long productId, UpdateProduct product, Long categoryId) {                
        mapChildEntites(product, categoryId);
        
        return putProduct(productId, product);
    }

    @Override
    public Product putProduct(Long productId, UpdateProduct product) {                
        Product existing = getProduct(productId);

        if (existing == null) {
            existing = new Product(productId, null, null, null, null, null, null, null);
        }

        existing.override(product, getPropertyNames(UpdateProduct.class));
        
        return addProduct(existing);        
    }
    
    @Override
    public Product patchProduct(Long productId, UpdateProduct productPatch) {                           
        Product product = Optional.ofNullable(getProduct(productId)).orElseThrow(() -> new EntityNotFoundException("Product", productId.toString()));     
                
        product.outerJoin(productPatch, getPropertyNames(UpdateProduct.class));
                
        return addProduct(product);
    }

    @Override
    public Product patchProduct(Long productId, UpdateProduct productPatch, Long categoryId) {        
        mapChildEntites(productPatch, categoryId);

        return patchProduct(productId, productPatch);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);                        
    }

    @Override
    public boolean productExists(Long productId) {
        return productRepository.existsById(productId);
    }
    
    private void mapChildEntites(BaseProduct product, Long categoryId) {
        if (categoryId != null) {
            Category category = Optional.ofNullable(productCategoryService.getCategory(categoryId)).orElseThrow(() -> new EntityNotFoundException("ProductCategory", categoryId.toString()));
            product.setCategory(category);
        }
    }
}
