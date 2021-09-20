package io.company.brewcraft.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.PathSpec;

public class PathAggregationTest {

    private CriteriaSpec<String> aggr;

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
    public void testPathArrConstructor() {
        Path<?> mPath1 = mock(Path.class);
        Path<?> mPath2 = mock(Path.class);

        doReturn(mPath1).when(mRoot).get("PATH_1");
        doReturn(mPath2).when(mPath1).get("PATH_2");

        aggr = new PathSpec<>(new String[] { "PATH_1", "PATH_2" });

        assertSame(mPath2, aggr.getExpression(mRoot, mCq, mCb));
    }
}
