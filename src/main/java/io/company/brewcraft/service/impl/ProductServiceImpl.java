package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseProduct;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.ProductMeasureValue;
import io.company.brewcraft.model.UpdateProduct;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.MeasureService;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.ProductMeasureValueService;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class ProductServiceImpl extends BaseService implements ProductService {

    private ProductRepository productRepository;

    private ProductCategoryService productCategoryService;

    private ProductMeasureValueService productMeasureValueService;

    private MeasureService measureService;

    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryService productCategoryService, ProductMeasureValueService productMeasureValueService, MeasureService measureService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
        this.productMeasureValueService = productMeasureValueService;
        this.measureService = measureService;
    }

    @Override
    public Page<Product> getProducts(Set<Long> ids, Set<Long> categoryIds, Set<String> categoryNames, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Set<Long> categoryIdsAndDescendantIds = new HashSet<Long>();
        if (categoryIds != null || categoryNames != null) {
            Page<ProductCategory> categories = productCategoryService.getCategories(categoryIds, categoryNames, null, null, page, size, sort, orderAscending);

            if (categories.getTotalElements() == 0) {
                //If no categories are found then there can be no products with those categories assigned
                return Page.empty();
            }

            categories.forEach(category -> {
                categoryIdsAndDescendantIds.add(category.getId());
                categoryIdsAndDescendantIds.addAll(category.getDescendantCategoryIds());
            });
        }

        Specification<Product> spec = WhereClauseBuilder
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
        Product product = productRepository.findById(productId).orElse(null);

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
    public Product putProduct(Long productId, Product putProduct) {
        Product existingProduct = getProduct(productId);

        if (existingProduct != null && existingProduct.getVersion() != putProduct.getVersion()) {
            throw new ObjectOptimisticLockingFailureException(Product.class, existingProduct.getId());
        }

        if (existingProduct == null) {
            existingProduct = putProduct;
            existingProduct.setId(productId);
        } else {
            List<ProductMeasureValue> updatedProductMeasureValues = productMeasureValueService.merge(existingProduct.getTargetMeasures(), putProduct.getTargetMeasures());

            existingProduct.override(putProduct, getPropertyNames(BaseProduct.class));
            existingProduct.setTargetMeasures(updatedProductMeasureValues);
        }

        return productRepository.saveAndFlush(existingProduct);
    }

    @Override
    public Product patchProduct(Long productId, Product productPatch) {
        Product existingProduct = Optional.ofNullable(getProduct(productId)).orElseThrow(() -> new EntityNotFoundException("Product", productId.toString()));

        if (existingProduct.getVersion() != productPatch.getVersion()) {
            throw new ObjectOptimisticLockingFailureException(Product.class, existingProduct.getId());
        }

        List<ProductMeasureValue> updatedProductMeasureValues = productMeasureValueService.merge(existingProduct.getTargetMeasures(), productPatch.getTargetMeasures());

        existingProduct.outerJoin(productPatch, getPropertyNames(UpdateProduct.class));
        existingProduct.setTargetMeasures(updatedProductMeasureValues);

        return productRepository.saveAndFlush(existingProduct);
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

        if (product.getTargetMeasures() != null && !product.getTargetMeasures().isEmpty()) {
            Page<Measure> measuresPage = measureService.getMeasures(null, 0, Integer.MAX_VALUE, new TreeSet<>(List.of("id")), true);
            List<Measure> measures = measuresPage.getContent();
            Map<Long, Measure> measureMap = new HashMap<Long, Measure>();
            measures.forEach(measure -> measureMap.put(measure.getId(), measure));

            for (int i = 0; i < product.getTargetMeasures().size(); i++) {
                ProductMeasureValue measure = product.getTargetMeasures().get(i);
                if (measure.getMeasure() == null || !measureMap.containsKey(measure.getMeasure().getId())) {
                    throw new IllegalArgumentException("Invalid target measure: " + measure.getMeasure().getId());
                } else {
                    product.getTargetMeasures().get(i).setMeasure(measureMap.get(measure.getMeasure().getId()));
                }
            }
        }
    }

}
