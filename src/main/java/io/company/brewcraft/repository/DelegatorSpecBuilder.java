package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;


public class DelegatorSpecBuilder implements SpecificationBuilder {
    private static final Logger log = LoggerFactory.getLogger(DelegatorSpecBuilder.class);

    private CriteriaSpecBuilder delegate;

    public DelegatorSpecBuilder() {
        this(new CriteriaSpecBuilder(new SpecAccumulator()));
    }

    protected DelegatorSpecBuilder(CriteriaSpecBuilder delegate) {
        this.delegate = delegate;
    }

    @Override
    public SpecificationBuilder isNull(String join, String path) {
        this.delegate.isNull(new String[] { join }, new String[] { path });
        return this;
    }

    @Override
    public SpecificationBuilder isNull(String join, String[] paths) {
        this.delegate.isNull(new String[] { join }, paths);
        return this;
    }

    @Override
    public SpecificationBuilder isNull(String[] joins, String path) {
        this.delegate.isNull(joins, new String[] { path });
        return this;
    }

    @Override
    public SpecificationBuilder isNull(String[] joins, String[] paths) {
        this.delegate.isNull(joins, paths);
        return this;
    }

    @Override
    public SpecificationBuilder isNull(String path) {
        this.delegate.isNull(null, new String[] { path });
        return this;
    }

    @Override
    public SpecificationBuilder isNull(String[] paths) {
        return isNull((String[]) null, paths);
    }

    @Override
    public SpecificationBuilder in(String[] paths, Collection<?> collection) {
        this.delegate.in((String[]) null, paths, collection);
        return this;
    }

    @Override
    public SpecificationBuilder in(String path, Collection<?> collection) {
        this.delegate.in(null, new String[] { path }, collection);
        return this;
    }

    @Override
    public SpecificationBuilder in(String[] joins, String[] paths, Collection<?> collection) {
        this.delegate.in(joins, paths, collection);
        return this;
    }

    @Override
    public SpecificationBuilder in(String join, String path, Collection<?> collection) {
        this.delegate.in(new String[] { join }, new String[] { path }, collection);
        return this;
    }

    @Override
    public SpecificationBuilder in(String join, String[] paths, Collection<?> collection) {
        this.delegate.in(new String[] { join }, paths, collection);
        return this;
    }

    @Override
    public SpecificationBuilder in(String[] joins, String path, Collection<?> collection) {
        this.delegate.in(joins, new String[] { path }, collection);
        return this;
    }

    @Override
    public SpecificationBuilder not() {
        this.delegate.not();
        return this;
    }

    @Override
    public SpecificationBuilder like(String[] paths, Set<String> queries) {
        this.delegate.like(null, paths, queries);
        return this;
    }

    @Override
    public SpecificationBuilder like(String path, Set<String> queries) {
        this.delegate.like(null, new String[] { path }, queries);
        return this;
    }

    @Override
    public SpecificationBuilder like(String[] joins, String[] paths, Set<String> queries) {
        this.delegate.like(joins, paths, queries);
        return this;
    }

    @Override
    public SpecificationBuilder like(String join, String path, Set<String> queries) {
        this.delegate.like(new String[] { join }, new String[] { path }, queries);
        return this;
    }

    @Override
    public SpecificationBuilder like(String join, String[] paths, Set<String> queries) {
        this.delegate.like(new String[] { join }, paths, queries);
        return this;
    }

    @Override
    public SpecificationBuilder like(String[] joins, String path, Set<String> queries) {
        this.delegate.like(joins, new String[] { path }, queries);
        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String[] paths, C start, C end) {
        this.delegate.between(null, paths, start, end);
        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String path, C start, C end) {
        this.delegate.between(null, new String[] { path }, start, end);
        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String[] joins, String path, C start, C end) {
        this.delegate.between(joins, new String[] { path }, start, end);
        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String join, String[] paths, C start, C end) {
        this.delegate.between(new String[] { join }, paths, start, end);
        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String[] joins, String[] paths, C start, C end) {
        this.delegate.between(joins, paths, start, end);
        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String join, String path, C start, C end) {
        this.delegate.between(new String[] { join }, new String[] { path }, start, end);
        return this;
    }

    @Override
    public <T> Specification<T> build() {
        return this.delegate.build();
    }
}
