package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.impl.EnhancedUserRoleRepositoryImpl;

public class EnhancedUserRoleRepositoryImplTest {

    private AccessorRefresher<Long, UserRoleAccessor, UserRole> refresher;

    private EnhancedUserRoleRepository repo;

    @BeforeEach
    public void init() {
        refresher = mock(AccessorRefresher.class);
        repo = new EnhancedUserRoleRepositoryImpl(refresher);
    }

    @Test
    public void testRefresh_DoesNothing() {
        repo.refresh(null);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        UserRoleAccessor accessor = mock(UserRoleAccessor.class);
        List<UserRoleAccessor> accessors = List.of(accessor);
        repo.refreshAccessors(accessors);

        verify(refresher, times(1)).refreshAccessors(List.of(accessor));
    }
}
