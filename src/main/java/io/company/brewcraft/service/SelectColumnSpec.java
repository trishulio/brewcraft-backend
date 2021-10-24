package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.repository.RootUtil;

public class SelectColumnSpec<T> extends BaseModel implements CriteriaSpec<T> {
    private static final Logger log = LoggerFactory.getLogger(SelectColumnSpec.class);

    private RootUtil rootUtil;

    private String[] paths;
    private String[] joins;

    protected SelectColumnSpec(RootUtil rootUtil, String[] joins, String[] paths) {
        this.rootUtil = rootUtil;
        this.joins = joins;
        this.paths = paths;
    }

    public SelectColumnSpec(String[] joins, String[] paths) {
        this(RootUtil.INSTANCE, joins, paths);
    }

    public SelectColumnSpec(String[] paths) {
        this(null, paths);
    }

    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.rootUtil.getPathWithJoin(root, joins, paths);
    }
}