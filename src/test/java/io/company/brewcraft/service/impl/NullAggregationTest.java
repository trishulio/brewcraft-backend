package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.NullSpec;

public class NullAggregationTest {
    private CriteriaSpec<Integer> aggr;

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
    public void testGetAggregation_ReturnsNullLiteralWithIntegerClass() {
        Expression<Integer> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mCb).nullLiteral(Integer.class);

        aggr = new NullSpec();

        assertSame(mExpr, aggr.getExpression(mRoot, mCq, mCb));
    }
}
