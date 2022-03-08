package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.AwsDocumentTemplates;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.TenantIaasAuthResources;
import io.company.brewcraft.model.UpdateIaasRole;

public class TenantIaasAuthService {
    private AwsDocumentTemplates templates;
    private TenantIaasAuthResourceMapper mapper;
    private IaasRoleService roleService;
    
    public TenantIaasAuthService(TenantIaasAuthResourceMapper mapper, IaasRoleService roleService, AwsDocumentTemplates templates) {
        this.templates = templates;
        this.mapper = mapper;
        this.roleService = roleService;
    }

    public List<TenantIaasAuthResources> get(List<IaasIdpTenant> idpTenants) {
        Set<String> roleIds = new HashSet<>();

        idpTenants
        .stream()
        .map(idpTenant -> idpTenant.getId())
        .forEach(idpTenantId -> {
            String roleName = this.templates.getTenantIaasRoleName(idpTenantId);
            roleIds.add(roleName);
         });

        List<IaasRole> roles = this.roleService.getAll(roleIds);
        
        return this.mapper.fromComponents(roles);
    }

    public List<TenantIaasAuthResources> add(List<IaasIdpTenant> idpTenants) {
        List<BaseIaasRole> roleUpdates = new ArrayList<>(idpTenants.size());

        idpTenants.forEach(idpTenant -> {
            String idpTenantId = idpTenant.getId();

            BaseIaasRole role = new IaasRole();
            role.setName(this.templates.getTenantIaasRoleName(idpTenantId));
            role.setDescription(this.templates.getTenantIaasRoleDescription(idpTenantId));
            role.setAssumePolicyDocument(this.templates.getCognitoIdAssumeRolePolicyDoc());

            roleUpdates.add(role);
        });

        List<IaasRole> roles = this.roleService.add(roleUpdates);

        return this.mapper.fromComponents(roles);
    }

    public List<TenantIaasAuthResources> put(List<IaasIdpTenant> idpTenants) {
        List<UpdateIaasRole> roleUpdates = new ArrayList<>(idpTenants.size());

        idpTenants.forEach(idpTenant -> {
            String idpTenantId = idpTenant.getId();

            UpdateIaasRole role = new IaasRole();
            role.setName(this.templates.getTenantIaasRoleName(idpTenantId));
            role.setDescription(this.templates.getTenantIaasRoleDescription(idpTenantId));
            role.setAssumePolicyDocument(this.templates.getCognitoIdAssumeRolePolicyDoc());

            roleUpdates.add(role);
        });

        List<IaasRole> roles = this.roleService.put(roleUpdates);

        return this.mapper.fromComponents(roles);
    }

    public void delete(List<IaasIdpTenant> idpTenants) {
        Set<String> roleIds = new HashSet<>();

        idpTenants
        .stream()
        .map(idpTenant -> idpTenant.getId())
        .forEach(idpTenantId -> {
            String roleName = this.templates.getTenantIaasRoleName(idpTenantId);
            roleIds.add(roleName);
         });

        this.roleService.delete(roleIds);
    }
}
