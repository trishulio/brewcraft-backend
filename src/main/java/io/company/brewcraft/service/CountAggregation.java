package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class CountAggregation extends BaseModel implements Aggregation {
    private Aggregation aggr;
    
    public CountAggregation(String... path) {
        this(new PathAggregation(path));
    }
    
    public CountAggregation(Aggregation aggr) {
        this.aggr = aggr;
    }
    
    @Override
    public Expression<? extends Number> getExpression(Root<?> root, CriteriaBuilder cb) {
        return cb.count(this.aggr.getExpression(root, cb));
    }
}
