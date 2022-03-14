package io.company.brewcraft.service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.function.Supplier;

public abstract class MockUtils {
    
    public static BlockingAsyncExecutor mockExecutor() {
        BlockingAsyncExecutor executor = mock(BlockingAsyncExecutor.class);
        
        doAnswer(inv -> {
            List<Supplier<?>> suppliers = inv.getArgument(0, List.class);
            
            return suppliers.stream().map(Supplier::get).toList();
        }).when(executor).supply(anyList());

        doAnswer(inv -> {
            List<Runnable> runnables = inv.getArgument(0, List.class);
            
            runnables.stream().forEach(Runnable::run);
            return null;
        }).when(executor).run(anyList());

        return executor;
    }
    
}
