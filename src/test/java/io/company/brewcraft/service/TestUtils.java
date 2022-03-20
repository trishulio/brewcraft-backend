package io.company.brewcraft.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public abstract class TestUtils {

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

    public static UtilityProvider utilProvider() {
        Validator validator = new Validator();
        return new UtilityProvider() {
            @Override
            public void setValidator(Validator validator) {
            }

            @Override
            public Validator getValidator() {
                return validator;
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <ID, E extends CrudEntity<ID>, BE, UE extends UpdatableEntity<ID>> UpdateService<ID, E, BE, UE> updateService(Class<BE> baseEntityCls, Class<UE> updateEntityCls, Class<E> entityCls, Set<String> excludeAttr) {
        UpdateService<ID, E, BE, UE> realService = new SimpleUpdateService<>(utilProvider(), baseEntityCls, updateEntityCls, entityCls, excludeAttr);

        UpdateService<ID, E, BE, UE> mockService = mock(UpdateService.class);
        doAnswer(inv -> realService.getAddEntities(inv.getArgument(0, List.class))).when(mockService).getAddEntities(any());
        doAnswer(inv -> realService.getPutEntities(inv.getArgument(0, List.class), inv.getArgument(1, List.class))).when(mockService).getPutEntities(any(), any());
        doAnswer(inv -> realService.getPatchEntities(inv.getArgument(0, List.class), inv.getArgument(1, List.class))).when(mockService).getPatchEntities(any(), any());

        return mockService;
    }
}
