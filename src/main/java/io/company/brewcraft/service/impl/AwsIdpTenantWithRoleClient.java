package io.company.brewcraft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.CreateGroupRequest;
import com.amazonaws.services.cognitoidp.model.CreateGroupResult;
import com.amazonaws.services.cognitoidp.model.DeleteGroupRequest;
import com.amazonaws.services.cognitoidp.model.DeleteGroupResult;
import com.amazonaws.services.cognitoidp.model.GetGroupRequest;
import com.amazonaws.services.cognitoidp.model.GetGroupResult;
import com.amazonaws.services.cognitoidp.model.GroupType;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;
import com.amazonaws.services.cognitoidp.model.UpdateGroupRequest;
import com.amazonaws.services.cognitoidp.model.UpdateGroupResult;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.service.AwsArnMapper;
import io.company.brewcraft.service.IaasClient;
import io.company.brewcraft.service.IaasEntityMapper;
import io.company.brewcraft.service.IaasRoleAccessor;
import io.company.brewcraft.service.IaasRoleService;

public class AwsIdpTenantWithRoleClient implements IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> {
    private static final Logger log = LoggerFactory.getLogger(AwsIdpTenantWithRoleClient.class);

    private AWSCognitoIdentityProvider idp;
    private final String userPoolId;
    private IaasEntityMapper<GroupType, IaasIdpTenant> mapper;
    private AwsArnMapper arnMapper;

    private IaasRoleService roleService;

    public AwsIdpTenantWithRoleClient(AWSCognitoIdentityProvider idp, String userPoolId, IaasEntityMapper<GroupType, IaasIdpTenant> mapper, AwsArnMapper arnMapper, IaasRoleService roleService) {
        this.idp = idp;
        this.userPoolId = userPoolId;
        this.mapper = mapper;
        this.arnMapper = arnMapper;
        this.roleService = roleService;
    }

    @Override
    public IaasIdpTenant get(String id) {
        IaasIdpTenant tenant = null;

        GetGroupRequest request = new GetGroupRequest()
                                        .withGroupName(id)
                                        .withUserPoolId(userPoolId);

        try {
            GetGroupResult result = this.idp.getGroup(request);
            tenant = this.mapper.fromIaasEntity(result.getGroup());
            setRole(tenant, result.getGroup().getRoleArn());

        } catch (ResourceNotFoundException e) {
            log.error("Failed to fetch group: {}", id);
        }

        return  tenant;
    }

    @Override
    public <BE extends BaseIaasIdpTenant> IaasIdpTenant add(BE entity) {
        CreateGroupRequest request = new CreateGroupRequest()
                                         .withDescription(entity.getDescription())
                                         .withGroupName(entity.getName())
                                         .withRoleArn(roleArn(entity))
                                         .withUserPoolId(userPoolId);

        CreateGroupResult result = this.idp.createGroup(request);

        IaasIdpTenant tenant = mapper.fromIaasEntity(result.getGroup());
        setRole(tenant, result.getGroup().getRoleArn());

        return tenant;
    }

    @Override
    public <UE extends UpdateIaasIdpTenant> IaasIdpTenant put(UE entity) {
        if (exists(entity.getId())) {
            return update(entity);
        } else {
            return add(entity);
        }
    }

    @Override
    public boolean delete(String id) {
        boolean success = false;

        DeleteGroupRequest request = new DeleteGroupRequest()
                                         .withGroupName(id)
                                         .withUserPoolId(userPoolId);

        try {
            DeleteGroupResult result = this.idp.deleteGroup(request);
            success = true;
        } catch (ResourceNotFoundException e) {
            log.error("Failed to delete group with id: {}", id);
        }

        return success;
    }

    @Override
    public boolean exists(String id) {
        return this.get(id) != null;
    }

    public <UE extends UpdateIaasIdpTenant> IaasIdpTenant update(UE entity) {
        UpdateGroupRequest request = new UpdateGroupRequest()
                                         .withDescription(entity.getDescription())
                                         .withGroupName(entity.getName())
                                         .withRoleArn(roleArn(entity))
                                         .withUserPoolId(userPoolId);

        UpdateGroupResult result = this.idp.updateGroup(request);

        IaasIdpTenant tenant = mapper.fromIaasEntity(result.getGroup());
        setRole(tenant, result.getGroup().getRoleArn());

        return tenant;
    }

    private String roleArn(IaasRoleAccessor accessor) {
        String roleArn = null;
        IaasRole role = accessor.getIaasRole();
        if (role != null && role.getName() != null) {
            roleArn = this.arnMapper.getRoleArn(role.getName());
        }

        return roleArn;
    }

    private void setRole(IaasRoleAccessor accessor, String roleArn) {
        String roleName = this.arnMapper.getName(roleArn);
        IaasRole role = this.roleService.get(roleName);
        accessor.setIaasRole(role);
    }
}
