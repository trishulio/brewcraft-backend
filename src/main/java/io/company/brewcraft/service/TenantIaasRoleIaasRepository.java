package io.company.brewcraft.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import io.company.brewcraft.model.TenantIaasRole;
import io.company.brewcraft.model.TenantPolicy;

public class TenantIaasRoleIaasRepository {
    private AwsIamClient iamClient;
    
    public TenantIaasRoleIaasRepository(AwsIamClient iamClient) {
        this.iamClient = iamClient;
    }
    
    public void putRoles(List<TenantIaasRole> roles) {
        this.iamClient.createPolicy(null, null, null);
        
        CompletableFuture<?>[] operations = roles.stream()
                .filter(role -> role != null && role.getRoleName() != null)
                .map(role -> {
                    CompletableFuture<?>[] attachRolePolicies = new CompletableFuture<?>[0];
                    CompletableFuture<Void> createRole = CompletableFuture.runAsync(() -> iamClient.createRole(role.getRoleName(), "")); // TODO
                    List<TenantPolicy> policies = role.getPolicies();
                    if (policies != null && !policies.isEmpty()) {
                        attachRolePolicies = policies.stream()
                                .map(policy -> createRole.thenRunAsync(() -> iamClient.attachRolePolicy(role.getRoleName(), policy.getIaasResourceName())))
                                .collect(Collectors.toList())
                                .toArray(attachRolePolicies);
                    }
                    
                    return CompletableFuture.allOf(attachRolePolicies);
                })
                .collect(Collectors.toList())
                .toArray(new CompletableFuture<?>[0]);
        try {
            CompletableFuture.allOf(operations).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(String.format("Failed to put roles because: %s", e.getMessage()), e);
        }
    }
}
