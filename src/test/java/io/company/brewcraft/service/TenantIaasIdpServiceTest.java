package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.TenantIaasIdpDeleteResult;
import io.company.brewcraft.model.TenantIaasIdpResources;

public class TenantIaasIdpServiceTest {
    private TenantIaasIdpService service;

    private IaasIdpTenantService mIdpTenantService;

    @BeforeEach
    public void init() {
        mIdpTenantService = mock(IaasIdpTenantService.class);

        service = new TenantIaasIdpService(mIdpTenantService, TenantIaasIdpResourcesMapper.INSTANCE);
    }

    @Test
    public void testGet_ReturnsAuthResourcesFromComponents() {
        doReturn(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2"))).when(mIdpTenantService).getAll(Set.of("T1", "T2"));

        List<TenantIaasIdpResources> resources = service.get(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasIdpResources> expected = List.of(
            new TenantIaasIdpResources(new IaasIdpTenant("T1")),
            new TenantIaasIdpResources(new IaasIdpTenant("T2"))
        );

        assertEquals(expected, resources);
    }

    @Test
    public void testAdd_ReturnsAddedAuthResourcesFromComponents() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIdpTenantService).add(anyList());

        List<TenantIaasIdpResources> resources = service.add(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasIdpResources> expected = List.of(
            new TenantIaasIdpResources(new IaasIdpTenant("T1")),
            new TenantIaasIdpResources(new IaasIdpTenant("T2"))
        );

        assertEquals(expected, resources);
        verify(mIdpTenantService).add(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));
    }

    @Test
    public void testPut_ReturnsPutAuthResourcesFromComponents() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIdpTenantService).put(anyList());

        List<TenantIaasIdpResources> resources = service.put(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasIdpResources> expected = List.of(
            new TenantIaasIdpResources(new IaasIdpTenant("T1")),
            new TenantIaasIdpResources(new IaasIdpTenant("T2"))
        );

        assertEquals(expected, resources);
        verify(mIdpTenantService).put(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));
    }

    @Test
    public void testDelete_ReturnsDeleteResult() {
        doAnswer(inv -> new Long(inv.getArgument(0, Set.class).size())).when(mIdpTenantService).delete(anySet());

        TenantIaasIdpDeleteResult res = service.delete(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        assertEquals(new TenantIaasIdpDeleteResult(2), res);
        verify(mIdpTenantService).delete(Set.of("T1", "T2"));
    }
}
