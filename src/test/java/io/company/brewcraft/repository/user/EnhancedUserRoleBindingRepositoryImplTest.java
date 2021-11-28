package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.repository.user.impl.EnhancedUserRoleBindingRepositoryImpl;

public class EnhancedUserRoleBindingRepositoryImplTest {

    private UserRoleRepository mRoleRepo;

    private EnhancedUserRoleBindingRepository repo;

    @BeforeEach
    public void init() {
        mRoleRepo = mock(UserRoleRepository.class);
        repo = new EnhancedUserRoleBindingRepositoryImpl( mRoleRepo);
    }

    @Test
    public void testRefresh_RefreshersChildEntities() {
        List<UserRoleBinding> bindings = List.of(new UserRoleBinding(1L), new UserRoleBinding(2L));
        repo.refresh(bindings);

        verify(mRoleRepo, times(1)).refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));
    }

    @Test
    public void testRefreshRoles_RefreshesRoles() {
        List<UserRoleBinding> bindings = List.of(new UserRoleBinding(1L), new UserRoleBinding(2L));
        repo.refresh(bindings);

        verify(mRoleRepo, times(1)).refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));
    }
}