package io.company.brewcraft.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.Selector;

public class AggregationRepository {
    private static Logger log = LoggerFactory.getLogger(AggregationRepository.class);

    private QueryResolver qResolver;

    public AggregationRepository(QueryResolver qResolver) {
        this.qResolver = qResolver;
    }

    public <T> List<T> getAggregation(Class<T> entityClz, Selector selection, Selector groupBy, Specification<T> spec, Pageable pageable) {
        TypedQuery<T> tq = qResolver.buildQuery(entityClz, entityClz, selection, groupBy, spec, pageable);

        return tq.getResultList();
    }

    public <R, T> List<R> getAggregation(Class<T> entityClz, Class<R> returnClz, Selector selection, Selector groupBy, Specification<T> spec, Pageable pageable) {
        TypedQuery<R> tq = qResolver.buildQuery(entityClz, returnClz, selection, groupBy, spec, pageable);

        return tq.getResultList();
    }

    public <R, T> R getSingleAggregation(Class<T> entityClz, Class<R> returnClz, Selector selection, Selector groupBy, Specification<T> spec, Pageable pageable) {
        TypedQuery<R> tq = qResolver.buildQuery(entityClz, returnClz, selection, groupBy, spec, pageable);

        return tq.getSingleResult();
    }

    public <T> Long getResultCount(Class<T> entityClz, Selector selection, Selector groupBy, Specification<T> spec, Pageable pageable) {
        /***
         * JPA has a limitation where it doesn't allow the use of a sub-query in a FROM
         * clause: https://stackoverflow.com/a/12076584. Hence a COUNT cannot be
         * performed to count the number of rows resulting from a aggregation query.
         * This implementation, therefore, counts the number of rows from the
         * result-set. This approach is inefficient as the result data needs to be
         * pulled from the DB over the network.
         * 
         * If and when the Criteria API starts to support using a sub-query in the FROM
         * clause, then a query equivalent to following can be used to count the rows
         * more efficiently
         * 
         * SELECT COUNT (*) FROM ( SELECT NULL FROM TABLE WHERE <SPEC> GROUP BY
         * <GROUP_BY_CLAUSE> ) AS SUB_QUERY
         */
        TypedQuery<Object> tq = qResolver.buildQuery(entityClz, Object.class, selection, groupBy, spec, pageable);

        return tq.getResultStream().count();
    }
}
