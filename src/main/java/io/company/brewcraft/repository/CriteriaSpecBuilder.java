package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.BetweenSpec;
import io.company.brewcraft.service.InSpec;
import io.company.brewcraft.service.IsNullSpec;
import io.company.brewcraft.service.LikeSpec;
import io.company.brewcraft.service.PathSpec;

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
        CriteriaSpec<Boolean> aggr = new IsNullSpec(new PathSpec<>(joins, paths));
        accumulator.add(aggr);

        accumulator.setIsNot(false);
    }

    public void in(String[] joins, String[] paths, Collection<?> collection) {
        if (collection != null) {
            CriteriaSpec<Boolean> aggr = new InSpec<>(new PathSpec<>(joins, paths), collection);
            accumulator.add(aggr);
        }

        accumulator.setIsNot(false);
    }

    public void like(String[] joins, String[] paths, Set<String> queries) {
        if (queries != null) {
            for (String text : queries) {
                if (text != null) {
                    CriteriaSpec<Boolean> aggr = new LikeSpec(new PathSpec<>(joins, paths), text);
                    accumulator.add(aggr);
                }
            }
        }

        accumulator.setIsNot(false);
    }

    public <C extends Comparable<C>> void between(String[] joins, String[] paths, C start, C end) {
        if (start != null || end != null) {
            CriteriaSpec<Boolean> aggr = new BetweenSpec<>(new PathSpec<>(joins, paths), start, end);
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
