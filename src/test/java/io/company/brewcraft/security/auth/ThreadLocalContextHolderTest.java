package io.company.brewcraft.security.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.atomic.AtomicInteger;

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
    public void testAccessPrincipalContext_ReturnsTheThreadsLocalContext() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger(0);

        PrincipalContext mCtx1 = mock(PrincipalContext.class);
        PrincipalContext mCtx2 = mock(PrincipalContext.class);
        PrincipalContext mCtx3 = mock(PrincipalContext.class);

        runAsync(() -> {
            holder.setContext(mCtx1);
            Thread.sleep(75);
            PrincipalContext ctx = holder.getPrincipalContext();
            assertSame(mCtx1, ctx);
            successCount.incrementAndGet();
        });

        runAsync(() -> {
            holder.setContext(mCtx2);
            Thread.sleep(50);
            PrincipalContext ctx = holder.getPrincipalContext();
            assertSame(mCtx2, ctx);
            successCount.incrementAndGet();
        });

        runAsync(() -> {
            holder.setContext(mCtx3);
            Thread.sleep(25);
            PrincipalContext ctx = holder.getPrincipalContext();
            assertSame(mCtx3, ctx);
            successCount.incrementAndGet();
        });

        Thread.sleep(100);

        assertSame(3, successCount.get());
    }

    private void runAsync(CheckedRunnable<Exception> runnable) {
        new Thread(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                fail();
            }
        }).start();
    }

}

interface CheckedRunnable<T extends Throwable> {
    void run() throws T;
}