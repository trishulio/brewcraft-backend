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

public class SpecAccumulator {
    private static final Logger log = LoggerFactory.getLogger(SpecAccumulator.class);

    private List<CriteriaSpec<Boolean>> aggregations;
    private boolean isNot;

    public SpecAccumulator() {
        this(new ArrayList<>());
    }

    protected SpecAccumulator(List<CriteriaSpec<Boolean>> aggregations) {
        this.aggregations = aggregations;
        this.isNot = false;
    }

    public void add(CriteriaSpec<Boolean> aggr) {
        log.debug("Not = {}", isNot);
        if (this.isNot) {
            aggr = new NotSpec(aggr);
        }

        aggr = new AndSpec(aggr);

        this.aggregations.add(aggr);
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
