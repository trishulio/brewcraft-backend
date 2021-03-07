package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateCategory;
import io.company.brewcraft.model.ProductCategoryEntity;
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.repository.ProductCategoryRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.CategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.ProductCategoryMapper;

@Transactional
public class ProductCategoryServiceImpl extends BaseService implements CategoryService {
    
    private ProductCategoryMapper productCategoryMapper = ProductCategoryMapper.INSTANCE;
    
    private ProductCategoryRepository productCategoryRepository;
    
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Page<Category> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentCategoryIds, Set<String> parentNames, 
            int page, int size, Set<String> sort, boolean orderAscending) {

        Specification<ProductCategoryEntity> spec = SpecificationBuilder.builder()
                .in(ProductCategoryEntity.FIELD_ID, ids)
                .in(ProductCategoryEntity.FIELD_NAME, names)
                .in(new String[] { ProductCategoryEntity.FIELD_PARENT_CATEGORY, ProductCategoryEntity.FIELD_ID }, parentCategoryIds)
                .in(new String[] { ProductCategoryEntity.FIELD_PARENT_CATEGORY, ProductCategoryEntity.FIELD_NAME }, parentNames)
                .build();

        Page<ProductCategoryEntity> categoryPage = productCategoryRepository.findAll(spec,
                pageRequest(sort, orderAscending, page, size));

        return categoryPage.map(productCategory -> productCategoryMapper.fromEntity(productCategory,
                new CycleAvoidingMappingContext()));
    }

    @Override
    public Category getCategory(Long categoryId) {
        ProductCategoryEntity category = productCategoryRepository.findById(categoryId).orElse(null);    
        
        return productCategoryMapper.fromEntity(category, new CycleAvoidingMappingContext());
    }

    @Override
    public Category addCategory(Long parentCategoryId, Category productCategory) {        
        if (parentCategoryId != null) {
            Category parentCategory = Optional.ofNullable(getCategory(parentCategoryId)).orElseThrow(() -> new EntityNotFoundException("ProductCategory", parentCategoryId.toString()));
            productCategory.setParentCategory(parentCategory);
        }
        
        ProductCategoryEntity categoryEntity = productCategoryMapper.toEntity(productCategory, new CycleAvoidingMappingContext());

        ProductCategoryEntity savedEntity = productCategoryRepository.saveAndFlush(categoryEntity);
        
        return productCategoryMapper.fromEntity(savedEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Category putCategory(Long parentCategoryId, Long categoryId, UpdateCategory putCategory) {        
        Category category = getCategory(categoryId);

        if (category == null) {
            category = new Category(categoryId, null, null, null, null, null, null);
        }

        category.override(putCategory, getPropertyNames(UpdateCategory.class));
        
        return addCategory(parentCategoryId, category);
    }

    @Override
    public Category patchCategory(Long parentCategoryId, Long categoryId, UpdateCategory updateProductCategory) {                
        Category category = Optional.ofNullable(getCategory(categoryId)).orElseThrow(() -> new EntityNotFoundException("ProductCategory", categoryId.toString()));     
        
        category.outerJoin(updateProductCategory, getPropertyNames(UpdateCategory.class));

        return addCategory(parentCategoryId, category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        productCategoryRepository.deleteById(categoryId);                
    }

    @Override
    public boolean categoryExists(Long categoryId) {
        return productCategoryRepository.existsById(categoryId);
    }
    
}
