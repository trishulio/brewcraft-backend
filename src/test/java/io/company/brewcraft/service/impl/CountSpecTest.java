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
import io.company.brewcraft.service.CountSpec;

public class CountSpecTest {
    private CriteriaSpec<Long> aggr;

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
    public void testAggrConstructor_CreatesCountExpressionWrapperOnGivenAggregation() {
        CriteriaSpec<Long> mArg = mock(CriteriaSpec.class);
        Expression<?> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mArg).getExpression(mRoot, mCq, mCb);

        Expression<?> mCountExpr = mock(Expression.class);
        doReturn(mCountExpr).when(mCb).count(mExpr);

        aggr = new CountSpec<>(mArg);

        Expression<?> expr = aggr.getExpression(mRoot, mCq, mCb);
        assertSame(mCountExpr, expr);
    }
}
