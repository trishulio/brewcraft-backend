package io.company.brewcraft.repository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

public interface JpaJoiner {
    final JpaJoiner JPA_JOINER = new LocalCachedJpaJoiner(new LocalJpaJoinerCache(), new CriteriaJoinAnnotationJoiner());

    <X, Y> From<X, Y> join(From<X, Y> join, Class<? extends Y> clazz, String fieldName);

    <X, Y> Path<X> get(From<X, Y> join, Class<? extends Y> clazz, String fieldName);
}
