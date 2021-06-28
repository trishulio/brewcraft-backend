package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.Aggregation;
import io.company.brewcraft.service.MaxAggregation;

@SuppressWarnings("unchecked")
public class MaxAggregationTest {
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
    public void testPathArrayConstructor_CreatesMaxExpressionOnRootPath() {
        Path<?> mPath1 = mock(Path.class);
        Path<? extends Number> mPath2 = mock(Path.class);
        
        doReturn(mPath1).when(mRoot).get("PATH_1");
        doReturn(mPath2).when(mPath1).get("PATH_2");

        Expression<?> mMaxExpr = mock(Expression.class);
        doReturn(mMaxExpr).when(mCb).max(mPath2);
        
        aggr = new MaxAggregation("PATH_1", "PATH_2");
        
        Expression<?> expr = aggr.getExpression(mRoot, mCq, mCb);
        assertSame(mMaxExpr, expr);
    }

    @Test
    public void testAggrConstructor_CreatesMaxExpressionWrapperOnGivenAggregation() {
        Aggregation mArg = mock(Aggregation.class);
        Expression<? extends Number> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mArg).getExpression(mRoot, mCq, mCb);

        Expression<?> mMaxExpr = mock(Expression.class);
        doReturn(mMaxExpr).when(mCb).max(mExpr);
        
        aggr = new MaxAggregation(mArg);
        
        Expression<?> expr = aggr.getExpression(mRoot, mCq, mCb);
        assertSame(mMaxExpr, expr);
    }
}
