package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.BetweenSpec;
import io.company.brewcraft.service.ColumnSpec;
import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.InSpec;
import io.company.brewcraft.service.IsNullSpec;
import io.company.brewcraft.service.IsSpec;
import io.company.brewcraft.service.LikeSpec;

public class WhereClauseBuilderDelegate {
    private static final Logger log = LoggerFactory.getLogger(WhereClauseBuilderDelegate.class);

    private PredicateSpecAccumulator accumulator;

    public WhereClauseBuilderDelegate(PredicateSpecAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    public void not() {
        this.accumulator.setIsNot(true);
    }

    public void isNull(String[] paths) {
        CriteriaSpec<Boolean> spec = new IsNullSpec(new ColumnSpec<>(paths));
        accumulator.add(spec);

        accumulator.setIsNot(false);
    }

    public void in(String[] paths, Collection<?> collection) {
        if (collection != null) {
            CriteriaSpec<Boolean> spec = new InSpec<>(new ColumnSpec<>(paths), collection);
            accumulator.add(spec);
        }

        accumulator.setIsNot(false);
    }
    
    public void is(String[] paths, Object value) {
        if (value != null) {
            CriteriaSpec<Boolean> spec = new IsSpec<>(new ColumnSpec<>(paths), value);
            accumulator.add(spec);
        }
        
        accumulator.setIsNot(false);
    }

    public void like(String[] paths, Set<String> queries) {
        if (queries != null) {
            for (String text : queries) {
                if (text != null) {
                    CriteriaSpec<Boolean> spec = new LikeSpec(new ColumnSpec<>(paths), text);
                    accumulator.add(spec);
                }
            }
        }

        accumulator.setIsNot(false);
    }

    public <C extends Comparable<C>> void between(String[] paths, C start, C end) {
        // Note: Null checks for start and end reduces the redundant clause for a true
        // literal. It only sort-of improves the Query performance but its not required.
        if (start != null || end != null) {
            CriteriaSpec<Boolean> spec = new BetweenSpec<>(new ColumnSpec<>(paths), start, end);
            accumulator.add(spec);
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
