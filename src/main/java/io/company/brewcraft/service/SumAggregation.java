package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class SumAggregation extends BaseModel implements Aggregation {
    private Aggregation path;
    
    public SumAggregation(PathProvider provider) {
        this(provider.getPath());
    }
    
    public SumAggregation(String... path) {
        this(new PathAggregation(path));
    }
    
    public SumAggregation(Aggregation path) {
        this.path = path;
    }

    @Override
    public Expression<? extends Number> getExpression(Root<?> root, CriteriaBuilder cb) {
        Expression<? extends Number> x = this.path.getExpression(root, cb);
        
        return cb.sum(x);
    }   
}
