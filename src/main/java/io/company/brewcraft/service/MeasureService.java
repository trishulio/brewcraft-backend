package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Measure;

public interface MeasureService {
    
    Page<Measure> getMeasures(Set<Long> ids, int page, int size, SortedSet<String> sort, boolean orderAscending);

}
