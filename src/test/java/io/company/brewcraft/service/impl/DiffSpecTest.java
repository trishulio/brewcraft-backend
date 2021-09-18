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
import io.company.brewcraft.service.DiffSpec;

@SuppressWarnings("unchecked")
public class DiffSpecTest {
    private CriteriaSpec<Number> aggr;

    private Root<?> mRoot;
    private CriteriaBuilder mCb;
    private CriteriaQuery<?> mCq;

    @BeforeEach
    public void init() {
        this.mRoot = mock(Root.class);
        this.mCb = mock(CriteriaBuilder.class);
        this.mCq = mock(CriteriaQuery.class);
    }

    @Test
    public void testAggrConstructor_CreatesDiffExpressionWrapperOnGivenAggregation() {
        final CriteriaSpec<Number> mArg1 = mock(CriteriaSpec.class);
        final Expression<? extends Number> mExpr1 = mock(Expression.class);
        doReturn(mExpr1).when(mArg1).getExpression(this.mRoot, this.mCq, this.mCb);

        final CriteriaSpec<Number> mArg2 = mock(CriteriaSpec.class);
        final Expression<? extends Number> mExpr2 = mock(Expression.class);
        doReturn(mExpr2).when(mArg2).getExpression(this.mRoot, this.mCq, this.mCb);

        final Expression<?> mDiffExpr = mock(Expression.class);
        doReturn(mDiffExpr).when(this.mCb).diff(mExpr1, mExpr2);

        this.aggr = new DiffSpec(mArg1, mArg2);

        final Expression<?> expr = this.aggr.getExpression(this.mRoot, this.mCq, this.mCb);
        assertSame(mDiffExpr, expr);
    }
}
