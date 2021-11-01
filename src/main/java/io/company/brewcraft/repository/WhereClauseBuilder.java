package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

public interface WhereClauseBuilder {
    static WhereClauseBuilder builder() {
        return new WhereClauseBuilderWrapper();
    }

    WhereClauseBuilder isNull(String join, String path);

    WhereClauseBuilder isNull(String join, String[] paths);

    WhereClauseBuilder isNull(String[] joins, String path);

    WhereClauseBuilder isNull(String[] joins, String[] paths);

    WhereClauseBuilder isNull(String path);

    WhereClauseBuilder isNull(String[] paths);

    WhereClauseBuilder in(String[] paths, Collection<?> collection);

    WhereClauseBuilder in(String path, Collection<?> collection);

    WhereClauseBuilder in(String[] joins, String[] paths, Collection<?> collection);

    WhereClauseBuilder in(String join, String path, Collection<?> collection);

    WhereClauseBuilder in(String join, String[] paths, Collection<?> collection);

    WhereClauseBuilder in(String[] joins, String path, Collection<?> collection);

    WhereClauseBuilder not();

    WhereClauseBuilder like(String[] paths, Set<String> queries);

    WhereClauseBuilder like(String path, Set<String> queries);

    WhereClauseBuilder like(String[] joins, String[] paths, Set<String> queries);

    WhereClauseBuilder like(String join, String path, Set<String> queries);

    WhereClauseBuilder like(String join, String[] paths, Set<String> queries);

    WhereClauseBuilder like(String joins[], String path, Set<String> queries);

    <C extends Comparable<C>> WhereClauseBuilder between(String[] paths, C start, C end);

    <C extends Comparable<C>> WhereClauseBuilder between(String path, C start, C end);

    <C extends Comparable<C>> WhereClauseBuilder between(String[] joins, String path, C start, C end);

    <C extends Comparable<C>> WhereClauseBuilder between(String join, String[] paths, C start, C end);

    <C extends Comparable<C>> WhereClauseBuilder between(String[] joins, String[] paths, C start, C end);

    <C extends Comparable<C>> WhereClauseBuilder between(String join, String path, C start, C end);

    <T> Specification<T> build();
}
