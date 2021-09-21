package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.company.brewcraft.model.BaseModel;

public class LikeSpec extends BaseModel implements CriteriaSpec<Boolean> {

    private CriteriaSpec<String> spec;
    private String text;

    public LikeSpec(CriteriaSpec<String> spec, String text) {
        this.spec = spec;
        this.text = text;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.like(cb.lower(this.spec.getExpression(root, cq, cb)), String.format("%%%s%%", text.toLowerCase()));
    }
}
