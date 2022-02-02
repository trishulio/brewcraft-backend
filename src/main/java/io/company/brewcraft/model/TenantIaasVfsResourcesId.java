package io.company.brewcraft.model;

public class TenantIaasVfsResourcesId {
    private Long roleId;
    private Long bucketId;

    public TenantIaasVfsResourcesId(Long roleId, Long bucketId) {
        setRoleId(roleId);
        setBucketId(bucketId);
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getBucketId() {
        return bucketId;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
    }
}
