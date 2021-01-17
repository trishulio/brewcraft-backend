package io.company.brewcraft.repository;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

public class EnhancedSpecBuilder implements SpecificationBuilder {

    private SpecificationBuilder delegate;

    public EnhancedSpecBuilder(SpecificationBuilder delegate) {
        this.delegate = delegate;
    }

    @Override
    public SpecificationBuilder in(String[] paths, Collection<?> collection) {
        if (collection != null) {
            this.delegate = this.delegate.in(paths, collection);
        }

        return this;
    }

    @Override
    public SpecificationBuilder in(String path, Collection<?> collection) {
        if (collection != null) {
            this.delegate = this.delegate.in(path, collection);
        }

        return this;
    }

    @Override
    public SpecificationBuilder not() {
        this.delegate = this.delegate.not();
        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String[] paths, C start, C end) {
        if (start != null && end != null) {
            this.delegate = this.delegate.between(paths, start, end);
        }

        return this;
    }

    @Override
    public <C extends Comparable<C>> SpecificationBuilder between(String path, C start, C end) {
        if (start != null && end != null) {
            this.delegate = this.delegate.between(path, start, end);
        }

        return this;
    }

    @Override
    public <T> Specification<T> build() {
        return this.delegate.build();
    }

    public static SpecificationBuilder builder() {
        return new EnhancedSpecBuilder(new BasicSpecBuilder());
    }
}
