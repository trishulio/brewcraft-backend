package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class BetweenSpec<C extends Comparable<C>> extends BaseModel implements CriteriaSpec<Boolean> {
    private CriteriaSpec<C> spec;
    private C start;
    private C end;

    public BetweenSpec(CriteriaSpec<C> spec, C start, C end) {
        this.spec = spec;
        this.start = start;
        this.end = end;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Expression<Boolean> expr = null;
        Expression<? extends C> innerExpr = this.spec.getExpression(root, cq, cb);

        if (start != null && end != null) {
            expr = cb.between(innerExpr, start, end);

        } else if (start != null) {
            expr = cb.greaterThanOrEqualTo(innerExpr, start);

        } else if (end != null) {
            expr = cb.lessThanOrEqualTo(innerExpr, end);

        } else {
            // Note: When start and end are both null, a "true" literal is returned,
            // which makes sense but is that a better than throwing an error?
            expr = cb.literal(true);
        }

        return expr;
    }
}
