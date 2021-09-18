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
import io.company.brewcraft.service.SumSpec;

@SuppressWarnings("unchecked")
public class SumAggregationTest {

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
    public void testAggrConstructor_CreatesSumExpressionWrapperOnGivenAggregation() {
        final CriteriaSpec<Number> mArg = mock(CriteriaSpec.class);
        final Expression<? extends Number> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mArg).getExpression(this.mRoot, this.mCq, this.mCb);

        final Expression<?> mSumExpr = mock(Expression.class);
        doReturn(mSumExpr).when(this.mCb).sum(mExpr);

        this.aggr = new SumSpec<>(mArg);

        final Expression<?> expr = this.aggr.getExpression(this.mRoot, this.mCq, this.mCb);
        assertSame(mSumExpr, expr);
    }
}
