package io.company.brewcraft.repository;

import javax.persistence.criteria.From;

public interface JpaJoiner {
    final JpaJoiner JPA_JOINER = new LocalCachedJpaJoiner(new CriteriaJoinAnnotationJoiner());

    <X, Y> From<X, Y> apply(From<X, Y> join, Class<? extends Y> clazz, String fieldName);
}
