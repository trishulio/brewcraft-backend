package io.company.brewcraft.repository;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.repository.AggregationRepositoryTest.TestEntity;
import io.company.brewcraft.service.Selector;

public class QueryResolverTest {

    private QueryResolver qResolver;

    private EntityManager mEm;
    private CriteriaBuilder mCb;
    private CriteriaQuery<TestEntity> mCq;
    private Root<TestEntity> mRoot;
    private TypedQuery<TestEntity> mTq;

    @BeforeEach
    public void init() {
        mEm = mock(EntityManager.class);

        mCb = mock(CriteriaBuilder.class);
        doReturn(mCb).when(mEm).getCriteriaBuilder();

        mCq = mock(CriteriaQuery.class);
        doReturn(mCq).when(mCb).createQuery(Object.class);

        mRoot = mock(Root.class);
        doReturn(mRoot).when(mCq).from(TestEntity.class);

        mTq = mock(TypedQuery.class);
        doReturn(mTq).when(mEm).createQuery(mCq);

        doReturn(mTq).when(mTq).setFirstResult(any(Integer.class));
        doReturn(mTq).when(mTq).setMaxResults(any(Integer.class));

        qResolver = new QueryResolver(mEm);
    }

    @Test
    public void testBuildQuery_ReturnsTypedQueryWithSelectAndGroupByAndWhereAndPageableClausesApplied_WhenAllClausesAreNotNull() {
        Specification<TestEntity> mSpec = mock(Specification.class);
        Predicate mPred = mock(Predicate.class);
        doReturn(mPred).when(mSpec).toPredicate(mRoot, mCq, mCb);

        Selector mSelector = mock(Selector.class);
        List<Selection<?>> mSelection = List.of(mock(Selection.class));
        doReturn(mSelection).when(mSelector).getSelection(mRoot, mCq, mCb);

        Selector mGroupBySelector = mock(Selector.class);
        List<Expression<?>> mGroupSelection = List.of(mock(Expression.class));
        doReturn(mGroupSelection).when(mGroupBySelector).getSelection(mRoot, mCq, mCb);

        TypedQuery<Object> q = this.qResolver.buildQuery(TestEntity.class, Object.class, mSelector, mGroupBySelector, mSpec, PageRequest.of(10, 99));
        assertSame(mTq, q);

        InOrder order = inOrder(mCq, mTq);
        order.verify(mCq, times(1)).where(mPred);
        order.verify(mCq, times(1)).multiselect(mSelection);
        order.verify(mCq, times(1)).groupBy(mGroupSelection);
        order.verify(mTq).setFirstResult(990);
        order.verify(mTq).setMaxResults(99);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void testBuildQuery_ReturnsTypedQueryWithoutGroupByClause_WhenGroupByClauseIsNull() {
        Specification<TestEntity> mSpec = mock(Specification.class);
        Predicate mPred = mock(Predicate.class);
        doReturn(mPred).when(mSpec).toPredicate(mRoot, mCq, mCb);

        Selector mSelector = mock(Selector.class);
        List<Selection<?>> mSelection = List.of(mock(Selection.class));
        doReturn(mSelection).when(mSelector).getSelection(mRoot, mCq, mCb);

        TypedQuery<Object> q = this.qResolver.buildQuery(TestEntity.class, Object.class, mSelector, null, mSpec, PageRequest.of(10, 99));
        assertSame(mTq, q);

        InOrder order = inOrder(mCq, mTq);
        order.verify(mCq, times(1)).where(mPred);
        order.verify(mCq, times(1)).multiselect(mSelection);
        order.verify(mTq).setFirstResult(990);
        order.verify(mTq).setMaxResults(99);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void testBuildQuery_ReturnsTypedQueryWithoutPagination_WhenPageableClauseIsNull() {
        Specification<TestEntity> mSpec = mock(Specification.class);
        Predicate mPred = mock(Predicate.class);
        doReturn(mPred).when(mSpec).toPredicate(mRoot, mCq, mCb);

        Selector mSelector = mock(Selector.class);
        List<Selection<?>> mSelection = List.of(mock(Selection.class));
        doReturn(mSelection).when(mSelector).getSelection(mRoot, mCq, mCb);

        Selector mGroupBySelector = mock(Selector.class);
        List<Expression<?>> mGroupSelection = List.of(mock(Expression.class));
        doReturn(mGroupSelection).when(mGroupBySelector).getSelection(mRoot, mCq, mCb);

        TypedQuery<Object> q = this.qResolver.buildQuery(TestEntity.class, Object.class, mSelector, mGroupBySelector, mSpec, null);
        assertSame(mTq, q);

        InOrder order = inOrder(mCq, mTq);
        order.verify(mCq, times(1)).where(mPred);
        order.verify(mCq, times(1)).multiselect(mSelection);
        order.verify(mCq, times(1)).groupBy(mGroupSelection);
        order.verifyNoMoreInteractions();
    }
}
