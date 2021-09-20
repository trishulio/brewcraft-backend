package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class AverageSpec<T extends Number> extends BaseModel implements CriteriaSpec<Double> {
    private CriteriaSpec<T> aggr;

    public AverageSpec(CriteriaSpec<T> aggr) {
        this.aggr = aggr;
    }

    @Override
    public Expression<Double> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.avg(this.aggr.getExpression(root, cq, cb));
    }

}
