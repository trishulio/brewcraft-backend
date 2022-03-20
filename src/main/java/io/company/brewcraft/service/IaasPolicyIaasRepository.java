package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import com.amazonaws.services.identitymanagement.model.Policy;

import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.UpdateIaasPolicy;

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

        List<Policy> iamPolicies = this.executor.supply(suppliers)
                                                .stream()
                                                .filter(Objects::nonNull)
                                                .toList();

        return this.mapper.fromIamPolicies(iamPolicies);
    }

    public List<IaasPolicy> add(Collection<? extends BaseIaasPolicy> policies) {
        List<Supplier<Policy>> suppliers = new ArrayList<>();
        for (BaseIaasPolicy iamPolicy: policies) {
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

    public List<IaasPolicy> put(Collection<? extends UpdateIaasPolicy> policies) {
        List<Supplier<Policy>> suppliers = new ArrayList<>();
        for (UpdateIaasPolicy iamPolicy: policies) {
            Supplier<Policy> supplier = new Supplier<Policy>() {
                @Override
                public Policy get() {
                    return iamClient.put(iamPolicy.getName(), iamPolicy.getDescription(), iamPolicy.getDocument());
                }
            };
            suppliers.add(supplier);
        }

        List<Policy> iamPolicies = this.executor.supply(suppliers);

        return this.mapper.fromIamPolicies(iamPolicies);
    }

    public long delete(Set<String> ids) {
        List<Supplier<Boolean>> suppliers = ids.stream()
                                            .filter(Objects::nonNull)
                                            .map(id -> (Supplier<Boolean>) () -> iamClient.delete(id))
                                            .toList();

        return this.executor.supply(suppliers)
                             .stream()
                             .filter(b -> b)
                             .count();
    }
    
    public Map<String, Boolean> exists(Set<String> ids) {
        Map<String, Boolean> exists = new HashMap<>();

        get(ids).stream()
               .map(IaasPolicy::getId)
               .forEach(existingId -> exists.put(existingId, true));
        ids.forEach(id -> exists.putIfAbsent(id, false));

        return exists;
    }
}
