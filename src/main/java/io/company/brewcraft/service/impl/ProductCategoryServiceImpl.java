package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.repository.ProductCategoryRepository;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class ProductCategoryServiceImpl extends BaseService implements ProductCategoryService {
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Page<ProductCategory> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentCategoryIds, Set<String> parentNames,
            int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<ProductCategory> spec = WhereClauseBuilder.builder()
                .in(ProductCategory.FIELD_ID, ids)
                .in(ProductCategory.FIELD_NAME, names)
                .in(new String[] { ProductCategory.FIELD_PARENT_CATEGORY, ProductCategory.FIELD_ID }, parentCategoryIds)
                .in(new String[] { ProductCategory.FIELD_PARENT_CATEGORY, ProductCategory.FIELD_NAME }, parentNames)
                .build();

        Page<ProductCategory> categoryPage = productCategoryRepository.findAll(spec,
                pageRequest(sort, orderAscending, page, size));

        return categoryPage;
    }

    @Override
    public ProductCategory getCategory(Long categoryId) {
        ProductCategory category = productCategoryRepository.findById(categoryId).orElse(null);

        return category;
    }

    @Override
    public ProductCategory addCategory(Long parentCategoryId, ProductCategory productCategory) {
        if (parentCategoryId != null) {
            ProductCategory parentCategory = Optional.ofNullable(getCategory(parentCategoryId)).orElseThrow(() -> new EntityNotFoundException("ProductCategory", parentCategoryId.toString()));
            productCategory.setParentCategory(parentCategory);
        }

        ProductCategory savedEntity = productCategoryRepository.saveAndFlush(productCategory);

        return savedEntity;
    }

    @Override
    public ProductCategory putCategory(Long parentCategoryId, Long categoryId, ProductCategory putCategory) {
        putCategory.setId(categoryId);

        return addCategory(parentCategoryId, putCategory);
    }

    @Override
    public ProductCategory patchCategory(Long parentCategoryId, Long categoryId, ProductCategory updateProductCategory) {
        ProductCategory category = Optional.ofNullable(getCategory(categoryId)).orElseThrow(() -> new EntityNotFoundException("ProductCategory", categoryId.toString()));

        updateProductCategory.copyToNullFields(category);

        return addCategory(parentCategoryId, updateProductCategory);
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
