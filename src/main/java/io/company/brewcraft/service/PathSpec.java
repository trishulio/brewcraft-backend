package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.repository.DeepRoot;

public class PathSpec<T> extends BaseModel implements CriteriaSpec<T> {
    private String[] paths;
    private String[] joins;

    public PathSpec(String... paths) {
        this(null, paths);
    }

    public PathSpec(String[] joins, String[] paths) {
        this.paths = paths;
        this.joins = joins;
    }

    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return new DeepRoot(root).get(this.joins, this.paths);
    }
}