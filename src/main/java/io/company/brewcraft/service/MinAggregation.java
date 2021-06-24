package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class MinAggregation extends BaseModel implements Aggregation {

    private Aggregation aggr;

    public MinAggregation(String... path) {
        this(new PathAggregation(path));
    }

    public MinAggregation(Aggregation aggr) {
        this.aggr = aggr;
    }

    @Override
    public Expression<? extends Number> getExpression(Root<?> root, CriteriaBuilder cb) {
        return cb.max(this.aggr.getExpression(root, cb));
    }
}
