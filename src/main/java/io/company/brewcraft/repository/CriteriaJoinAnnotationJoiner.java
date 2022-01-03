package io.company.brewcraft.repository;

import java.lang.reflect.Field;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.CriteriaJoin;

public class CriteriaJoinAnnotationJoiner implements JpaJoiner {
    private static final Logger log = LoggerFactory.getLogger(CriteriaJoinAnnotationJoiner.class);

    @Override
    public <X, Y> From<X, Y> apply(From<X, Y> join, Class<? extends Y> clazz, String fieldName) {
        Field field = FieldUtils.getAllFieldsList(clazz).stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElseThrow();
        CriteriaJoin cj = field.getAnnotation(CriteriaJoin.class);
        JoinType jt = JoinType.INNER;
        if (cj != null && cj.type() != null) {
            jt = cj.type();
        }

        return join.join(fieldName, jt);
    }
}
