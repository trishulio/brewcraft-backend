package io.company.brewcraft.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.util.entity.ReflectionManipulator;

public class InvoiceRepositoryGetAllInvoicesSpecification implements Specification<InvoiceEntity> {
    private static final long serialVersionUID = -4419077865693397354L;

    private Set<Long> ids;
    private LocalDateTime from;
    private LocalDateTime to;
    private Set<InvoiceStatus> statuses;
    private Set<Long> supplierIds;

    public InvoiceRepositoryGetAllInvoicesSpecification() {
        this(null, null, null, null, null);
    }

    public InvoiceRepositoryGetAllInvoicesSpecification(Set<Long> ids, LocalDateTime from, LocalDateTime to, Set<InvoiceStatus> statuses, Set<Long> supplierIds) {
        this.ids = ids;
        this.from = from;
        this.to = to;
        this.statuses = statuses;
        this.supplierIds = supplierIds;
    }

    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        @SuppressWarnings("unchecked")
        CriteriaQuery<InvoiceEntity> invoiceQuery = (CriteriaQuery<InvoiceEntity>) query;
        return criteriaBuilder.and(this.getPredicates(root, invoiceQuery, criteriaBuilder));
    }

    public Predicate[] getPredicates(Root<InvoiceEntity> root, CriteriaQuery<InvoiceEntity> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>(10);
        
        if (ids != null && ids.size() > 0) {
            predicates.add(criteriaBuilder.and(root.get("id").in(ids)));
        }
        
        if (from != null && to != null) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get("date"), this.from, this.to)));
        }

        if (statuses != null) {
            predicates.add(criteriaBuilder.and(root.get("status").in(statuses)));
        }

        if (supplierIds != null) {
            predicates.add(criteriaBuilder.and(root.get("supplier").get("id").in(supplierIds)));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public boolean equals(Object o) {
        return ReflectionManipulator.INSTANCE.equals(this, o);
    }

}
