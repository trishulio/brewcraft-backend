package io.company.brewcraft.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public interface Aggregation {
    Expression<? extends Number> getExpression(Root<?> root, CriteriaBuilder cb);
}
