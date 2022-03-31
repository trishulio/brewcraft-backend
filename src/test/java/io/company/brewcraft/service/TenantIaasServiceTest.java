package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantIaasAuthDeleteResult;
import io.company.brewcraft.model.TenantIaasAuthResources;
import io.company.brewcraft.model.TenantIaasDeleteResult;
import io.company.brewcraft.model.TenantIaasIdpDeleteResult;
import io.company.brewcraft.model.TenantIaasIdpResources;
import io.company.brewcraft.model.TenantIaasResources;
import io.company.brewcraft.model.TenantIaasVfsDeleteResult;
import io.company.brewcraft.model.TenantIaasVfsResources;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.service.mapper.TenantIaasIdpTenantMapper;

public class TenantIaasServiceTest {

    private TenantIaasService service;

    private TenantIaasAuthService mAuthService;
    private TenantIaasIdpService mIdpService;
    private TenantIaasVfsService mVfsService;

    @BeforeEach
    public void init() {
        mAuthService = mock(TenantIaasAuthService.class);
        mIdpService = mock(TenantIaasIdpService.class);
        mVfsService = mock(TenantIaasVfsService.class);

        this.service = new TenantIaasService(mAuthService, mIdpService, mVfsService, TenantIaasIdpTenantMapper.INSTANCE);
    }

    @Test
    public void testGet_GetsAndReturnsListTenantIaasResourcesFromIndividualResources() {
        List<IaasIdpTenant> idpTenants = List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"), new IaasIdpTenant("00000000-0000-0000-0000-000000000002"));

        List<TenantIaasAuthResources> authResources = List.of(new TenantIaasAuthResources(new IaasRole("T1_ROLE")), new TenantIaasAuthResources(new IaasRole("T2_ROLE")));
        doReturn(authResources).when(mAuthService).get(idpTenants);

        List<TenantIaasIdpResources> idpResources = List.of(new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")));
        doReturn(idpResources).when(mIdpService).get(idpTenants);

        List<TenantIaasVfsResources> vfsResources = List.of(new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"), new IaasPolicy("T1_POLICY")), new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY")));
        doReturn(vfsResources).when(mVfsService).get(idpTenants);

        List<Tenant> tenants = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
        List<TenantIaasResources> resources = service.get(tenants);

        List<TenantIaasResources> expected = List.of(
            new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T1_ROLE")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")), new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"), new IaasPolicy("T1_POLICY"))),
            new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T2_ROLE")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")), new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY")))
        );

        assertEquals(expected, resources);
    }

    @Test
    public void testAdd_AddsAndReturnsListTenantIaasResourcesFromIndividualResources() {
        List<TenantIaasAuthResources> authResources = List.of(new TenantIaasAuthResources(new IaasRole("T1_ROLE")), new TenantIaasAuthResources(new IaasRole("T2_ROLE")));
        doReturn(authResources).when(mAuthService).add(List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"), new IaasIdpTenant("00000000-0000-0000-0000-000000000002")));

        List<BaseIaasIdpTenant> idpTenants = List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"), new IaasIdpTenant("00000000-0000-0000-0000-000000000002"));
        idpTenants.get(0).setIaasRole(new IaasRole("T1_ROLE"));
        idpTenants.get(1).setIaasRole(new IaasRole("T2_ROLE"));

        List<TenantIaasIdpResources> idpResources = List.of(new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")));
        doReturn(idpResources).when(mIdpService).add(idpTenants);

        List<TenantIaasVfsResources> vfsResources = List.of(new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"), new IaasPolicy("T1_POLICY")), new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY")));
        doReturn(vfsResources).when(mVfsService).add(idpTenants);

        List<Tenant> tenants = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
        List<TenantIaasResources> resources = service.add(tenants);

        List<TenantIaasResources> expected = List.of(
            new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T1_ROLE")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")), new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"), new IaasPolicy("T1_POLICY"))),
            new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T2_ROLE")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")), new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY")))
        );

        assertEquals(expected, resources);
    }

    @Test
    public void testPut_PutsAndReturnsListTenantIaasResourcesFromIndividualResources() {
        List<TenantIaasAuthResources> authResources = List.of(new TenantIaasAuthResources(new IaasRole("T1_ROLE")), new TenantIaasAuthResources(new IaasRole("T2_ROLE")));
        doReturn(authResources).when(mAuthService).put(List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"), new IaasIdpTenant("00000000-0000-0000-0000-000000000002")));

        List<UpdateIaasIdpTenant> idpTenants = List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"), new IaasIdpTenant("00000000-0000-0000-0000-000000000002"));
        idpTenants.get(0).setIaasRole(new IaasRole("T1_ROLE"));
        idpTenants.get(1).setIaasRole(new IaasRole("T2_ROLE"));

        List<TenantIaasIdpResources> idpResources = List.of(new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")));
        doReturn(idpResources).when(mIdpService).put(idpTenants);

        List<TenantIaasVfsResources> vfsResources = List.of(new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"), new IaasPolicy("T1_POLICY")), new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY")));
        doReturn(vfsResources).when(mVfsService).put(idpTenants);

        List<Tenant> tenants = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
        List<TenantIaasResources> resources = service.put(tenants);

        List<TenantIaasResources> expected = List.of(
            new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T1_ROLE")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T1")), new TenantIaasVfsResources(new IaasObjectStore("T1_OBJECT_STORE"), new IaasPolicy("T1_POLICY"))),
            new TenantIaasResources(new TenantIaasAuthResources(new IaasRole("T2_ROLE")), new TenantIaasIdpResources(new IaasIdpTenant("IDP_T2")), new TenantIaasVfsResources(new IaasObjectStore("T2_OBJECT_STORE"), new IaasPolicy("T2_POLICY")))
        );

        assertEquals(expected, resources);
    }

    @Test
    public void testDelete_DeletesAndReturnsCombinedDeleteResult() {
        List<IaasIdpTenant> idpTenants = List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"), new IaasIdpTenant("00000000-0000-0000-0000-000000000002"));

        doReturn(new TenantIaasAuthDeleteResult(3)).when(mAuthService).delete(idpTenants);
        doReturn(new TenantIaasIdpDeleteResult(4)).when(mIdpService).delete(idpTenants);
        doReturn(new TenantIaasVfsDeleteResult(5, 6)).when(mVfsService).delete(idpTenants);

        List<Tenant> tenants = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
        TenantIaasDeleteResult result = service.delete(tenants);

        TenantIaasDeleteResult expected = new TenantIaasDeleteResult(new TenantIaasAuthDeleteResult(3), new TenantIaasIdpDeleteResult(4), new TenantIaasVfsDeleteResult(5, 6));
        assertEquals(expected, result);
    }
}
