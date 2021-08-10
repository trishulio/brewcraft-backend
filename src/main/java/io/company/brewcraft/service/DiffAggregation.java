package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

@SuppressWarnings("unchecked")
public class DiffAggregation extends BaseModel implements Aggregation {
    private Aggregation pathX, pathY;

    public DiffAggregation(PathProvider pathX, PathProvider pathY) {
        this(pathX.getPath(), pathY.getPath());
    }

    public DiffAggregation(String[] pathX, String[] pathY) {
        this(new PathAggregation(pathX), new PathAggregation(pathY));
    }

    public DiffAggregation(Aggregation pathX, Aggregation pathY) {
        this.pathX = pathX;
        this.pathY = pathY;
    }

    @Override
    public Expression<? extends Number> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Expression<? extends Number> x = (Expression<? extends Number>) this.pathX.getExpression(root, cq, cb);
        Expression<? extends Number> y = (Expression<? extends Number>) this.pathY.getExpression(root, cq, cb);

        return cb.diff(x, y);
    }
}