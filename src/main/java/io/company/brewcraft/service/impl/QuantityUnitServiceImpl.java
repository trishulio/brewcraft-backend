package io.company.brewcraft.service.impl;

import javax.measure.Unit;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.repository.QuantityUnitRepository;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;

@Transactional
public class QuantityUnitServiceImpl implements QuantityUnitService {
    
    private QuantityUnitMapper quantityUnitMapper = QuantityUnitMapper.INSTANCE;
    
    private QuantityUnitRepository quantityUnitRepositry;
    
    public QuantityUnitServiceImpl(QuantityUnitRepository quantityUnitRepositry) {
        this.quantityUnitRepositry = quantityUnitRepositry;
    }

    @Override
    public Unit<?> get(String symbol) {
        UnitEntity unit = quantityUnitRepositry.findBySymbol(symbol).orElse(null);
        
        return quantityUnitMapper.fromEntity(unit);
    }

}
