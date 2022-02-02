package io.company.brewcraft.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import io.company.brewcraft.model.TenantPolicy;

public class TenantPolicyIaasRepository {
//    private PolicyDocumentProvider policyProvider;
    
    private AwsIamClient iamClient;
    
    public TenantPolicyIaasRepository(AwsIamClient iamClient) {
        this.iamClient = iamClient;
    }
    
    public void putPolicies(List<TenantPolicy> policies) {
        // TODO: Replace create method with put for idempotency
        CompletableFuture<?>[] operations = policies.stream()
                                                    .filter(policy -> policy != null)
                                                    .map(policy -> policy.getIaasResourceName())
                                                    .filter(policyName -> policyName != null)
                                                    .map(policyName -> CompletableFuture.supplyAsync(() -> iamClient.createPolicy("", "", ""))) // TODO: pick values
                                                    .collect(Collectors.toList())
                                                    .toArray(new CompletableFuture<?>[0]);
        try {
            CompletableFuture.allOf(operations).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(String.format("Failed to put policies because: %s", e.getMessage()), e);
        }
    }
}
