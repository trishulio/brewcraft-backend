package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.CriteriaJoinAnnotationProcessor;
import io.company.brewcraft.repository.CriteriaJoinProcessor;
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
        @ManyToMany
        private Child manyToMany;
        @ManyToOne
        private Child manyToOne;
        @OneToMany
        private Child oneToMany;
        @Embedded
        private Child embedded;
        @JoinColumn
        private Child joinColumn;
        @Basic
        private Child basic;
        @CriteriaJoin
        private Child throwError;
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
    public void testApply_PerformsALeftJoin_WhenAnnotationValueIsNotProvided() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("difault", JoinType.LEFT);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "difault");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsADefaultJoin_WhenCriteriaJoinAnnotationIsMissingOnManyToManyEntity() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("manyToMany", CriteriaJoin.DEFAULT_JOIN_TYPE);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "manyToMany");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsADefaultJoin_WhenCriteriaJoinAnnotationIsMissingOnOneToManyEntity() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("oneToMany", CriteriaJoin.DEFAULT_JOIN_TYPE);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "oneToMany");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsADefaultJoin_WhenCriteriaJoinAnnotationIsMissingOnManyToOneEntity() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("manyToOne", CriteriaJoin.DEFAULT_JOIN_TYPE);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "manyToOne");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsADefaultJoin_WhenCriteriaJoinAnnotationIsMissingOnEmbeddedEntity() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("embedded", CriteriaJoin.DEFAULT_JOIN_TYPE);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "embedded");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_PerformsADefaultJoin_WhenCriteriaJoinAnnotationIsMissingOnJoinColumnEntity() {
        Join<?, ?> mJoin = mock(Join.class);
        doReturn(mJoin).when(mEntity).join("joinColumn", CriteriaJoin.DEFAULT_JOIN_TYPE);

        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "joinColumn");

        assertEquals(mJoin, join);
    }

    @Test
    public void testApply_DoesntPerformJoin_WhenEntityIsBasic() {
        From<?, ?> join = this.cjProcessor.apply(mEntity, Entity.class, "basic");

        assertEquals(mEntity, join);
    }

    @Test
    public void testApply_ThrowsException_WhenCriteriaJoinIsPerformedOnBasicEntity() {
        assertThrows(IllegalAccessError.class, () -> this.cjProcessor.apply(mEntity, Entity.class, "throwError"));
    }
}
