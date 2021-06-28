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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.Selector;

public class AggregationRepository {
    private static Logger log = LoggerFactory.getLogger(AggregationRepository.class);

    private EntityManager em;
    
    public AggregationRepository(EntityManager em) {
        this.em = em;
    }
        
    public <R> Page<R> getAggregation(Class<R> clazz, Selector selection, Selector groupBy, Specification<R> spec, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<R> q = cb.createQuery(clazz);

        Root<R> root = q.from(clazz);

        q.where(spec.toPredicate(root, q, cb));

        List<Selection<?>> selectAttrs = selection.getSelection(root, q, cb);
        q.multiselect(selectAttrs);
        
        if (groupBy != null) {
            @SuppressWarnings("unchecked")
            List<Expression<?>> groupByAttrs = (List<Expression<?>>) (List<? extends Selection<?>>) groupBy.getSelection(root, q, cb);
            q.groupBy(groupByAttrs);
        }
        
        TypedQuery<R> tq = em.createQuery(q);

        if (pageable != null) {            
            tq.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }
        
        return new PageImpl<R>(tq.getResultList());
    }
}
