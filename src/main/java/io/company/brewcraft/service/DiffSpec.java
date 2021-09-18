package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class DiffSpec extends BaseModel implements CriteriaSpec<Number> {
    private CriteriaSpec<Number> pathX, pathY;

    public DiffSpec(CriteriaSpec<Number> pathX, CriteriaSpec<Number> pathY) {
        this.pathX = pathX;
        this.pathY = pathY;
    }

    @Override
    public Expression<Number> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Expression<? extends Number> x = (Expression<? extends Number>) this.pathX.getExpression(root, cq, cb);
        Expression<? extends Number> y = (Expression<? extends Number>) this.pathY.getExpression(root, cq, cb);

        return cb.diff(x, y);
    }
}