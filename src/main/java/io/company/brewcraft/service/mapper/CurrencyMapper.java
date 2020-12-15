package io.company.brewcraft.service.mapper;

import org.joda.money.CurrencyUnit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Currency;

@Mapper
public abstract class CurrencyMapper {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyMapper.class);

    public static final CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);
    
    public Currency toEntity(String code) {
        Currency entity = null;
        if (code != null) {
            CurrencyUnit unit = CurrencyUnit.of(code);
            entity = toEntity(unit);    
        }
        return entity;
    }    
    
    public Currency toEntity(CurrencyUnit unit) {
        Currency entity = null;
        if (unit != null) {
            entity = new Currency(unit.getNumericCode(), unit.getCode());
        }
        return entity;
    }
}
