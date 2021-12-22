package io.company.brewcraft.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.repository.ColumnSpecAccumulator;

public class SelectClauseBuilder extends BaseModel {
    private ColumnSpecAccumulator accumulator;

    public SelectClauseBuilder() {
        this(new ColumnSpecAccumulator());
    }

    protected SelectClauseBuilder(ColumnSpecAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    public SelectClauseBuilder select(PathProvider provider) {
        if (provider != null) {
            select(provider.getPath());
        }

        return this;
    }

    public SelectClauseBuilder select(String... paths) {
        if (paths != null) {
            select(new ColumnSpec<>(paths));
        }

        return this;
    }

    public SelectClauseBuilder select(CriteriaSpec<?> spec) {
        if (spec != null) {
            this.accumulator.add(spec);
        }

        return this;
    }

    public List<Selection<?>> getSelectClause(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.accumulator.getColumns(root, cq, cb);
    }
}
