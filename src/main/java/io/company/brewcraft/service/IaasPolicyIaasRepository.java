package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import com.amazonaws.services.identitymanagement.model.Policy;

import io.company.brewcraft.model.IaasPolicy;

public class IaasPolicyIaasRepository {
    private AwsIamPolicyClient iamClient;
    private BlockingAsyncExecutor executor;
    private AwsIaasPolicyMapper mapper;

    public IaasPolicyIaasRepository(AwsIamPolicyClient iamClient, BlockingAsyncExecutor executor, AwsIaasPolicyMapper mapper) {
        this.iamClient = iamClient;
        this.executor = executor;
        this.mapper = mapper;
    }

    public List<IaasPolicy> get(Collection<String> ids) {
        List<Supplier<Policy>> suppliers = new ArrayList<>();
        for (String id: ids) {
            Supplier<Policy> supplier = new Supplier<Policy>() {
                @Override
                public Policy get() {
                    return iamClient.get(id);
                }
            };
            suppliers.add(supplier);
        }

        List<Policy> iamPolicies = this.executor.supply(suppliers);

        return this.mapper.fromIamPolicies(iamPolicies);
    }

    public List<IaasPolicy> add(Collection<IaasPolicy> policies) {
        List<Supplier<Policy>> suppliers = new ArrayList<>();
        for (IaasPolicy iamPolicy: policies) {
            Supplier<Policy> supplier = new Supplier<Policy>() {
                @Override
                public Policy get() {
                    return iamClient.add(iamPolicy.getName(), iamPolicy.getDescription(), iamPolicy.getDocument());
                }
            };
            suppliers.add(supplier);
        }

        List<Policy> iamPolicies = this.executor.supply(suppliers);

        return this.mapper.fromIamPolicies(iamPolicies);
    }

    public void delete(Collection<String> ids) {
        List<Runnable> runnables = new ArrayList<>();

        for (String id: ids) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    iamClient.delete(id);
                }
            };
            runnables.add(runnable);
        }

        this.executor.run(runnables);
    }
}
