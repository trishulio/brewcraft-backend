package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.TenantIaasAuthDeleteResult;
import io.company.brewcraft.model.TenantIaasAuthResources;

public class TenantIaasAuthServiceTest {
    private TenantIaasAuthService service;

    private TenantIaasAuthResourceMapper mMapper;
    private IaasRoleService mRoleService;
    private TenantIaasResourceBuilder mResourceBuilder;

    @BeforeEach
    public void init() {
        mMapper = TestBeans.tenantIaasAuthResourceMapper();
        mRoleService = mock(IaasRoleService.class);
        mResourceBuilder = mock(TenantIaasResourceBuilder.class);

        service = new TenantIaasAuthService(mMapper, mRoleService, mResourceBuilder);
    }

    @Test
    public void testGet_ReturnsAuthResourceWithComponents() {
        doAnswer(inv -> inv.getArgument(0, IaasIdpTenant.class).getName() + "_ROLE_NAME").when(mResourceBuilder).getRoleName(any(IaasIdpTenant.class));
        doAnswer(inv -> inv.getArgument(0, Set.class).stream().map(id -> new IaasRole(id.toString())).toList()).when(mRoleService).getAll(anySet());

        List<TenantIaasAuthResources> resources = this.service.get(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasAuthResources> expected = List.of(
            new TenantIaasAuthResources(new IaasRole("T1_ROLE_NAME")),
            new TenantIaasAuthResources(new IaasRole("T2_ROLE_NAME"))
        );

        assertEquals(expected, resources);
    }

    @Test
    public void testAdd_ReturnsResourcesFromAddedRoles() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mRoleService).add(anyList());
        doAnswer(inv -> new IaasRole(inv.getArgument(0, IaasIdpTenant.class).getId())).when(mResourceBuilder).buildRole(any(IaasIdpTenant.class));

        List<TenantIaasAuthResources> resources = this.service.add(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasAuthResources> expected = List.of(
            new TenantIaasAuthResources(new IaasRole("T1")),
            new TenantIaasAuthResources(new IaasRole("T2"))
        );

        assertEquals(expected, resources);
    }

    @Test
    public void testPut_ReturnsResourcesFromPutRoles() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mRoleService).put(anyList());
        doAnswer(inv -> new IaasRole(inv.getArgument(0, IaasIdpTenant.class).getId())).when(mResourceBuilder).buildRole(any(IaasIdpTenant.class));

        List<TenantIaasAuthResources> resources = this.service.put(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasAuthResources> expected = List.of(
            new TenantIaasAuthResources(new IaasRole("T1")),
            new TenantIaasAuthResources(new IaasRole("T2"))
        );

        assertEquals(expected, resources);
    }

    @Test
    public void testDelete_ReturnsDeleteResultWithCounts() {
        doAnswer(inv -> inv.getArgument(0, IaasIdpTenant.class).getId()).when(mResourceBuilder).getRoleName(any(IaasIdpTenant.class));
        doAnswer(inv -> new Long(inv.getArgument(0, Set.class).size())).when(mRoleService).delete(anySet());

        TenantIaasAuthDeleteResult result = this.service.delete(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        assertEquals(new TenantIaasAuthDeleteResult(2L), result);
    }
}
