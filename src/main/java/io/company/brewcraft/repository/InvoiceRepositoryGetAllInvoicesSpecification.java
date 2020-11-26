package io.company.brewcraft.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.util.entity.ReflectionManipulator;

public class InvoiceRepositoryGetAllInvoicesSpecification implements Specification<Invoice> {
    private static final long serialVersionUID = -4419077865693397354L;

    private Date from;
    private Date to;
    private List<InvoiceStatus> statuses;
    private List<Long> supplierIds;

    public InvoiceRepositoryGetAllInvoicesSpecification() {
        this(null, null, null, null);
    }

    public InvoiceRepositoryGetAllInvoicesSpecification(Date from, Date to, List<InvoiceStatus> statuses, List<Long> supplierIds) {
        this.from = from;
        this.to = to;
        this.statuses = statuses;
        this.supplierIds = supplierIds;
    }

    @Override
    public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        @SuppressWarnings("unchecked")
        CriteriaQuery<Invoice> invoiceQuery = (CriteriaQuery<Invoice>) query;
        return criteriaBuilder.and(this.getPredicates(root, invoiceQuery, criteriaBuilder));
    }

    public Predicate[] getPredicates(Root<Invoice> root, CriteriaQuery<Invoice> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>(10);
        if (from != null && to != null) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get("date"), this.from, this.to)));
        }

        if (statuses != null) {
            query.select(root).where(root.get("status").in(statuses));
        }

        if (supplierIds != null) {
            query.select(root).where(root.get("supplier").get("id").in(supplierIds));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public boolean equals(Object o) {
        return ReflectionManipulator.INSTANCE.equals(this, o);
    }

}
