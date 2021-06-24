package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class DiffAggregation extends BaseModel implements Aggregation {
    private Aggregation pathX, pathY;
    
    public DiffAggregation(Aggregation pathX, Aggregation pathY) {
        this.pathX = pathX;
        this.pathY = pathY;
    }

    @Override
    public Expression<? extends Number> getExpression(Root<?> root, CriteriaBuilder cb) {
        Expression<? extends Number> x = this.pathX.getExpression(root, cb);
        Expression<? extends Number> y = this.pathY.getExpression(root, cb);
        
        return cb.diff(x, y);
    }   
}