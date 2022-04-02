package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseIaasUser;
import io.company.brewcraft.model.BaseIaasUserTenantMembership;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.model.IaasUserTenantMembership;
import io.company.brewcraft.model.IaasUserTenantMembershipId;
import io.company.brewcraft.model.UpdateIaasUser;
import io.company.brewcraft.model.UpdateIaasUserTenantMembership;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.TenantContext;
import io.company.brewcraft.service.mapper.TenantIaasIdpTenantMapper;

public class TenantIaasUserService {
    private static final Logger log = LoggerFactory.getLogger(TenantIaasUserService.class);

    private IaasRepository<String, IaasUser, BaseIaasUser, UpdateIaasUser> userService;
    private IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> membershipService;

    private TenantIaasUserMapper userMapper;
    private TenantIaasIdpTenantMapper tenantMapper;

    private ContextHolder ctxHolder;

    public TenantIaasUserService(
        IaasRepository<String, IaasUser, BaseIaasUser, UpdateIaasUser> userService,
        IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> membershipService,
        TenantIaasUserMapper userMapper,
        TenantIaasIdpTenantMapper tenantMapper,
        ContextHolder ctxHolder
    ) {
        this.userService = userService;
        this.membershipService = membershipService;
        this.userMapper = userMapper;
        this.tenantMapper = tenantMapper;
        this.ctxHolder = ctxHolder;
    }

    public List<IaasUserTenantMembership> put(List<User> users) {
        TenantContext ctx = this.ctxHolder.getTenantContext();
        IaasIdpTenant tenant = tenantMapper.fromTenant(ctx.getTenant());

        List<UpdateIaasUser> updates = userMapper.fromUsers(users);
        List<IaasUser> iaasUsers = this.userService.put(updates);

        List<IaasUserTenantMembership> memberships = iaasUsers.stream().map(iaasUser -> new IaasUserTenantMembership(iaasUser, tenant)).toList();
        return this.membershipService.put(memberships);
    }

    public long delete(List<User> users) {
        String tenantId = this.ctxHolder.getPrincipalContext().getTenantId().toString();

        List<IaasUser> iaasUsers = userMapper.fromUsers(users);
        Set<String> userIds = iaasUsers.stream().map(IaasUser::getId).collect(Collectors.toSet());
        Set<IaasUserTenantMembershipId> membershipIds = userIds.stream().map(userId -> new IaasUserTenantMembershipId(userId, tenantId)).collect(Collectors.toSet());

        long membershipCount = this.membershipService.delete(membershipIds);

        long userCount = this.userService.delete(userIds);

        log.info("Deleted user memberships: {}; users: {}", membershipCount, userCount);

        return userCount;
    }
}
