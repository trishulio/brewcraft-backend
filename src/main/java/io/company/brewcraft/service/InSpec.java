package io.company.brewcraft.service;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class InSpec<T> extends BaseModel implements CriteriaSpec<Boolean> {
    private CriteriaSpec<T> aggr;
    private Collection<T> collection;

    public InSpec(CriteriaSpec<T> aggr, Collection<T> collection) {
        this.aggr = aggr;
        this.collection = collection;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.aggr.getExpression(root, cq, cb).in(this.collection);
    }
}
