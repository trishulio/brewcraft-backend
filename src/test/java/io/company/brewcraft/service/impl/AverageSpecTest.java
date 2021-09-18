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
import io.company.brewcraft.service.AverageSpec;

@SuppressWarnings("unchecked")
public class AverageSpecTest {

    private CriteriaSpec<Double> aggr;

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
    public void testAggrConstructor_CreatesAvgExpressionWrapperOnGivenAggregation() {
        CriteriaSpec<Double> mArg = mock(CriteriaSpec.class);
        Expression<?> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mArg).getExpression(mRoot, mCq, mCb);

        Expression<?> mAvgExpr = mock(Expression.class);
        doReturn(mAvgExpr).when(mCb).avg((Expression<? extends Number>) mExpr);

        aggr = new AverageSpec(mArg);

        Expression<?> expr = aggr.getExpression(mRoot, mCq, mCb);
        assertSame(mAvgExpr, expr);
    }
}
