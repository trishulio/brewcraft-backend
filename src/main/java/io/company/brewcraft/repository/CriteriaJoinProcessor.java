package io.company.brewcraft.repository;

import javax.persistence.criteria.From;

public interface CriteriaJoinProcessor {
    final CriteriaJoinProcessor CRITERIA_JOINER = new LocalCachedCriteriaJoinProcessor(new CriteriaJoinAnnotationProcessor());

    <X, Y> From<X, Y> apply(From<X, Y> join, Class<? extends Y> clazz, String fieldName);
}
