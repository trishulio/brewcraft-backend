package io.company.brewcraft.repository;

import javax.persistence.criteria.From;

public class CriteriaJoinIgnorer implements CriteriaJoinProcessor {
    @Override
    public <X, Y> From<X, Y> apply(From<X, Y> join, Class<? extends Y> clazz, String fieldName) {
        return join;
    }
}
