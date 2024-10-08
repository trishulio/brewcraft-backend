package io.company.brewcraft.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.AndSpec;
import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.NotSpec;

public class PredicateSpecAccumulator {
    private static final Logger log = LoggerFactory.getLogger(PredicateSpecAccumulator.class);

    private List<CriteriaSpec<Boolean>> aggregations;
    private boolean isNot;
    private Boolean isPredicate;

    public PredicateSpecAccumulator() {
        this(new ArrayList<>());
    }

    protected PredicateSpecAccumulator(List<CriteriaSpec<Boolean>> aggregations) {
        this.aggregations = aggregations;
        this.isNot = false;
        this.isPredicate = true;
    }

    public void add(CriteriaSpec<Boolean> spec) {
        if (this.isPredicate == null || !this.isPredicate) {
            this.isNot = false;
            this.isPredicate = true;
            return;
        }

        if (this.isNot) {
            spec = new NotSpec(spec);
        }

        spec = new AndSpec(spec);

        this.aggregations.add(spec);
    }

    public void setIsNot(boolean isNot) {
        this.isNot = isNot;
    }

    public void setIsPredicate(Boolean isPredicate) {
        this.isPredicate = isPredicate;
    }

    public Predicate[] getPredicates(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicates = new Predicate[this.aggregations.size()];
        predicates = this.aggregations.stream().map(spec -> spec.getExpression(root, query, criteriaBuilder)).toList().toArray(predicates);

        return predicates;
    }
}
