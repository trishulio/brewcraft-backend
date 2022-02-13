package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;

import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;

public class IaasRolePolicyAttachmentIaasRepository {
    private AwsIamRolePolicyAttachmentClient iamClient;
    private BlockingAsyncExecutor executor;

    /// TODO; A lot of client calls don't return anything so retrun values are mocked.
    public IaasRolePolicyAttachmentIaasRepository(AwsIamRolePolicyAttachmentClient iamClient, BlockingAsyncExecutor executor) {
        this.iamClient = iamClient;
        this.executor = executor;
    }
    
    public List<IaasRolePolicyAttachment> get(Collection<IaasRolePolicyAttachmentId> ids) {
        List<Runnable> runnables = ids.stream()
                .filter(id -> id != null)
                .map(id -> (Runnable) () -> iamClient.get(id.getPolicyId(), id.getRoleId()))
                .toList();
        
        this.executor.run(runnables);
        
        return null;
    }

    public List<IaasRolePolicyAttachment> add(Collection<IaasRolePolicyAttachment> attachments) {
        List<Runnable> runnables = attachments.stream()
                .filter(attachment -> attachment != null)
                .map(attachment -> (Runnable) () -> {
                    IaasRolePolicyAttachmentId id = attachment.getId();
                    iamClient.add(id.getPolicyId(), id.getRoleId());
                })
                .toList();

        this.executor.run(runnables);

        return attachments.stream().toList();
    }
    
    public void delete(Collection<IaasRolePolicyAttachmentId> ids) {
        List<Runnable> runnables = ids.stream()
                .filter(id -> id != null)
                .map(id -> (Runnable) () -> iamClient.delete(id.getPolicyId(), id.getRoleId()))
                .toList();
        
        this.executor.run(runnables);
    }
}
