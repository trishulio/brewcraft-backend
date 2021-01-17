package io.company.brewcraft.repository;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder {
    static SpecificationBuilder builder() {
        return EnhancedSpecBuilder.builder();
    }

    SpecificationBuilder in(String[] paths, Collection<?> collection);

    SpecificationBuilder in(String path, Collection<?> collection);

    SpecificationBuilder not();

    <C extends Comparable<C>> SpecificationBuilder between(String[] paths, C start, C end);

    <C extends Comparable<C>> SpecificationBuilder between(String path, C start, C end);

    <T> Specification<T> build();
}
