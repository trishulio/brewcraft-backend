package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.function.Supplier;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class LocalCachedJpaJoinerTest {
    class Entity {
    }

    class Child {
    }

    private From<Object, Entity> mRoot;
    private From<Child, Entity> mChild;

    private JpaJoiner joiner;
    private JpaJoiner mDelegate;
    private LocalJpaJoinerCache mCache;

    @BeforeEach
    public void init() {
        mCache = mock(LocalJpaJoinerCache.class);
        mDelegate = mock(CriteriaJoinAnnotationJoiner.class);
        joiner = new LocalCachedJpaJoiner(mCache, mDelegate);
    }

    @Test
    public void testJoin_ReturnsFromCache_AndPassesSupplierWithCallToDelegate() {
        ArgumentCaptor<Supplier<Path<Entity>>> captor = ArgumentCaptor.forClass(Supplier.class);

        From<Object, Entity> mFrom = mock(From.class);
        doReturn(mFrom).when(mCache).get(eq(new Key(mRoot, Entity.class, "fieldName")), captor.capture());

        From<Object, Entity> from = joiner.join(mRoot, Entity.class, "fieldName");
        assertEquals(mFrom, from);

        From<Object, Entity> mPath = mock(From.class);
        doReturn(mPath).when(mDelegate).join(mRoot, Entity.class, "fieldName");

        Path<Entity> path = captor.getValue().get();
        assertEquals(mPath, path);
    }

    @Test
    public void testGet_ReturnsFromCache_AndPassesSupplierWithCallToDelegate() {
        ArgumentCaptor<Supplier<Path<Entity>>> captor = ArgumentCaptor.forClass(Supplier.class);

        From<Object, Entity> mFrom = mock(From.class);
        doReturn(mFrom).when(mCache).get(eq(new Key(mRoot, Entity.class, "fieldName")), captor.capture());

        Path<Object> from = joiner.get(mRoot, Entity.class, "fieldName");
        assertEquals(mFrom, from);

        From<Object, Entity> mPath = mock(From.class);
        doReturn(mPath).when(mDelegate).get(mRoot, Entity.class, "fieldName");

        Path<Entity> path = captor.getValue().get();
        assertEquals(mPath, path);
    }
}
