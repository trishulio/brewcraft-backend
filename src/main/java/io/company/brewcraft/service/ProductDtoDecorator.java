package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.model.EntityDecorator;
import io.company.brewcraft.model.TemporaryImageSrcDecorator;

public class ProductDtoDecorator implements EntityDecorator<ProductDto> {
    private static final Logger logger = LoggerFactory.getLogger(ProductDtoDecorator.class);

    private TemporaryImageSrcDecorator imageSrcDecorator;

    public ProductDtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
        this.imageSrcDecorator = imageSrcDecorator;
    }

    @Override
    public <R extends ProductDto> void decorate(List<R> entities) {
        // Catching the exception so that the request doesn't fail at the controller level.
        // The service call will have completed at this point so the operation would have.
        // This is a temporary hack. Need ideas on where decorating the entity would be ideal.
        try {
            this.imageSrcDecorator.decorate(entities);
        } catch (Exception e) {
            logger.error("Failed to decorate the product DTO entities.");
        }
    }
}
