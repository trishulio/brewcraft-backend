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
import io.company.brewcraft.service.SumAggregation;

@SuppressWarnings("unchecked")
public class SumAggregationTest {

    private Aggregation aggr;

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
    public void testPathArrayConstructor_CreatesSumExpressionOnRootPath() {
        final Path<?> mPath1 = mock(Path.class);
        final Path<?> mPath2 = mock(Path.class);

        doReturn(mPath1).when(this.mRoot).get("PATH_1");
        doReturn(mPath2).when(mPath1).get("PATH_2");

        final Expression<?> mSumExpr = mock(Expression.class);
        doReturn(mSumExpr).when(this.mCb).sum((Expression<? extends Number>) mPath2);

        this.aggr = new SumAggregation("PATH_1", "PATH_2");

        final Expression<?> expr = this.aggr.getExpression(this.mRoot, this.mCq, this.mCb);
        assertSame(mSumExpr, expr);
    }

    @Test
    public void testAggrConstructor_CreatesSumExpressionWrapperOnGivenAggregation() {
        final Aggregation mArg = mock(Aggregation.class);
        final Expression<? extends Number> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mArg).getExpression(this.mRoot, this.mCq, this.mCb);

        final Expression<?> mSumExpr = mock(Expression.class);
        doReturn(mSumExpr).when(this.mCb).sum(mExpr);

        this.aggr = new SumAggregation(mArg);

        final Expression<?> expr = this.aggr.getExpression(this.mRoot, this.mCq, this.mCb);
        assertSame(mSumExpr, expr);
    }
}
