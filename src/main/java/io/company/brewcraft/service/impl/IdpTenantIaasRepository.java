package io.company.brewcraft.service.impl;

import java.util.List;

import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasTenant;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.service.BlockingAsyncExecutor;

public class IdpTenantIaasRepository {
    private IdentityProviderClient idpClient;
    private BlockingAsyncExecutor executor;

    public IdpTenantIaasRepository(IdentityProviderClient idpClient, BlockingAsyncExecutor executor) {
        this.idpClient = idpClient;
    }
    
    public void add(List<? extends IaasTenant> tenants) {
        List<Runnable> runnables = tenants.stream()
                                   .filter(tenant -> tenant != null)
                                   .map(tenant -> (Runnable) () -> {
                                       String roleArn = null;
                                       IaasRole role = tenant.getIaasRole();

                                       if (role != null) {
                                           roleArn = role.getIaasResourceName();
                                       }

                                       idpClient.createGroup(tenant.getIaasId(), roleArn);
                                   }).toList();
        
        this.executor.run(runnables);
    }

    public void delete(List<String> tenantIaasIds) {
        List<Runnable> runnables = tenantIaasIds.stream()
                .filter(tenantIaasId -> tenantIaasId != null)
                .map(tenantIaasId -> (Runnable) () -> idpClient.deleteGroup(tenantIaasId))
                .toList();

        this.executor.run(runnables);
    }

    public boolean exists(String tenantIaasId) {
        return this.idpClient.groupExists(tenantIaasId);
    }

    public void put(List<? extends IaasTenant> tenants) {
        List<Runnable> runnables = tenants.stream()
                .filter(tenant -> tenant != null)
                .map(tenant -> (Runnable) () -> {
                    if (this.exists(tenant.getIaasId())) {
                        String roleArn = null;
                        IaasRole role = tenant.getIaasRole();

                        if (role != null) {
                            roleArn = role.getIaasResourceName();
                        }

                        this.idpClient.updateGroup(tenant.getIaasId(), roleArn);
                    } else {
                        this.add(List.of(tenant));
                    }
                })
                .toList();

        this.executor.run(runnables);
    }

}
