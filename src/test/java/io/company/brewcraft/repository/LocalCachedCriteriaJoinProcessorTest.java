package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.persistence.criteria.From;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalCachedCriteriaJoinProcessorTest {
    class Entity {
    }

    class Child {
    }

    private From<Object, Entity> mRoot;
    private From<Child, Entity> mChild;

    private CriteriaJoinProcessor joiner;
    private CriteriaJoinProcessor mDelegate;

    @BeforeEach
    public void init() {
        mDelegate = mock(CriteriaJoinAnnotationProcessor.class);
        joiner = new LocalCachedCriteriaJoinProcessor(mDelegate);

        mRoot = mock(From.class);
        mChild = mock(From.class);
    }

    @Test
    public void testApply_ReturnsNewJoin_WhenCacheIsMissing() {
        doReturn(mChild).when(mDelegate).apply(mRoot, Entity.class, "child");

        From<Object, Entity> join = joiner.apply(mRoot, Entity.class, "child");

        assertEquals(mChild, join);
        verify(mDelegate, times(1)).apply(any(), any(), any());
    }

    @Test
    public void testApply_ReturnsCachedJoin_WhenCacheIsPresent() {
        doReturn(mChild).when(mDelegate).apply(mRoot, Entity.class, "child");

        joiner.apply(mRoot, Entity.class, "child");
        joiner.apply(mRoot, Entity.class, "child");
        From<Object, Entity> join = joiner.apply(mRoot, Entity.class, "child");

        assertEquals(mChild, join);
        verify(mDelegate, times(1)).apply(any(), any(), any());
    }

    @Test
    public void testApply_ReturnsCachedJoinFromLocalThread() throws InterruptedException, ExecutionException {
        doReturn(mChild).when(mDelegate).apply(mRoot, Entity.class, "child");

        CompletableFuture<From<Object, Entity>> op1 = CompletableFuture.supplyAsync(() -> joiner.apply(mRoot, Entity.class, "child"));
        CompletableFuture<From<Object, Entity>> op2 = CompletableFuture.supplyAsync(() -> joiner.apply(mRoot, Entity.class, "child"));

        op1.thenAccept(join -> assertEquals(mChild, join));
        op2.thenAccept(join -> assertEquals(mChild, join));

        CompletableFuture.allOf(op1, op2)
                         .thenRun(() -> verify(mDelegate, times(2)).apply(any(), any(), any()))
                         .get();
    }

    @Test
    public void testApply_ReturnsCachedJoinFromLocalThread_Multithreaded() throws InterruptedException {
        doReturn(mChild).when(mDelegate).apply(mRoot, Entity.class, "child");

        new Thread(() ->{
            From<Object, Entity> join = joiner.apply(mRoot, Entity.class, "child");
            assertEquals(mChild, join);
        }).start();

        new Thread(() -> {
            From<Object, Entity> join = joiner.apply(mRoot, Entity.class, "child");
            assertEquals(mChild, join);
        }).start();

        Thread.sleep(1000);

        verify(mDelegate, times(2)).apply(any(), any(), any());
    }
}
