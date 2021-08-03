package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.MeasureService;

@Transactional
public class MeasureServiceImpl extends BaseService implements MeasureService {
        
    private MeasureRepository measureRepository;
    
    public MeasureServiceImpl(MeasureRepository measureRepository) {
        this.measureRepository = measureRepository;        
    }

    @Override
    public Page<Measure> getMeasures(Set<Long> ids, int page, int size, SortedSet<String> sort, boolean orderAscending) {
		Specification<Measure> spec = SpecificationBuilder
                .builder()
                .in(Measure.FIELD_ID, ids)
                .build();
        Page<Measure> measures = measureRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));
    	
        return measures;
    }
}
