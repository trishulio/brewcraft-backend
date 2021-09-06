package io.company.brewcraft.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.Selector;

public class QueryResolver {
    private static Logger log = LoggerFactory.getLogger(AggregationRepository.class);

    private EntityManager em;

    public QueryResolver(EntityManager em) {
        this.em = em;
    }

    public <R, T> TypedQuery<R> buildQuery(Class<T> entityClz, Class<R> returnClz, Selector selection, Selector groupBy, Specification<T> spec, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<R> cq = cb.createQuery(returnClz);

        Root<T> root = cq.from(entityClz);

        cq.where(spec.toPredicate(root, cq, cb));

        List<Selection<?>> selectAttrs = selection.getSelection(root, cq, cb);
        cq.multiselect(selectAttrs);

        if (groupBy != null) {
            @SuppressWarnings("unchecked")
            List<Expression<?>> groupByAttrs = (List<Expression<?>>) (List<? extends Selection<?>>) groupBy.getSelection(root, cq, cb);
            cq.groupBy(groupByAttrs);
        }

        TypedQuery<R> q = em.createQuery(cq);

        if (pageable != null) {
            q.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return q;
    }
}