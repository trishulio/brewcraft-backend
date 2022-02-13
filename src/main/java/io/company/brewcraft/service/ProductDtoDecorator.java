package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.model.EntityDecorator;
import io.company.brewcraft.model.TemporaryImageSrcDecorator;

public class ProductDtoDecorator implements EntityDecorator<ProductDto> {
    private TemporaryImageSrcDecorator imageSrcDecorator;

    public ProductDtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
        this.imageSrcDecorator = imageSrcDecorator;
    }

    @Override
    public <R extends ProductDto> void decorate(List<R> entities) {
        this.imageSrcDecorator.decorate(entities);
    }
}
