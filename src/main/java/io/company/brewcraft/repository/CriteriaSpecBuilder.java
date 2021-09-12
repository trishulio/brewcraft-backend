package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.Aggregation;

public class CriteriaSpecBuilder {
    private static final Logger log = LoggerFactory.getLogger(CriteriaSpecBuilder.class);

    private SpecAccumulator accumulator;
    
    public CriteriaSpecBuilder(SpecAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    public void not() {
        this.accumulator.setIsNot(true);
    }

    public void isNull(String[] joins, String[] paths) {
        Aggregation aggr = (root, query, criteriaBuilder) -> criteriaBuilder.isNull(new DeepRoot(root).get(joins, paths));
        accumulator.add(aggr);

        accumulator.setIsNot(false);
    }

    public void in(String[] joins, String[] paths, Collection<?> collection) {
        if (collection != null) {
            Aggregation aggr = (root, query, criteriaBuilder) -> new DeepRoot(root).get(joins, paths).in(collection);
            accumulator.add(aggr);
        }

        accumulator.setIsNot(false);
    }

    public void like(String[] joins, String[] paths, Set<String> queries) {
        if (queries != null) {
            for (String text : queries) {
                if (text != null) {
                    Aggregation aggr = (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(new DeepRoot(root).get(joins, paths)), String.format("%%%s%%", text.toLowerCase()));
                    accumulator.add(aggr);
                }
            }
        }
    }

    public <C extends Comparable<C>> void between(String[] joins, String[] paths, C start, C end) {
        Aggregation aggr = null;
        if (start != null && end != null) {
            aggr = (root, query, criteriaBuilder) -> criteriaBuilder.between(new DeepRoot(root).get(joins, paths), start, end);
        } else if (start != null) {
            aggr = (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(new DeepRoot(root).get(joins, paths), start);
        } else if (end != null) {
            aggr = (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(new DeepRoot(root).get(joins, paths), end);
        }

        if (aggr != null) {
            accumulator.add(aggr);
        }

        accumulator.setIsNot(false);
    }

    public <T> Specification<T> build() {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = this.accumulator.getPredicates(root, query, criteriaBuilder);
            return criteriaBuilder.and(predicates);
        };
    }
}
