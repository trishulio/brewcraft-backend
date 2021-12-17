package io.company.brewcraft.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.repository.ColumnSpecAccumulator;

public class GroupByClauseBuilder extends BaseModel {
    private ColumnSpecAccumulator accumulator;

    public GroupByClauseBuilder() {
        this(new ColumnSpecAccumulator());
    }

    protected GroupByClauseBuilder(ColumnSpecAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    public GroupByClauseBuilder groupBy(PathProvider provider) {
        if (provider != null) {
            groupBy(provider.getPath());
        }

        return this;
    }

    public GroupByClauseBuilder groupBy(String... paths) {
        if (paths != null) {
            groupBy(new SelectColumnSpec<>(paths));
        }

        return this;
    }

    public GroupByClauseBuilder groupBy(CriteriaSpec<?> spec) {
        if (spec != null) {
            this.accumulator.add(spec);
        }

        return this;
    }

    public List<Expression<?>> getGroupByClause(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.accumulator.getColumns(root, cq, cb);
    }
}
