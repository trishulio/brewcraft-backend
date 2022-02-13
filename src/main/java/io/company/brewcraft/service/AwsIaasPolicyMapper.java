package io.company.brewcraft.service;

import java.util.List;

import com.amazonaws.services.identitymanagement.model.Policy;

import io.company.brewcraft.model.IaasPolicy;

public class AwsIaasPolicyMapper {
    private LocalDateTimeMapper dtMapper;
    
    public AwsIaasPolicyMapper(LocalDateTimeMapper dtMapper) {
        this.dtMapper = dtMapper;
    }

    public List<IaasPolicy> fromIamPolicies(List<Policy> iamPolicies) {
        List<IaasPolicy> policies = null;
        
        if (iamPolicies != null) {
            policies = iamPolicies.stream().map(this::fromIamPolicy).toList();
        }
        
        return policies;
    }
    
    public IaasPolicy fromIamPolicy(Policy iamPolicy) {
        IaasPolicy policy = null;
        
        if (iamPolicy != null) {
            policy = new IaasPolicy();
            policy.setId(iamPolicy.getPolicyName());
            policy.setDescription(iamPolicy.getDescription());
            policy.setIaasId(iamPolicy.getPolicyId());
            policy.setIaasResourceName(iamPolicy.getArn());
            policy.setCreatedAt(this.dtMapper.fromUtilDate(iamPolicy.getCreateDate()));
            policy.setLastUpdated(this.dtMapper.fromUtilDate(iamPolicy.getUpdateDate()));
        }
        
        return policy;
    }
}
