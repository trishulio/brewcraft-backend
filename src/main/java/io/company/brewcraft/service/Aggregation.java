package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public interface Aggregation {
    Expression<?> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb);
}
