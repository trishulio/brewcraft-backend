package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.repository.DeepRoot;

public class PathAggregation extends BaseModel implements Aggregation {
    private String[] path;
    
    public PathAggregation(String[] path) {
        this.path = path;
    }

    @Override
    public Expression<? extends Number> getExpression(Root<?> root, CriteriaBuilder cb) {
        return new DeepRoot(root).get(path);
    }
}