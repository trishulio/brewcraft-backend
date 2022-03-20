package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import com.amazonaws.services.identitymanagement.model.AttachedPolicy;

import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;
import io.company.brewcraft.model.UpdateIaasRolePolicyAttachment;

public class IaasRolePolicyAttachmentIaasRepository {
    private AwsIamRolePolicyAttachmentClient iamClient;
    private BlockingAsyncExecutor executor;

    public IaasRolePolicyAttachmentIaasRepository(AwsIamRolePolicyAttachmentClient iamClient, BlockingAsyncExecutor executor) {
        this.iamClient = iamClient;
        this.executor = executor;
    }

    public List<IaasRolePolicyAttachment> get(Set<IaasRolePolicyAttachmentId> ids) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public List<IaasRolePolicyAttachment> add(Collection<? extends BaseIaasRolePolicyAttachment> attachments) {
        List<Supplier<IaasRolePolicyAttachment>> suppliers = attachments.stream()
                .filter(Objects::nonNull)
                .map(attachment -> (Supplier<IaasRolePolicyAttachment>) () -> {
                    iamClient.add(attachment.getIaasPolicy().getId(), attachment.getIaasRole().getId());

                    return (IaasRolePolicyAttachment) attachment;
                })
                .toList();

        return this.executor.supply(suppliers);
    }

    public List<IaasRolePolicyAttachment> put(Collection<? extends UpdateIaasRolePolicyAttachment> attachments) {
        List<Supplier<IaasRolePolicyAttachment>> suppliers = attachments.stream()
                .filter(Objects::nonNull)
                .map(attachment -> (Supplier<IaasRolePolicyAttachment>) () -> {
                    iamClient.put(attachment.getIaasPolicy().getId(), attachment.getIaasRole().getId());

                    return (IaasRolePolicyAttachment) attachment;
                })
                .toList();

        return this.executor.supply(suppliers);
    }

    public void delete(Set<IaasRolePolicyAttachmentId> ids) {
        List<Runnable> runnables = ids.stream()
                .filter(Objects::nonNull)
                .map(id -> (Runnable) () -> iamClient.delete(id.getPolicyId(), id.getRoleId()))
                .toList();

        this.executor.run(runnables);
    }

    public boolean exists(IaasRolePolicyAttachmentId id) {
        return exists(Set.of(id)).get(id);
    }

    public Map<IaasRolePolicyAttachmentId, Boolean> exists(Set<IaasRolePolicyAttachmentId> ids) {
        Map<IaasRolePolicyAttachmentId, Boolean> exists = new HashMap<>();

        List<IaasRolePolicyAttachmentId> existingIds = getExistingIds(ids);

        for (IaasRolePolicyAttachmentId existingId: existingIds) {
            if (ids.contains(existingId)) {
                exists.put(existingId, true);
            }
        }

        ids.forEach(id -> exists.putIfAbsent(id, false));

        return exists;
    }

    private List<IaasRolePolicyAttachmentId> getExistingIds(Set<IaasRolePolicyAttachmentId> ids) {
        Map<String, Set<String>> roleNameToPolicyNames = new HashMap<>();

        ids.forEach(id -> {
            Set<String> policyNames = roleNameToPolicyNames.get(id.getRoleId());
            if (policyNames == null) {
                policyNames = new HashSet<>();
                roleNameToPolicyNames.put(id.getRoleId(), policyNames);
            }
            policyNames.add(id.getPolicyId());
        });

        Set<String> roleNames = roleNameToPolicyNames.keySet();

        List<IaasRolePolicyAttachmentId> attachmentIds = new ArrayList<>();

        roleNames.forEach(roleName -> {
            List<AttachedPolicy> policies = iamClient.get(roleName);

            Set<String> policyNames = roleNameToPolicyNames.get(roleName);

            for (AttachedPolicy policy: policies) {
                if (policyNames.contains(policy.getPolicyName())) {
                    IaasRolePolicyAttachmentId attachmentId = new IaasRolePolicyAttachmentId(policy.getPolicyName(), roleName);
                    attachmentIds.add(attachmentId);
                }
            }
        });

        return attachmentIds;
    }
}
