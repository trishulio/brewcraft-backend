package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class RootAggregation extends BaseModel implements Aggregation {
    @Override
    public Expression<?> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return root;
    }
}
