package io.company.brewcraft.service;

import static org.mockito.Mockito.spy;

import java.util.Set;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public abstract class TestBeans {
    public static BlockingAsyncExecutor mockExecutor() {
        BlockingAsyncExecutor executor = new BlockingAsyncExecutor();

        return spy(executor);
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

    public static <ID, E extends CrudEntity<ID>, BE, UE extends UpdatableEntity<ID>> UpdateService<ID, E, BE, UE> updateService(Class<BE> baseEntityCls, Class<UE> updateEntityCls, Class<E> entityCls, Set<String> excludeAttr) {
        UpdateService<ID, E, BE, UE> realService = new SimpleUpdateService<>(utilProvider(), baseEntityCls, updateEntityCls, entityCls, excludeAttr);

        return spy(realService);
    }

    public static BlockingAsyncExecutor executor() {
        return new BlockingAsyncExecutor();
    }

    public static <ID, E extends Identified<ID>, BE, UE> BulkIaasClient<ID, E, BE, UE> bulkClient(IaasClient<ID, E, BE, UE> iaasClient) {
        return new BulkIaasClient<>(executor(), iaasClient);
    }

    public static LocalDateTimeMapper dtMapper() {
        return LocalDateTimeMapper.INSTANCE;
    }

    public static TenantIaasAuthResourceMapper tenantIaasAuthResourceMapper() {
        return new TenantIaasAuthResourceMapper();
    }
}
