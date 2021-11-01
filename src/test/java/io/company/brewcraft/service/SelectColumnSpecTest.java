package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.RootUtil;

public class SelectColumnSpecTest {
    private Root<?> mRoot;
    private RootUtil mRootUtil;

    private SelectColumnSpec<String> column;

    @BeforeEach
    public void init() {
        mRootUtil = mock(RootUtil.class);
        column = new SelectColumnSpec<>(mRootUtil, new String[] {"JOIN_1", "JOIN_2"}, new String[] {"PATH_1", "PATH_2"});
    }

    @Test
    public void testGetExpression_GetsPathWithJoin() {
        Path<?> mPath = mock(Path.class);
        doReturn(mPath).when(mRootUtil).getPathWithJoin(mRoot, new String[] {"JOIN_1", "JOIN_2"}, new String[] {"PATH_1", "PATH_2"});

        Expression<?> expression = column.getExpression(mRoot, null, null);

        assertEquals(mPath, expression);
    }
}
