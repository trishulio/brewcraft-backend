package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockingAsynExecutorTest {
    private BlockingAsyncExecutor executor;

    @BeforeEach
    public void init() {
        this.executor = new BlockingAsyncExecutor();
    }

    @Test
    public void testSupply_ReturnsCombinedResultsAfterExecutingAllSuppliersParallely() {
        Supplier<Integer> supplier1 = new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    Thread.sleep(2000);
                    return 1;
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to execute supplier");
                }
            }
        };

        Supplier<Integer> supplier2 = new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    Thread.sleep(2000);
                    return 2;
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to execute supplier");
                }
            }
        };

        long startTime = System.currentTimeMillis();
        List<Integer> values = this.executor.supply(List.of(supplier1, supplier2));
        long endTime = System.currentTimeMillis();
        long seconds = (endTime - startTime) / 1000;

        assertEquals(List.of(1, 2), values);
        assertEquals(2, seconds);
    }

    @Test
    public void testSupply_ThrowsException_WhenAnyActionThrowsException() {
        Supplier<Integer> supplier1 = new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    Thread.sleep(2000);
                    throw new RuntimeException("Expect to fail");
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to execute supplier");
                }
            }
        };

        Supplier<Integer> supplier2 = new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    Thread.sleep(2000);
                    return 2;
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to execute supplier");
                }
            }
        };

        assertThrows(RuntimeException.class, () -> this.executor.supply(List.of(supplier1, supplier2)));
    }

    @Test
    public void testRun_ExecutesRunnablesParallely() {
        Runnable mock = mock(Runnable.class);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mock.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to execute runnable");
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mock.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to execute runnable");
                }
            }
        };

        long startTime = System.currentTimeMillis();
        this.executor.run(List.of(runnable1, runnable2));
        long endTime = System.currentTimeMillis();
        long seconds = (endTime - startTime) / 1000;

        assertEquals(2, seconds);
        verify(mock, times(2)).run();
    }

    @Test
    public void testRun_ThrowsException_WhenAnyRunnableThrowsException() {
        Runnable mock = mock(Runnable.class);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    throw new RuntimeException("Expected to fail");
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to execute runnable");
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mock.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to execute runnable");
                }
            }
        };

        assertThrows(RuntimeException.class, () -> this.executor.run(List.of(runnable1, runnable2)));

        verify(mock, times(1)).run();
    }
}
