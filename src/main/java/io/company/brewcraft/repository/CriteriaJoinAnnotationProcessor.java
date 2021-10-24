package io.company.brewcraft.repository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import io.company.brewcraft.service.CriteriaJoin;

public class CriteriaJoinAnnotationProcessor implements CriteriaJoinProcessor {
    private static final Logger log = LoggerFactory.getLogger(CriteriaJoinAnnotationProcessor.class);

    public static Set<Class<?>> nonBasicEntityQualifiers = ImmutableSet.of(JoinColumn.class, ManyToMany.class, ManyToOne.class, OneToMany.class, Embedded.class);

    @Override
    public <X, Y> From<X, Y> apply(From<X, Y> join, Class<? extends Y> clazz, String fieldName) {
        Field field = FieldUtils.getAllFieldsList(clazz).stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElseThrow();
        CriteriaJoin cj = field.getAnnotation(CriteriaJoin.class);
        if (isNotBasicEntity(field)) {
            JoinType jt = CriteriaJoin.DEFAULT_JOIN_TYPE;
            if (cj != null) {
                jt = cj.type();
            }
            join = join.join(fieldName, jt);

        } else if (cj != null) {
            String msg = String.format("Cannot apply CriteriaJoin on a Basic entity named: %s, of type: %s, in class: %s", fieldName, field.getClass().getName(), clazz.getName());
            log.error(msg);
            throw new IllegalAccessError(msg);

        } else {
            log.info("Neither a compound entity, nor annotation was present, skipping from creating a join.");
        }

        return join;
    }

    private boolean isNotBasicEntity(Field field) {
        boolean isNotBasicEntity = false;
        Annotation[] annotations = field.getAnnotations();
        for (int i = 0; i < annotations.length && !isNotBasicEntity; i++) {
            Class<?> type = annotations[i].annotationType();
            isNotBasicEntity = nonBasicEntityQualifiers.contains(type);
        }

        return isNotBasicEntity;
    }
}
