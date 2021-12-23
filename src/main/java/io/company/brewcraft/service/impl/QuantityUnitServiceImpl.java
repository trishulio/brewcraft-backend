package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Set;
import java.util.SortedSet;

import javax.measure.Unit;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.repository.QuantityUnitRepository;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;

@Transactional
public class QuantityUnitServiceImpl implements QuantityUnitService {

    private QuantityUnitMapper quantityUnitMapper = QuantityUnitMapper.INSTANCE;

    private QuantityUnitRepository quantityUnitRepository;

    public QuantityUnitServiceImpl(QuantityUnitRepository quantityUnitRepository) {
        this.quantityUnitRepository = quantityUnitRepository;
    }

    @Override
    public Page<UnitEntity> getUnits(Set<String> symbols, SortedSet<String> sort, boolean orderAscending, int page, int size) {
        Specification<UnitEntity> spec = WhereClauseBuilder
                .builder()
                .in(UnitEntity.FIELD_SYMBOL, symbols)
                .build();
        Page<UnitEntity> units = quantityUnitRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return units;
    }

    @Override
    public Unit<?> get(String symbol) {
        UnitEntity unit = quantityUnitRepository.findBySymbol(symbol).orElse(null);

        return quantityUnitMapper.fromEntity(unit);
    }
}
