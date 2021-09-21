package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class MinSpec<T extends Number> extends BaseModel implements CriteriaSpec<T> {
    private CriteriaSpec<T> spec;

    public MinSpec(CriteriaSpec<T> spec) {
        this.spec = spec;
    }

    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.min(this.spec.getExpression(root, cq, cb));
    }
}
