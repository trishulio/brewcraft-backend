package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.service.Selector;

@SuppressWarnings("unchecked")
public class AggregationRepositoryTest {
    class TestEntity extends BaseModel {
        private int data;

        public TestEntity(int data) {
            this.data = data;
        }
        
        public int getData() { 
            return this.data;
        }
        
        public void setData(int data) {
            this.data = data;
        }
    }

    private AggregationRepository repo;
    
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
        doReturn(mCq).when(mCb).createQuery(TestEntity.class);
        
        mRoot = mock(Root.class);
        doReturn(mRoot).when(mCq).from(TestEntity.class);
        
        mTq = mock(TypedQuery.class);
        doReturn(mTq).when(mEm).createQuery(mCq);
        
        doReturn(mTq).when(mTq).setFirstResult(any(Integer.class));
        doReturn(mTq).when(mTq).setMaxResults(any(Integer.class));

        repo = new AggregationRepository(mEm);
    }

    @Test
    public void testGetAggregation_PerformsSelection_WhenNothingIsNull() {
        Specification<TestEntity> mSpec = mock(Specification.class);
        Predicate mPred = mock(Predicate.class);
        doReturn(mPred).when(mSpec).toPredicate(mRoot, mCq, mCb);
        
        Selector mSelector = mock(Selector.class);
        List<Selection<?>> mSelection = List.of(mock(Selection.class));
        doReturn(mSelection).when(mSelector).getSelection(mRoot, mCb);
        
        Selector mGroupBySelector = mock(Selector.class);
        List<Expression<?>> mGroupSelection = List.of(mock(Expression.class));
        doReturn(mGroupSelection).when(mGroupBySelector).getSelection(mRoot, mCb);

        doAnswer(inv -> {
            InOrder order = inOrder(mCq, mTq);
            order.verify(mCq, times(1)).where(mPred);
            order.verify(mCq, times(1)).multiselect(mSelection);
            order.verify(mCq, times(1)).groupBy(mGroupSelection);
            order.verify(mTq).setFirstResult(990);
            order.verify(mTq).setMaxResults(99);

            return List.of(new TestEntity(100));
        }).when(mTq).getResultList();

        Page<TestEntity> page = repo.getAggregation(
            TestEntity.class,
            mSelector,
            mGroupBySelector,
            mSpec,
            PageRequest.of(10, 99)
        );
        
        Page<TestEntity> expected = new PageImpl<>(List.of(new TestEntity(100)));
        assertEquals(expected, page);
    }
    
}
