package io.company.brewcraft.service;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class InSpec<T> extends BaseModel implements CriteriaSpec<Boolean> {
    private CriteriaSpec<T> spec;
    private Collection<T> collection;

    public InSpec(CriteriaSpec<T> spec, Collection<T> collection) {
        this.spec = spec;
        this.collection = collection;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.spec.getExpression(root, cq, cb).in(this.collection);
    }
}
