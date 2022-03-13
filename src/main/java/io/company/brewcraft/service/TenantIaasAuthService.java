package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.TenantIaasAuthResources;
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

    public List<TenantIaasAuthResources> get(List<IaasIdpTenant> idpTenants) {
        Set<String> roleIds = new HashSet<>();

        idpTenants
        .stream()
        .forEach(idpTenant -> {
            String roleName = this.resourceBuilder.getRoleName(idpTenant);
            roleIds.add(roleName);
         });

        List<IaasRole> roles = this.roleService.getAll(roleIds);

        return this.mapper.fromComponents(roles);
    }

    public List<TenantIaasAuthResources> add(List<IaasIdpTenant> idpTenants) {
        List<BaseIaasRole> roleUpdates = new ArrayList<>(idpTenants.size());

        idpTenants.forEach(idpTenant -> {
            BaseIaasRole role = this.resourceBuilder.buildRole(idpTenant);
            roleUpdates.add(role);
        });

        List<IaasRole> roles = this.roleService.add(roleUpdates);

        return this.mapper.fromComponents(roles);
    }

    public List<TenantIaasAuthResources> put(List<IaasIdpTenant> idpTenants) {
        List<UpdateIaasRole> roleUpdates = new ArrayList<>(idpTenants.size());

        idpTenants.forEach(idpTenant -> {
            UpdateIaasRole role = this.resourceBuilder.buildRole(idpTenant);

            roleUpdates.add(role);
        });

        List<IaasRole> roles = this.roleService.put(roleUpdates);

        return this.mapper.fromComponents(roles);
    }

    public void delete(List<IaasIdpTenant> idpTenants) {
        Set<String> roleIds = new HashSet<>();

        idpTenants
        .stream()
        .forEach(idpTenant -> {
            String roleName = this.resourceBuilder.getRoleName(idpTenant);
            roleIds.add(roleName);
         });

        this.roleService.delete(roleIds);
    }
}
