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

import io.company.brewcraft.service.Aggregation;

public class SpecAccumulator {
    private static final Logger log = LoggerFactory.getLogger(SpecAccumulator.class);

    private List<Aggregation> aggregations;
    private boolean isNot;

    public SpecAccumulator() {
        this.aggregations = new ArrayList<>();
        this.isNot = false;
    }

    public void add(Aggregation aggr) {
        log.debug("Not = {}", isNot);
        if (this.isNot) {
            Aggregation orig = aggr;
            aggr = (root, query, criteriaBuilder) -> criteriaBuilder.not((Predicate) orig.getExpression(root, query, criteriaBuilder));
        }

        final Aggregation ref = aggr;
        Aggregation combined = (root, query, criteriaBuilder) -> criteriaBuilder.and((Predicate) ref.getExpression(root, query, criteriaBuilder));
        this.aggregations.add(combined);
    }

    public void setIsNot(boolean isNot) {
        this.isNot = isNot;
    }

    public Predicate[] getPredicates(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicates = new Predicate[this.aggregations.size()];
        log.debug("Total Predicates = {}", predicates.length);
        predicates = this.aggregations.stream().map(aggr -> aggr.getExpression(root, query, criteriaBuilder)).collect(Collectors.toList()).toArray(predicates);

        return predicates;
    }

}
