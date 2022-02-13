package io.company.brewcraft.model;

public interface IaasRolePolicyAttachmentAccessor {
    IaasRolePolicyAttachment getTenantIaasRolePolicyAttachment();
    
    void setTenantIaasRolePolicyAttachment(IaasRolePolicyAttachment attachment);
}
