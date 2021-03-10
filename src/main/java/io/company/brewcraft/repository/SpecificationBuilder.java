package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder {
    static SpecificationBuilder builder() {
        return new BasicSpecBuilder();
    }
    
    SpecificationBuilder isNull(String path);

    SpecificationBuilder isNull(String[] paths);

    SpecificationBuilder in(String[] paths, Collection<?> collection);

    SpecificationBuilder in(String path, Collection<?> collection);
    
    SpecificationBuilder not();

    SpecificationBuilder like(String[] paths, Set<String> queries);

    SpecificationBuilder like(String path, Set<String> queries);

    <C extends Comparable<C>> SpecificationBuilder between(String[] paths, C start, C end);

    <C extends Comparable<C>> SpecificationBuilder between(String path, C start, C end);

    <T> Specification<T> build();
}
