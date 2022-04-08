package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class IsSpec<T> extends BaseModel implements CriteriaSpec<Boolean> {
    private CriteriaSpec<T> spec;
    private Object value;

    public IsSpec(CriteriaSpec<T> spec, Object value) {
        this.spec = spec;
        this.value = value;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.equal(this.spec.getExpression(root, cq, cb), value);
    }
}
