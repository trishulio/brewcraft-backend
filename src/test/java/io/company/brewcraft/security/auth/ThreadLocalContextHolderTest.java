package io.company.brewcraft.security.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;
import io.company.brewcraft.security.session.ThreadLocalContexHolder;

public class ThreadLocalContextHolderTest {
    private ContextHolder holder;

    @BeforeEach
    public void init() {
        holder = new ThreadLocalContexHolder();
    }

    @Test
    public void testAccessPrincipalContext_ReturnsTheTenantThatIsSetUsingMutator() {
        PrincipalContext mCtx = mock(PrincipalContext.class);
        holder.setContext(mCtx);

        PrincipalContext ctx = holder.getPrincipalContext();
        assertSame(mCtx, ctx);
    }

    @Test
    public void testAccessPrincipalContext_ReturnsTheThreadsLocalContext() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> op1 = runAsync(() -> {
            PrincipalContext mCtx1 = mock(PrincipalContext.class);
            holder.setContext(mCtx1);
            Thread.sleep(75);
            PrincipalContext ctx = holder.getPrincipalContext();
            assertSame(mCtx1, ctx);
        });
        CompletableFuture<Void> op2 = runAsync(() -> {
            PrincipalContext mCtx2 = mock(PrincipalContext.class);
            holder.setContext(mCtx2);
            Thread.sleep(50);
            PrincipalContext ctx = holder.getPrincipalContext();
            assertSame(mCtx2, ctx);
        });
        CompletableFuture<Void> op3 = runAsync(() -> {
            PrincipalContext mCtx3 = mock(PrincipalContext.class);
            holder.setContext(mCtx3);
            Thread.sleep(25);
            PrincipalContext ctx = holder.getPrincipalContext();
            assertSame(mCtx3, ctx);
        });

        CompletableFuture.allOf(op1, op2, op3).get();
    }

    private CompletableFuture<Void> runAsync(CheckedRunnable<Exception> runnable) {
        CompletableFuture<Void> marker = new CompletableFuture<>();
        new Thread(() -> {
            try {
                runnable.run();
                marker.complete(null);
            } catch (Exception e) {
                fail();
            }
        }).start();

        return marker;
    }
}

interface CheckedRunnable<T extends Throwable> {
    void run() throws T;
}