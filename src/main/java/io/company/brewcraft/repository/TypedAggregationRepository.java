package io.company.brewcraft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.service.Selector;

public class TypedAggregationRepository<T extends BaseEntity> {
    private AggregationRepository baseRepo;
    private Class<T> clazz;
    
    public TypedAggregationRepository(AggregationRepository baseRepo, Class<T> clazz) {
        this.baseRepo = baseRepo;
        this.clazz = clazz;
    }
    
    public Page<T> getAggregation(Selector selection, Specification<T> spec, Pageable pageable) {
        return this.baseRepo.getAggregation(clazz, selection, null, spec, pageable);
    }
    
    public Page<T> getAggregation(Selector selection, Selector groupBy, Specification<T> spec, Pageable pageable) {
        return this.baseRepo.getAggregation(clazz, selection, groupBy, spec, pageable);
    }
}
