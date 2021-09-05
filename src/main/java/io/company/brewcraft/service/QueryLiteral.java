package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class QueryLiteral extends BaseModel implements Aggregation {
    private String value;

    public QueryLiteral(String value) {
        this.value = value;
    }

    @Override
    public Expression<?> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.literal(value);
    }
}
