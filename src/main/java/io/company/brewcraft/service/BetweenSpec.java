package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class BetweenSpec<C extends Comparable<C>> extends BaseModel implements CriteriaSpec<Boolean> {
    private CriteriaSpec<C> aggr;
    private C start;
    private C end;

    public BetweenSpec(CriteriaSpec<C> aggr, C start, C end) {
        this.aggr = aggr;
        this.start = start;
        this.end = end;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Expression<Boolean> expr = null;
        if (start != null && end != null) {
            expr = cb.between(this.aggr.getExpression(root, cq, cb), start, end);
        } else if (start != null) {
            expr = cb.greaterThanOrEqualTo(this.aggr.getExpression(root, cq, cb), start);
        } else if (end != null) {
            expr = cb.lessThanOrEqualTo(this.aggr.getExpression(root, cq, cb), end);
        }
        // TODO: Never return a null expression

        return expr;
    }
}
