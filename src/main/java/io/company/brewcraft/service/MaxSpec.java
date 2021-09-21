package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class MaxSpec<T extends Number> extends BaseModel implements CriteriaSpec<T> {
    private CriteriaSpec<T> spec;

    public MaxSpec(CriteriaSpec<T> spec) {
        this.spec = spec;
    }

    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.max(this.spec.getExpression(root, cq, cb));
    }
}
