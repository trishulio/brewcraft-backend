package io.company.brewcraft.service;

import java.util.List;

import com.amazonaws.services.identitymanagement.model.Role;

import io.company.brewcraft.model.IaasRole;

public class AwsIaasRoleMapper {
    private LocalDateTimeMapper dtMapper;

    public AwsIaasRoleMapper(LocalDateTimeMapper dtMapper) {
        this.dtMapper = dtMapper;
    }

    public List<IaasRole> fromIamRoles(List<Role> iamRoles) {
        List<IaasRole> policies = null;

        if (iamRoles != null) {
            policies = iamRoles.stream().map(this::fromIamRole).toList();
        }

        return policies;
    }

    public IaasRole fromIamRole(Role iamRole) {
        IaasRole role = null;

        if (iamRole != null) {
            role = new IaasRole();
            role.setId(iamRole.getRoleName());
            role.setDescription(iamRole.getDescription());
            role.setIaasResourceName(iamRole.getArn());
            role.setIaasId(iamRole.getRoleId());
            if (iamRole.getRoleLastUsed() != null) {
                role.setLastUsed(this.dtMapper.fromUtilDate(iamRole.getRoleLastUsed().getLastUsedDate()));
            }
            role.setCreatedAt(this.dtMapper.fromUtilDate(iamRole.getCreateDate()));
        }

        return role;
    }
}
