package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.impl.UserRoleRefresher;

public class UserRoleRefresherTest {
    private AccessorRefresher<Long, UserRoleAccessor, UserRole> refresher;

    private UserRoleRefresher userRoleRefresher;

    @BeforeEach
    public void init() {
        refresher = mock(AccessorRefresher.class);
        userRoleRefresher = new UserRoleRefresher(refresher);
    }

    @Test
    public void testRefresh_DoesNothing() {
        userRoleRefresher.refresh(null);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        UserRoleAccessor accessor = mock(UserRoleAccessor.class);
        List<UserRoleAccessor> accessors = List.of(accessor);
        userRoleRefresher.refreshAccessors(accessors);

        verify(refresher, times(1)).refreshAccessors(List.of(accessor));
    }
}
