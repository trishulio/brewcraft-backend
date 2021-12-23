package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.JoinColumn;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.CriteriaJoin;

public class CriteriaJoinAnnotationProcessorTest {
    class Entity {
        @JoinColumn
        @CriteriaJoin(type = JoinType.LEFT)
        private Child left;
        @JoinColumn
        @CriteriaJoin(type = JoinType.RIGHT)
        private Child right;
        @JoinColumn
        @CriteriaJoin(type = JoinType.INNER)
        private Child inner;
        @JoinColumn
        @CriteriaJoin
        private Child difault;

        private Child get;
    }

    class Child {
    }

    private CriteriaJoinProcessor cjProcessor;

    private From<?, Entity> mEntity;

    @BeforeEach
    public void init() {
        mEntity = mock(From.class);
        this.cjProcessor = new CriteriaJoinAnnotationProcessor();
    }

    @Test
    public void testApply_PerformsALeftJoin_WhenAnnotationValueIsLeft() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("left", JoinType.LEFT);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "left");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsARightJoin_WhenAnnotationValueIsRight() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("right", JoinType.RIGHT);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "right");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsAInnerJoin_WhenAnnotationValueIsInner() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("inner", JoinType.INNER);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "inner");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsAInnerJoin_WhenAnnotationValueIsNotProvided() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("difault", JoinType.INNER);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "difault");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsAInnerJoinOperation_WhenAnnotationIsMissing() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("get", JoinType.INNER);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "get");

        assertEquals(mJoin, join);
    }
}
