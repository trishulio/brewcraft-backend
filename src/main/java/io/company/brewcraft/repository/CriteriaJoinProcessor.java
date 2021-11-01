package io.company.brewcraft.repository;

import javax.persistence.criteria.From;

public interface CriteriaJoinProcessor {
    final CriteriaJoinProcessor ANNOTATION_PROCESSOR = new CriteriaJoinAnnotationProcessor();
    final CriteriaJoinProcessor JOIN_IGNORER = new CriteriaJoinIgnorer();

    <X, Y> From<X, Y> apply(From<X, Y> join, Class<? extends Y> clazz, String fieldName);
}
