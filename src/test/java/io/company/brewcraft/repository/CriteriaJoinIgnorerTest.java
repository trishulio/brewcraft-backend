package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.From;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CriteriaJoinIgnorerTest {
    class Entity {}

    private CriteriaJoinProcessor processor;
    private From<?, Entity> mRoot;

    @BeforeEach
    public void init() {
        mRoot = mock(From.class);
        processor = new CriteriaJoinIgnorer();
    }

    @Test
    public void testApply_DoesNothingAndReturnsEntity() {
        From<?, ?> join = processor.apply(mRoot, Entity.class, "FOO");

        assertEquals(mRoot, join);
    }
}
