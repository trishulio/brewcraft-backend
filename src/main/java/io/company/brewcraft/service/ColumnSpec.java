package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.repository.RootUtil;

public class ColumnSpec<T> extends BaseModel implements CriteriaSpec<T> {
    private static final Logger log = LoggerFactory.getLogger(ColumnSpec.class);

    private RootUtil rootUtil;

    private String[] paths;

    protected ColumnSpec(RootUtil rootUtil, String[] paths) {
        this.rootUtil = rootUtil;
        this.paths = paths;
    }

    public ColumnSpec(String[] paths) {
        this(RootUtil.INSTANCE, paths);
    }

    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.rootUtil.getPath(root, paths);
    }
}