package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import com.amazonaws.services.identitymanagement.model.Role;

import io.company.brewcraft.model.IaasRole;

public class IaasRoleIaasRepository {
    private AwsIamRoleClient iamClient;
    private BlockingAsyncExecutor executor;
    private AwsIaasRoleMapper mapper;

    public IaasRoleIaasRepository(AwsIamRoleClient iamClient, BlockingAsyncExecutor executor, AwsIaasRoleMapper mapper) {
        this.iamClient = iamClient;
        this.executor = executor;
        this.mapper = mapper;
    }
    
    public List<IaasRole> get(Collection<String> ids) {
        List<Supplier<Role>> suppliers = ids.stream()
                .filter(id -> id != null)
                .map(id -> (Supplier<Role>) () -> iamClient.get(id))
                .toList();
        
        List<Role> iamRoles = this.executor.supply(suppliers);
        
        return this.mapper.fromIamRoles(iamRoles);
    }
    
    public List<IaasRole> add(Collection<IaasRole> policies) {
        List<Supplier<Role>> suppliers = policies.stream()
                .filter(role -> role != null)
                .map(role -> (Supplier<Role>) () -> iamClient.add(role.getName(), role.getDescription(), role.getAssumePolicyDocument()))
                .toList();
        
        List<Role> iamRoles = this.executor.supply(suppliers);
        
        return this.mapper.fromIamRoles(iamRoles);
    }
    
    public void delete(Collection<String> ids) {
        List<Runnable> runnables = ids.stream()
                .filter(id -> id != null)
                .map(id -> (Runnable) () -> iamClient.delete(id))
                .toList();
        
        this.executor.run(runnables);
    }
}
