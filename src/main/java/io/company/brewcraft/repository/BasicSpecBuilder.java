package io.company.brewcraft.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    // TODO: Can the Trifunction interface be replaced with Specification interface?
    private static final Logger log = LoggerFactory.getLogger(BasicSpecBuilder.class);

    private List<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> funcs;
    private boolean isNot;

    public BasicSpecBuilder() {
        this.funcs = new ArrayList<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>>();
        this.isNot = false;
    }

    @Override
    public BasicSpecBuilder in(String[] paths, Collection<?> collection) {
        TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> func = (root, query, criteriaBuilder) -> get(root, paths).in(collection);
        add(func);
        return this;
    }

    @Override
    public SpecificationBuilder in(String path, Collection<?> collection) {
        return in(new String[] { path }, collection);
    }

    @Override
    public BasicSpecBuilder not() {
        this.isNot = true;
        return this;
    }

    @Override
    public <C extends Comparable<C>> BasicSpecBuilder between(String[] paths, C start, C end) {
        if (start != null && end != null) {
            TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> func = (root, query, criteriaBuilder) -> criteriaBuilder.between(get(root, paths), start, end);
            add(func);
        }

        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String path, C start, C end) {
        return between(new String[] { path }, start, end);
    }

    @Override
    public <T> Specification<T> build() {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = new Predicate[this.funcs.size()];
            predicates = this.funcs.stream().map(func -> func.apply(root, query, criteriaBuilder)).collect(Collectors.toList()).toArray(predicates);

            return criteriaBuilder.and(predicates);
        };
    }

    private void add(TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> func) {
        if (this.isNot) {
            TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> orig = func;

            func = (root, query, criteriaBuilder) -> criteriaBuilder.not(criteriaBuilder.and(orig.apply(root, query, criteriaBuilder)));
            this.isNot = false;
        }

        final TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> ref = func;
        TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder> combined = (root, query, criteriaBuilder) -> criteriaBuilder.and(ref.apply(root, query, criteriaBuilder));
        this.funcs.add(combined);
    }

    private <C> Path<C> get(Root<?> root, String[] fieldNames) {
        if (fieldNames.length <= 0) {
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
