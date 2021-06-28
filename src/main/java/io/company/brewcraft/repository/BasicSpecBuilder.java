package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.Aggregation;

public class BasicSpecBuilder implements SpecificationBuilder {
    private static final Logger log = LoggerFactory.getLogger(BasicSpecBuilder.class);

    private SpecAccumulator accumulator;

    public BasicSpecBuilder() {
        this(new SpecAccumulator());
    }

    protected BasicSpecBuilder(SpecAccumulator accumulator) {
        this.accumulator = accumulator;
    }
    
    @Override
    public BasicSpecBuilder isNull(String[] paths) {
        Aggregation aggr = (root, query, criteriaBuilder) -> criteriaBuilder.isNull(new DeepRoot(root).get(paths));
        accumulator.add(aggr);

        accumulator.setIsNot(false);
        return this;
    }

    @Override
    public SpecificationBuilder isNull(String path) {
        return isNull(new String[] { path });
    }

    @Override
    public BasicSpecBuilder in(String[] paths, Collection<?> collection) {
        if (collection != null) {
            Aggregation aggr = (root, query, criteriaBuilder) -> new DeepRoot(root).get(paths).in(collection);
            accumulator.add(aggr);
        }

        accumulator.setIsNot(false);
        return this;
    }

    @Override
    public SpecificationBuilder in(String path, Collection<?> collection) {
        return in(new String[] { path }, collection);
    }

    @Override
    public BasicSpecBuilder not() {
        accumulator.setIsNot(true);
        return this;
    }

    @Override
    public <C extends Comparable<C>> BasicSpecBuilder between(String[] paths, C start, C end) {
        Aggregation aggr = null;
        if (start != null && end != null) {
            aggr = (root, query, criteriaBuilder) -> criteriaBuilder.between(new DeepRoot(root).get(paths), start, end);
        } else if (start != null) {
            aggr = (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(new DeepRoot(root).get(paths), start);
        } else if (end != null) {
            aggr = (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(new DeepRoot(root).get(paths), end);
        }

        if (aggr != null) {
            accumulator.add(aggr);
        }

        accumulator.setIsNot(false);
        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String path, C start, C end) {
        return between(new String[] { path }, start, end);
    }

    @Override
    public <T> Specification<T> build() {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = this.accumulator.getPredicates(root, query, criteriaBuilder);
            return criteriaBuilder.and(predicates);
        };
    }

    @Override
    public SpecificationBuilder like(String[] paths, Set<String> queries) {
        if (queries != null) {
            for (String text : queries) {
                if (text != null) {
                    Aggregation aggr = (root, query, criteriaBuilder) -> criteriaBuilder.like(new DeepRoot(root).get(paths), String.format("%%%s%%", text));
                    accumulator.add(aggr);
                }
            }
        }

        return this;
    }

    @Override
    public SpecificationBuilder like(String path, Set<String> queries) {
        return like(new String[] { path }, queries);
    }
}
