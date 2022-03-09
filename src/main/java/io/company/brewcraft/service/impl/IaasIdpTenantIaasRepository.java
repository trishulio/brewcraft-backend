package io.company.brewcraft.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.amazonaws.services.cognitoidp.model.GroupType;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.service.AwsArnMapper;
import io.company.brewcraft.service.BlockingAsyncExecutor;
import io.company.brewcraft.service.IaasRoleService;
import io.company.brewcraft.service.mapper.IaasIdpTenantMapper;

public class IaasIdpTenantIaasRepository {
    private IdentityProviderClient idpClient;
    private BlockingAsyncExecutor executor;
    private IaasIdpTenantMapper mapper;
    private IaasRoleService roleService;
    private AwsArnMapper arnMapper;

    public IaasIdpTenantIaasRepository(IdentityProviderClient idpClient, BlockingAsyncExecutor executor, IaasRoleService roleService, IaasIdpTenantMapper mapper, AwsArnMapper arnMapper) {
        this.idpClient = idpClient;
        this.executor = executor;
        this.mapper = mapper;
        this.arnMapper = arnMapper;
    }

    public List<IaasIdpTenant> get(Set<String> groupIds) {
        List<Supplier<GroupType>> suppliers = groupIds.stream()
                                                  .filter(groupId -> groupId != null)
                                                  .map(groupId -> (Supplier<GroupType>) () -> idpClient.getGroup(groupId))
                                                  .toList();
        List<GroupType> groups = this.executor.supply(suppliers);

        Set<String> roleNames = groups.stream().map(group -> arnMapper.getName(group.getRoleArn())).collect(Collectors.toSet());
        Iterator<IaasRole> roles = roleService.getAll(roleNames).iterator();

        List<IaasIdpTenant> idpTenants = this.mapper.fromGroups(groups);
        // TODO: Assuming that number of roles == number of idpTenants and that the order is same.  Need to validate that's always true.
        idpTenants.forEach(idpTenant -> idpTenant.setIaasRole(roles.next()));

        return idpTenants;
    }

    public List<IaasIdpTenant> add(List<? extends BaseIaasIdpTenant> tenants) {
        List<Supplier<GroupType>> suppliers = tenants.stream()
                                   .filter(tenant -> tenant != null)
                                   .map(tenant -> (Supplier<GroupType>) () -> {
                                       String roleArn = null;
                                       IaasRole role = tenant.getIaasRole();

                                       if (role != null) {
                                           roleArn = role.getIaasResourceName();
                                       }

                                       return idpClient.createGroup(tenant.getName(), roleArn);
                                   }).toList();

        List<GroupType> groups = this.executor.supply(suppliers);

        return this.mapper.fromGroups(groups);
    }

    public List<IaasIdpTenant> put(List<? extends UpdateIaasIdpTenant> tenants) {
        List<Supplier<GroupType>> suppliers = tenants.stream()
                .filter(tenant -> tenant != null)
                .map(tenant -> (Supplier<GroupType>) () -> {
                    String roleArn = null;
                    IaasRole role = tenant.getIaasRole();

                    if (role != null) {
                        roleArn = role.getIaasResourceName();
                    }

                    return idpClient.putGroup(tenant.getName(), roleArn);
                }).toList();

        List<GroupType> groups = this.executor.supply(suppliers);

        return this.mapper.fromGroups(groups);
    }

    public void delete(Set<String> iaasIdpTenantIds) {
        List<Runnable> runnables = iaasIdpTenantIds.stream()
                .filter(iaasIdpTenantId -> iaasIdpTenantId != null)
                .map(iaasIdpTenantId -> (Runnable) () -> idpClient.deleteGroup(iaasIdpTenantId))
                .toList();

        this.executor.run(runnables);
    }

    public boolean exists(String iaasIdpTenantId) {
        return this.idpClient.groupExists(iaasIdpTenantId);
    }
}
