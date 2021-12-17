package io.company.brewcraft.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;

import com.google.common.collect.Lists;

import io.company.brewcraft.service.GroupByClauseBuilder;
import io.company.brewcraft.service.SelectClauseBuilder;

public class QueryResolver {
    private static Logger log = LoggerFactory.getLogger(AggregationRepository.class);

    private EntityManager em;

    public QueryResolver(EntityManager em) {
        this.em = em;
    }

    public <R, T> TypedQuery<R> buildQuery(Class<T> entityClz, Class<R> returnClz, SelectClauseBuilder selector, GroupByClauseBuilder grouper, Specification<T> spec, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<R> cq = cb.createQuery(returnClz);

        Root<T> root = cq.from(entityClz);

        cq.where(spec.toPredicate(root, cq, cb));

        List<Expression<?>> groupByAttrs;
        if (grouper != null) {
            groupByAttrs = grouper.getGroupByClause(root, cq, cb);
        } else {
            groupByAttrs = new ArrayList<>(0);
        }

        List<Selection<?>> selectAttrs = Lists.newArrayList(groupByAttrs);

        if (selector != null) {
            List<Selection<?>> additionalSelectAttrs = selector.getSelectClause(root, cq, cb);
            selectAttrs.addAll(additionalSelectAttrs);
        }

        cq.multiselect(selectAttrs);
        cq.groupBy(groupByAttrs);

        if (pageable != null) {
            List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, cb);
            cq.orderBy(orders);
        }

        TypedQuery<R> q = em.createQuery(cq);

        if (pageable != null) {
            q.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return q;
    }
}