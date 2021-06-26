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
import io.company.brewcraft.service.CountAggregation;

public class CountAggregationTest {
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
    public void testPathArrayConstructor_CreatesCountExpressionOnRootPath() {
        Path<?> mPath1 = mock(Path.class);
        Path<?> mPath2 = mock(Path.class);

        doReturn(mPath1).when(mRoot).get("PATH_1");
        doReturn(mPath2).when(mPath1).get("PATH_2");

        Expression<?> mCountExpr = mock(Expression.class);
        doReturn(mCountExpr).when(mCb).count(mPath2);

        aggr = new CountAggregation("PATH_1", "PATH_2");

        Expression<?> expr = aggr.getExpression(mRoot, mCq, mCb);
        assertSame(mCountExpr, expr);
    }

    @Test
    public void testAggrConstructor_CreatesCountExpressionWrapperOnGivenAggregation() {
        Aggregation mArg = mock(Aggregation.class);
        Expression<?> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mArg).getExpression(mRoot, mCq, mCb);

        Expression<?> mCountExpr = mock(Expression.class);
        doReturn(mCountExpr).when(mCb).count(mExpr);

        aggr = new CountAggregation(mArg);

        Expression<?> expr = aggr.getExpression(mRoot, mCq, mCb);
        assertSame(mCountExpr, expr);
    }
}
