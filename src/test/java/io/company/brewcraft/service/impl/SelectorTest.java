package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.CountSpec;
import io.company.brewcraft.service.PathProvider;
import io.company.brewcraft.service.Selector;

public class SelectorTest {
    private Selector selector;

    private Root<?> mRoot;
    private CriteriaBuilder mCb;
    private CriteriaQuery<?> mCq;

    @BeforeEach
    public void init() {
        mRoot = mock(Root.class);
        mCb = mock(CriteriaBuilder.class);
        mCq = mock(CriteriaQuery.class);

        selector = new Selector();
    }

    @Test
    public void testSelectPathProvider_AddsRootPathToSelection() {
        Path<?> mPath1 = mock(Path.class);
        Path<?> mPath2 = mock(Path.class);

        doReturn(mPath1).when(mRoot).get("PATH_1");
        doReturn(mPath2).when(mPath1).get("PATH_2");

        PathProvider mProvider = () -> new String[] { "PATH_1", "PATH_2" };

        selector.select(mProvider);

        assertEquals(List.of(mPath2), selector.getSelection(mRoot, mCq, mCb));
    }

    @Test
    public void testSelectPathArray_AddsRootPathToSelection() {
        Path<?> mPath1 = mock(Path.class);
        Path<?> mPath2 = mock(Path.class);

        doReturn(mPath1).when(mRoot).get("PATH_1");
        doReturn(mPath2).when(mPath1).get("PATH_2");

        selector.select(new String[] { "PATH_1", "PATH_2" });

        assertEquals(List.of(mPath2), selector.getSelection(mRoot, mCq, mCb));
    }

    @Test
    public void testSelectAggregation_AddsAggregationToSelection() {
        CriteriaSpec mCountAggr = mock(CountSpec.class);
        Expression<?> mCountExpr = mock(Expression.class);
        doReturn(mCountExpr).when(mCountAggr).getExpression(mRoot, mCq, mCb);

        selector.select(mCountAggr);

        assertEquals(List.of(mCountExpr), selector.getSelection(mRoot, mCq, mCb));
    }
}
