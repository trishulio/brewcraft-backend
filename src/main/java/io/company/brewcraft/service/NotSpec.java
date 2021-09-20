package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class NotSpec extends BaseModel implements CriteriaSpec<Boolean> {
    private CriteriaSpec<Boolean> spec;

    public NotSpec(CriteriaSpec<Boolean> spec) {
        this.spec = spec;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.not(this.spec.getExpression(root, cq, cb));
    }
}
