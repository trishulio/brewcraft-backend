package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class CountSpec<T> extends BaseModel implements CriteriaSpec<Long> {
    private CriteriaSpec<T> aggr;

    public CountSpec(CriteriaSpec<T> aggr) {
        this.aggr = aggr;
    }

    @Override
    public Expression<Long> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.count(this.aggr.getExpression(root, cq, cb));
    }
}
