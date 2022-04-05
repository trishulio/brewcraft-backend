package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseIaasUser;
import io.company.brewcraft.model.BaseIaasUserTenantMembership;
import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.model.IaasUserTenantMembership;
import io.company.brewcraft.model.IaasUserTenantMembershipId;
import io.company.brewcraft.model.UpdateIaasUser;
import io.company.brewcraft.model.UpdateIaasUserTenantMembership;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;
import io.company.brewcraft.service.mapper.TenantIaasIdpTenantMapper;

public class TenantIaasUserServiceTest {
    private TenantIaasUserService service;

    private IaasRepository<String, IaasUser, BaseIaasUser, UpdateIaasUser> mUserService;
    private IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> mMembershipService;
    private ContextHolder mCtxHolder;

    @BeforeEach
    public void init() {
        mUserService = mock(IaasRepository.class);
        mMembershipService = mock(IaasRepository.class);
        mCtxHolder = mock(ContextHolder.class);

        service = new TenantIaasUserService(mUserService, mMembershipService, TenantIaasUserMapper.INSTANCE, TenantIaasIdpTenantMapper .INSTANCE, mCtxHolder);
    }

    @Test
    public void testPut_ReturnsMembershipsAfterSavingUsersAndCreatingMemberships() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mUserService).put(anyList());
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mMembershipService).put(anyList());

        PrincipalContext ctx = mock(PrincipalContext.class);
        doReturn(ctx).when(mCtxHolder).getPrincipalContext();
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001")).when(ctx).getTenantId();

        List<User> users = List.of(
            new User(1L, "USERNAME_1", null, null, null, "example-1@localhost", "phone-number-1", null, null, null, null, LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), null),
            new User(2L, "USERNAME_2", null, null, null, "example-2@localhost", "phone-number-2", null, null, null, null, LocalDateTime.of(2001, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), null)
        );

        List<IaasUserTenantMembership> memberships = service.put(users);

        List<IaasUserTenantMembership> expected = List.of(
            new IaasUserTenantMembership(new IaasUser("USERNAME_1", "example-1@localhost", "phone-number-1"), "00000000-0000-0000-0000-000000000001"),
            new IaasUserTenantMembership(new IaasUser("USERNAME_2", "example-2@localhost", "phone-number-2"), "00000000-0000-0000-0000-000000000001")
        );

        assertEquals(expected, memberships);
    }

    @Test
    public void testDelete_RemovesMembershipAndDeletesUsers() {
        doReturn(55L).when(mMembershipService).delete(Set.of(new IaasUserTenantMembershipId("USERNAME_1", "00000000-0000-0000-0000-000000000001"), new IaasUserTenantMembershipId("USERNAME_2", "00000000-0000-0000-0000-000000000001")));
        doReturn(55L).when(mUserService).delete(Set.of("USERNAME_1", "USERNAME_2"));

        PrincipalContext ctx = mock(PrincipalContext.class);
        doReturn(ctx).when(mCtxHolder).getPrincipalContext();
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001")).when(ctx).getTenantId();

        List<User> users = List.of(
            new User(1L, "USERNAME_1", null, null, null, "example-1@localhost", "phone-number-1", null, null, null, null, LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), null),
            new User(2L, "USERNAME_2", null, null, null, "example-2@localhost", "phone-number-2", null, null, null, null, LocalDateTime.of(2001, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), null)
        );

        long count = service.delete(users);

        assertEquals(55L, count);
    }
}
