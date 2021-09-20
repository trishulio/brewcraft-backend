package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class IsNullSpec extends BaseModel implements CriteriaSpec<Boolean> {
    private CriteriaSpec<?> aggr;

    public IsNullSpec(CriteriaSpec<?> aggr) {
        this.aggr = aggr;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.isNull(this.aggr.getExpression(root, cq, cb));
    }
}
