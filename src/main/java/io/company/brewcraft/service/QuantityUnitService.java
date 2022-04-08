package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import javax.measure.Unit;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.UnitEntity;

public interface QuantityUnitService {
    Page<UnitEntity> getUnits(Set<String> symbols, SortedSet<String> sort, boolean orderAscending, int page, int size);

    public Unit<?> get(String symbol);

 }
