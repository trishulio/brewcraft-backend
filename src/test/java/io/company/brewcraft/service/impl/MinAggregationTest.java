package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.MinSpec;

public class MinAggregationTest {
    private CriteriaSpec<Number> aggr;

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
    public void testAggrConstructor_CreatesMinExpressionWrapperOnGivenAggregation() {
        CriteriaSpec<Number> mArg = mock(CriteriaSpec.class);
        Expression<? extends Number> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mArg).getExpression(mRoot, mCq, mCb);

        Expression<?> mMinExpr = mock(Expression.class);
        doReturn(mMinExpr).when(mCb).min(mExpr);

        aggr = new MinSpec<Number>(mArg);

        Expression<?> expr = aggr.getExpression(mRoot, mCq, mCb);
        assertSame(mMinExpr, expr);
    }
}
