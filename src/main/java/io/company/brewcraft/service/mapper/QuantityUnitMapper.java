package io.company.brewcraft.service.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.measure.Unit;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Volume;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.dto.UnitDto;
import io.company.brewcraft.model.UnitEntity;
import tec.units.ri.quantity.QuantityDimension;
import tec.units.ri.unit.BaseUnit;
import tec.units.ri.unit.MetricPrefix;
import tec.units.ri.unit.Units;

@Mapper
public abstract class QuantityUnitMapper {
    private static final Logger logger = LoggerFactory.getLogger(QuantityUnitMapper.class);

    public static final QuantityUnitMapper INSTANCE = Mappers.getMapper(QuantityUnitMapper.class);

    private final Map<String, Unit<?>> map;

    public QuantityUnitMapper() {
        this.map = getAllUnits();
    }

    public Unit<?> fromSymbol(String symbol) {
        Unit<?> unit = null;
        if (symbol != null) {
            unit = this.map.get(symbol);
        }

        logger.info("Mapping for symbol: {} is: {}", symbol, unit);

        return unit;
    }

    public Unit<?> fromEntity(UnitEntity entity) {
        Unit<?> unit = null;
        if (entity != null) {
            unit = fromSymbol(entity.getSymbol());
        }
        return unit;
    }

    public abstract String toSymbol(Unit<?> unit);

    public abstract UnitEntity toEntity(Unit<?> unit);
    
    Unit<?> fromDto(UnitDto dto) {
        Unit<?> unit = null;
        if (dto != null) {
            unit = fromSymbol(dto.getSymbol());
        }

        return unit;
    }
    
    public abstract UnitDto toDto(Unit<?> unit);

    private Map<String, Unit<?>> getAllUnits() {
        try {

            Field[] fields = Units.class.getFields();

            Predicate<Integer> isPublicStaticFinal = mod -> Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod);

            Function<Field, Object> getValue = field -> {
                try {
                    return field.get(null);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    String msg = String.format("Failed to retrieve the field value because: %s", e.getMessage());
                    logger.error(msg);
                    throw new RuntimeException(msg, e.getCause());
                }
            };

            Map<String, Unit<?>> unitMap = Arrays.stream(fields)
                         .filter(field -> isPublicStaticFinal.test(field.getModifiers()))
                         .map(getValue)
                         .filter(o -> o instanceof Unit)
                         .collect(Collectors.toMap(o -> ((Unit<?>) o).toString(), o -> (Unit<?>) o));
            
            //Define custom unit types here
            Unit<AmountOfSubstance> EACH = new BaseUnit<AmountOfSubstance>("each", QuantityDimension.AMOUNT_OF_SUBSTANCE);
            Unit<Volume> HECTOLITRE = MetricPrefix.HECTO(Units.LITRE);

            unitMap.put("each", EACH);
            unitMap.put("hl", HECTOLITRE);
                  
            return unitMap;
        } catch (IllegalArgumentException e) {
            String msg = String.format("Failed to fetch property descriptors for class: %s because %s", QuantityUnitMapper.class.getName(), e.getMessage());
            throw new RuntimeException(msg, e.getCause());
        }
    }
}
