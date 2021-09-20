package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

@SuppressWarnings("unchecked")
public class RootSpec<T> extends BaseModel implements CriteriaSpec<T> {
    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return (Expression<T>) root;
    }
}
