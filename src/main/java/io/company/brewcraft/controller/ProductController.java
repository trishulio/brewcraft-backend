package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddProductDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.UpdateProductDto;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.service.ProductDtoDecorator;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.ProductMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends BaseController {

    private ProductService productService;
    private ProductMapper productMapper = ProductMapper.INSTANCE;
    private ProductDtoDecorator decorator;

    public ProductController(ProductService productService, AttributeFilter filter, ProductDtoDecorator decorator) {
        super(filter);
        this.productService = productService;
        this.decorator = decorator;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<ProductDto> getProducts(
        @RequestParam(required = false) Set<Long> ids,
        @RequestParam(required = false, name = "category_ids") Set<Long> categoryIds,
        @RequestParam(required = false, name = "category_names") Set<String> categoryNames,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {

        Page<Product> productsPage = productService.getProducts(ids, categoryIds, categoryNames, page, size, sort, orderAscending);

        List<ProductDto> productsList = productsPage.stream()
                                                    .map(product -> productMapper.toDto(product))
                                                    .collect(Collectors.toList());

        this.decorator.decorate(productsList);
        PageDto<ProductDto> dto = new PageDto<ProductDto>(productsList, productsPage.getTotalPages(), productsPage.getTotalElements());
        
        return dto;
    }

    @GetMapping(value = "/{productId}", consumes = MediaType.ALL_VALUE)
    public ProductDto getProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);

        Validator.assertion(product != null, EntityNotFoundException.class, "Product", productId.toString());

        ProductDto dto = productMapper.toDto(product);
        
        this.decorator.decorate(List.of(dto));
        
        return dto;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@Valid @RequestBody AddProductDto addProductDto) {
        Product product = productMapper.fromDto(addProductDto);

        Product addedProduct = productService.addProduct(product, addProductDto.getCategoryId());

        ProductDto dto = productMapper.toDto(addedProduct);
        this.decorator.decorate(List.of(dto));
        
        return dto;
    }

    @PutMapping("/{productId}")
    public ProductDto putProduct(@Valid @RequestBody UpdateProductDto updateProductDto, @PathVariable Long productId) {
        Product product = productMapper.fromDto(updateProductDto);

        Product putProduct = productService.putProduct(productId, product, updateProductDto.getCategoryId());

        ProductDto dto = productMapper.toDto(putProduct);
        this.decorator.decorate(List.of(dto));

        return dto;
    }

    @PatchMapping("/{productId}")
    public ProductDto patchProduct(@Valid @RequestBody UpdateProductDto updateProductDto, @PathVariable Long productId) {
        Product product = productMapper.fromDto(updateProductDto);

        Product patchedProduct = productService.patchProduct(productId, product, updateProductDto.getCategoryId());

        ProductDto dto = productMapper.toDto(patchedProduct);
        this.decorator.decorate(List.of(dto));
        
        return dto;
    }

    @DeleteMapping(value = "/{productId}", consumes = MediaType.ALL_VALUE)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
