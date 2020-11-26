package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Supplier;

@SuppressWarnings("unchecked")
public class InvoiceRepositoryGetAllInvoicesSpecificationTest {

    private InvoiceRepositoryGetAllInvoicesSpecification specification;

    @Test
    public void testToPredicate_ReturnsCriteriaBuilderWithAllPredicates() {
        specification = spy(new InvoiceRepositoryGetAllInvoicesSpecification());

        Root<Invoice> mRoot = mock(Root.class);
        CriteriaQuery<Invoice> mQuery = mock(CriteriaQuery.class);
        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);

        Predicate mPredicateWithPredicates = mock(Predicate.class);
        Predicate[] mPredicates = { mock(Predicate.class) };

        doReturn(mPredicates).when(specification).getPredicates(mRoot, mQuery, mBuilder);
        doReturn(mPredicateWithPredicates).when(mBuilder).and(mPredicates);

        Predicate p = specification.toPredicate(mRoot, mQuery, mBuilder);

        assertSame(mPredicateWithPredicates, p);
    }

    @Test
    public void testGetPredicates_AddsDatePredicateOnly_WhenDateToAndFromAreNotNull() {
        Root<Invoice> mRoot = mock(Root.class);
        Path<Date> mPath = mock(Path.class);
        doReturn(mPath).when(mRoot).get("date");

        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);
        Predicate mDatePredicate = mock(Predicate.class);
        doReturn(mDatePredicate).when(mBuilder).between(mPath, new Date(123), new Date(456));

        Predicate mExpectedPredicate = mock(Predicate.class);
        doReturn(mExpectedPredicate).when(mBuilder).and(mDatePredicate);

        CriteriaQuery<Invoice> mQuery = mock(CriteriaQuery.class);
        specification = new InvoiceRepositoryGetAllInvoicesSpecification(new Date(123), new Date(456), null, null);
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(1, predicates.length);
        assertSame(mExpectedPredicate, predicates[0]);
    }

    @Test
    public void testGetPredicates_AddsInStatusClauseToQuery_WhenStatusesAreNotNull() {
        Path<InvoiceStatus> mPath = mock(Path.class);
        Predicate mInPredicate = mock(Predicate.class);
        doReturn(mInPredicate).when(mPath).in(List.of(InvoiceStatus.PENDING));

        Root<Invoice> mRoot = mock(Root.class);
        doReturn(mPath).when(mRoot).get("status");

        CriteriaQuery<Invoice> mQuery = mock(CriteriaQuery.class);
        doReturn(mQuery).when(mQuery).select(mRoot);

        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);

        specification = new InvoiceRepositoryGetAllInvoicesSpecification(null, null, List.of(InvoiceStatus.PENDING), null);
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(0, predicates.length);
        InOrder order = inOrder(mQuery);
        order.verify(mQuery, times(1)).select(mRoot);
        order.verify(mQuery, times(1)).where(mInPredicate);
    }

    @Test
    public void testGetPredicates_AddsInSupplierIdClauseToQuery_WhenSupplierIdsAreNotNull() {
        Predicate mInPredicate = mock(Predicate.class);
        Path<String> mSupplierIdPath = mock(Path.class);
        doReturn(mInPredicate).when(mSupplierIdPath).in(List.of(12345L));

        Path<Supplier> mSupplierPath = mock(Path.class);
        doReturn(mSupplierIdPath).when(mSupplierPath).get("id");

        Root<Invoice> mRoot = mock(Root.class);
        doReturn(mSupplierPath).when(mRoot).get("supplier");

        CriteriaQuery<Invoice> mQuery = mock(CriteriaQuery.class);
        doReturn(mQuery).when(mQuery).select(mRoot);

        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);

        specification = new InvoiceRepositoryGetAllInvoicesSpecification(null, null, null, List.of(12345L));
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(0, predicates.length);
        InOrder order = inOrder(mQuery);
        order.verify(mQuery, times(1)).select(mRoot);
        order.verify(mQuery, times(1)).where(mInPredicate);
    }

    @Test
    public void testGetPredicates_AddsAllPredicates_WhenNoArgIsNull() {
        // Invoice Status Criteria Mock
        Path<InvoiceStatus> mInvoiceStatusPath = mock(Path.class);
        Predicate mInInvoiceStatusPredicate = mock(Predicate.class);
        doReturn(mInInvoiceStatusPredicate).when(mInvoiceStatusPath).in(List.of(InvoiceStatus.PENDING));

        // SupplierId Criteria Mock
        Predicate mInSupplierIdsPredicate = mock(Predicate.class);
        Path<String> mSupplierIdPath = mock(Path.class);
        doReturn(mInSupplierIdsPredicate).when(mSupplierIdPath).in(List.of(12345L));

        Path<Supplier> mSupplierPath = mock(Path.class);
        doReturn(mSupplierIdPath).when(mSupplierPath).get("id");

        // Date Criteria Mock
        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);
        Path<Date> mDatePath = mock(Path.class);
        Predicate mDatePredicate = mock(Predicate.class);
        doReturn(mDatePredicate).when(mBuilder).between(mDatePath, new Date(123), new Date(456));

        Predicate mAndDatePredicate = mock(Predicate.class);
        doReturn(mAndDatePredicate).when(mBuilder).and(mDatePredicate);

        Root<Invoice> mRoot = mock(Root.class);
        doReturn(mInvoiceStatusPath).when(mRoot).get("status");
        doReturn(mSupplierPath).when(mRoot).get("supplier");
        doReturn(mDatePath).when(mRoot).get("date");

        CriteriaQuery<Invoice> mQuery = mock(CriteriaQuery.class);
        doReturn(mQuery).when(mQuery).select(mRoot);

        specification = new InvoiceRepositoryGetAllInvoicesSpecification(new Date(123), new Date(456), List.of(InvoiceStatus.PENDING), List.of(12345L));
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(1, predicates.length);
        assertSame(mAndDatePredicate, predicates[0]);

        verify(mQuery, times(2)).select(mRoot);
        verify(mQuery, times(1)).where(mInInvoiceStatusPredicate);
        verify(mQuery, times(1)).where(mInSupplierIdsPredicate);
    }
}
