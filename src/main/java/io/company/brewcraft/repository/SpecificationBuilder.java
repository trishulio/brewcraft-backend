package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder {
    static SpecificationBuilder builder() {
        return new DelegatorSpecBuilder();
    }

    SpecificationBuilder isNull(String join, String path);

    SpecificationBuilder isNull(String join, String[] paths);

    SpecificationBuilder isNull(String[] joins, String path);

    SpecificationBuilder isNull(String[] joins, String[] paths);

    SpecificationBuilder isNull(String path);

    SpecificationBuilder isNull(String[] paths);

    SpecificationBuilder in(String[] paths, Collection<?> collection);

    SpecificationBuilder in(String path, Collection<?> collection);

    SpecificationBuilder in(String[] joins, String[] paths, Collection<?> collection);

    SpecificationBuilder in(String join, String path, Collection<?> collection);

    SpecificationBuilder in(String join, String[] paths, Collection<?> collection);

    SpecificationBuilder in(String[] joins, String path, Collection<?> collection);

    SpecificationBuilder not();

    SpecificationBuilder like(String[] paths, Set<String> queries);

    SpecificationBuilder like(String path, Set<String> queries);

    SpecificationBuilder like(String[] joins, String[] paths, Set<String> queries);

    SpecificationBuilder like(String join, String path, Set<String> queries);

    SpecificationBuilder like(String join, String[] paths, Set<String> queries);

    SpecificationBuilder like(String joins[], String path, Set<String> queries);

    <C extends Comparable<C>> SpecificationBuilder between(String[] paths, C start, C end);

    <C extends Comparable<C>> SpecificationBuilder between(String path, C start, C end);

    <C extends Comparable<C>> SpecificationBuilder between(String[] joins, String path, C start, C end);

    <C extends Comparable<C>> SpecificationBuilder between(String join, String[] paths, C start, C end);

    <C extends Comparable<C>> SpecificationBuilder between(String[] joins, String[] paths, C start, C end);

    <C extends Comparable<C>> SpecificationBuilder between(String join, String path, C start, C end);

    <T> Specification<T> build();
}
