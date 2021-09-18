package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class MaxSpec<T extends Number> extends BaseModel implements CriteriaSpec<T> {
    private CriteriaSpec<T> aggr;

    public MaxSpec(CriteriaSpec<T> aggr) {
        this.aggr = aggr;
    }

    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.max(this.aggr.getExpression(root, cq, cb));
    }
}
