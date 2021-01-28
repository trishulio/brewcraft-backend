package io.company.brewcraft.repository;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.data.TriFunction;

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
    public BasicSpecBuilder in(String[] paths, Collection<?> collection) {
        if (collection != null) {
            TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> func = (root, query, criteriaBuilder) -> get(root, paths).in(collection);
            accumulator.add(func);
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
        TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> func = null;
        if (start != null && end != null) {
            func = (root, query, criteriaBuilder) -> criteriaBuilder.between(get(root, paths), start, end);
        } else if (start != null) {
            func = (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(get(root, paths), start);
        } else if (end != null) {
            func = (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(get(root, paths), end);
        }

        if (func != null) {
            accumulator.add(func);
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

    private <C> Path<C> get(Root<?> root, String[] fieldNames) {
        if (fieldNames == null || fieldNames.length <= 0) {
            String msg = String.format("No field names provided: %s", fieldNames.toString());
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        Path<C> path = root.get(fieldNames[0]);

        for (int i = 1; i < fieldNames.length; i++) {
            path = path.get(fieldNames[i]);
        }

        return path;
    }
}
