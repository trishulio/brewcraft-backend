package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.Aggregation;
import io.company.brewcraft.service.DiffAggregation;

@SuppressWarnings("unchecked")
public class DiffAggregationTest {
    private Aggregation aggr;
    
    private Root<?> mRoot;
    private CriteriaBuilder mCb;
    private CriteriaQuery<?> mCq;
    
    @BeforeEach
    public void init() {
        mRoot = mock(Root.class);
        mCb = mock(CriteriaBuilder.class);
        mCq = mock(CriteriaQuery.class);
    }

    @Test
    public void testAggrConstructor_CreatesDiffExpressionWrapperOnGivenAggregation() {
        Aggregation mArg1 = mock(Aggregation.class);
        Expression<? extends Number> mExpr1 = mock(Expression.class);
        doReturn(mExpr1).when(mArg1).getExpression(mRoot, mCq, mCb);

        Aggregation mArg2 = mock(Aggregation.class);
        Expression<? extends Number> mExpr2 = mock(Expression.class);
        doReturn(mExpr2).when(mArg2).getExpression(mRoot, mCq, mCb);

        Expression<?> mDiffExpr = mock(Expression.class);
        doReturn(mDiffExpr).when(mCb).diff(mExpr1, mExpr2);
        
        aggr = new DiffAggregation(mArg1, mArg2);
        
        Expression<?> expr = aggr.getExpression(mRoot, mCq, mCb);
        assertSame(mDiffExpr, expr);
    }
}
