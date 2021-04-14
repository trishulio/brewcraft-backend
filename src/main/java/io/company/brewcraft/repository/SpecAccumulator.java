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

import io.company.brewcraft.data.TriFunction;

public class SpecAccumulator {
    private static final Logger log = LoggerFactory.getLogger(SpecAccumulator.class);

    private List<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> funcs;
    private boolean isNot;

    public SpecAccumulator() {
        this.funcs = new ArrayList<>();
        this.isNot = false;
    }

    public void add(TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> func) {
        log.debug("Not = {}", isNot);
        if (this.isNot) {
            TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> orig = func;
            func = (root, query, criteriaBuilder) -> criteriaBuilder.not(orig.apply(root, query, criteriaBuilder));
        }

        final TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> ref = func;
        TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> combined = (root, query, criteriaBuilder) -> criteriaBuilder.and(ref.apply(root, query, criteriaBuilder));
        this.funcs.add(combined);
    }

    public void setIsNot(boolean isNot) {
        this.isNot = isNot;
    }

    public Predicate[] getPredicates(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicates = new Predicate[this.funcs.size()];
        log.debug("Total Predicates = {}", predicates.length);
        predicates = this.funcs.stream().map(func -> func.apply(root, query, criteriaBuilder)).collect(Collectors.toList()).toArray(predicates);

        return predicates;
    }

}
