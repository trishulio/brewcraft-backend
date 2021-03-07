package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
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
import io.company.brewcraft.pojo.Product;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.ProductMapper;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    
    private ProductService productService;
    
    private ProductMapper productMapper = ProductMapper.INSTANCE;
        
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<ProductDto> getProducts(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(required = false) Set<Long> categoryIds,
            @RequestParam(required = false) Set<String> categoryNames,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true") boolean orderAscending) {
        
        Page<Product> productsPage = productService.getProducts(ids, categoryIds, categoryNames, page, size, sort, orderAscending);
        
        List<ProductDto> productsList = productsPage.stream()
                .map(product -> productMapper.toDto(product)).collect(Collectors.toList());

        PageDto<ProductDto> dto = new PageDto<ProductDto>(productsList, productsPage.getTotalPages(), productsPage.getTotalElements());
        
        return dto;
    }
        
    @GetMapping(value = "/{productId}", consumes = MediaType.ALL_VALUE)
    public ProductDto getProduct(@PathVariable Long productId) {
        Validator validator = new Validator();

        Product product = productService.getProduct(productId);
        
        validator.assertion(product != null, EntityNotFoundException.class, "Product", productId.toString());

        return productMapper.toDto(product);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@Valid @RequestBody AddProductDto addProductDto) {
        Product product = productMapper.fromDto(addProductDto);
        
        Product addedProduct = productService.addProduct(product, addProductDto.getCategoryId());
        
        return productMapper.toDto(addedProduct);
    }
    
    @PutMapping("/{productId}")
    public ProductDto putProduct(@Valid @RequestBody UpdateProductDto updateProductDto, @PathVariable Long productId) {
        Product product = productMapper.fromDto(updateProductDto);
        
        Product putProduct = productService.putProduct(productId, product, updateProductDto.getCategoryId());

        return productMapper.toDto(putProduct);
    }
    
    @PatchMapping("/{productId}")
    public ProductDto patchProduct(@Valid @RequestBody UpdateProductDto updateProductDto, @PathVariable Long productId) {        
        Product product = productMapper.fromDto(updateProductDto);
        
        Product patchedProduct = productService.patchProduct(productId, product, updateProductDto.getCategoryId());
        
        return productMapper.toDto(patchedProduct);
    }

    @DeleteMapping(value = "/{productId}", consumes = MediaType.ALL_VALUE)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
