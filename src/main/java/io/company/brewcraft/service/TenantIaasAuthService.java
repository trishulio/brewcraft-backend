package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.TenantIaasAuthDeleteResult;
import io.company.brewcraft.model.TenantIaasAuthResources;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.model.UpdateIaasRole;

public class TenantIaasAuthService {
    private TenantIaasResourceBuilder resourceBuilder;
    private TenantIaasAuthResourceMapper mapper;
    private IaasRoleService roleService;

    public TenantIaasAuthService(TenantIaasAuthResourceMapper mapper, IaasRoleService roleService, TenantIaasResourceBuilder resourceBuilder) {
        this.resourceBuilder = resourceBuilder;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    public List<TenantIaasAuthResources> get(Set<String> idpTenantsIds) {
        Set<String> roleIds = new HashSet<>();

        idpTenantsIds
        .stream()
        .forEach(idpTenantsId -> {
            String roleName = this.resourceBuilder.getRoleId(idpTenantsId);
            roleIds.add(roleName);
         });

        List<IaasRole> roles = this.roleService.getAll(roleIds);

        return this.mapper.fromComponents(roles);
    }

    public List<TenantIaasAuthResources> add(List<BaseIaasIdpTenant> idpTenants) {
        List<BaseIaasRole> roleUpdates = new ArrayList<>(idpTenants.size());

        idpTenants.forEach(idpTenant -> {
            BaseIaasRole role = this.resourceBuilder.buildRole(idpTenant);
            roleUpdates.add(role);
        });

        List<IaasRole> roles = this.roleService.add(roleUpdates);

        return this.mapper.fromComponents(roles);
    }

    public List<TenantIaasAuthResources> put(List<UpdateIaasIdpTenant> idpTenants) {
        List<UpdateIaasRole> roleUpdates = new ArrayList<>(idpTenants.size());

        idpTenants.forEach(idpTenant -> {
            UpdateIaasRole role = this.resourceBuilder.buildRole(idpTenant);

            roleUpdates.add(role);
        });

        List<IaasRole> roles = this.roleService.put(roleUpdates);

        return this.mapper.fromComponents(roles);
    }

    public TenantIaasAuthDeleteResult delete(Set<String> iaasIdpTenantIds) {
        Set<String> roleIds = new HashSet<>();

        iaasIdpTenantIds
        .stream()
        .forEach(iaasIdpTenantId -> {
            String roleName = this.resourceBuilder.getRoleId(iaasIdpTenantId);
            roleIds.add(roleName);
         });

        long roleCount = this.roleService.delete(roleIds);

        return new TenantIaasAuthDeleteResult(roleCount);
    }
}
