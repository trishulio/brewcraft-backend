package io.company.brewcraft.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.util.entity.ReflectionManipulator;

public class EquipmentRepositoryGetAllEquipmentSpecification implements Specification<EquipmentEntity> {
    private static final long serialVersionUID = -6015366615586119965L;
    
    private Set<Long> ids;
    private Set<String> types;
    private Set<String> statuses;
    private Set<Long> facilityIds;

    public EquipmentRepositoryGetAllEquipmentSpecification() {
        this(null, null, null, null);
    }

    public EquipmentRepositoryGetAllEquipmentSpecification(Set<Long> ids, Set<String> types, Set<String> statuses, Set<Long> facilityIds) {
        this.ids = ids;
        this.types = types;
        this.statuses = statuses;
        this.facilityIds = facilityIds;
    }

    @Override
    public Predicate toPredicate(Root<EquipmentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        @SuppressWarnings("unchecked")
        CriteriaQuery<EquipmentEntity> equipmentQuery = (CriteriaQuery<EquipmentEntity>) query;
        return criteriaBuilder.and(this.getPredicates(root, equipmentQuery, criteriaBuilder));
    }

    public Predicate[] getPredicates(Root<EquipmentEntity> root, CriteriaQuery<EquipmentEntity> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>(10);
        
        if (ids != null && ids.size() > 0) {
            predicates.add(criteriaBuilder.and(root.get("id").in(ids)));
        }

        if (types != null && types.size() > 0) {
            predicates.add(criteriaBuilder.and(root.get("type").in(types)));
        }
        
        if (statuses != null && statuses.size() > 0) {
            predicates.add(criteriaBuilder.and(root.get("status").in(statuses)));
        }

        if (facilityIds != null && facilityIds.size() > 0) {
            predicates.add(criteriaBuilder.and(root.get("facility").get("id").in(facilityIds)));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public boolean equals(Object o) {
        return ReflectionManipulator.INSTANCE.equals(this, o);
    }

}
