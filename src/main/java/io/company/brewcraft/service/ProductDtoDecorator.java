package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.model.EntityDecorator;

public class ProductDtoDecorator implements EntityDecorator<ProductDto> {
    private static final Logger logger = LoggerFactory.getLogger(ProductDtoDecorator.class);

    private TemporaryImageSrcDecorator imageSrcDecorator;

    public ProductDtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
        this.imageSrcDecorator = imageSrcDecorator;
    }

    @Override
    public <R extends ProductDto> void decorate(List<R> entities) {
        this.imageSrcDecorator.decorate(entities);
    }
}
