package io.company.brewcraft.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.ProductMeasure;
import io.company.brewcraft.repository.ProductMeasureRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.ProductMeasureService;

@Transactional
public class ProductMeasureServiceImpl extends BaseService implements ProductMeasureService {
        
    private ProductMeasureRepository productMeasureRepository;
    
    public ProductMeasureServiceImpl(ProductMeasureRepository productMeasureRepository) {
        this.productMeasureRepository = productMeasureRepository;        
    }

    @Override
    public List<ProductMeasure> getAllProductMeasures() { 
        List<ProductMeasure> productMeasures = productMeasureRepository.findAll();

        return productMeasures;
    }
}
