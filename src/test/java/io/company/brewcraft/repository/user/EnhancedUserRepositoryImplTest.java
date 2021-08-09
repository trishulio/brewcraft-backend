package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.impl.EnhancedUserRepositoryImpl;

public class EnhancedUserRepositoryImplTest {

    private AccessorRefresher<Long, UserAccessor, User> mRefresher;
    private UserStatusRepository mStatusRepo;
    private UserSalutationRepository mSalutationRepo;
    private UserRoleBindingRepository mRoleBindingRepo;

    private EnhancedUserRepository repo;

    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);
        mStatusRepo = mock(UserStatusRepository.class);
        mSalutationRepo = mock(UserSalutationRepository.class);
        mRoleBindingRepo = mock(UserRoleBindingRepository.class);
        repo = new EnhancedUserRepositoryImpl(mRefresher, mStatusRepo, mSalutationRepo, mRoleBindingRepo);
    }

    @Test
    public void testRefresh_RefreshedChildEntitiesAndBindings() {
        List<User> users = List.of(
            new User(1L),
            new User(2L)
        );

        users.get(0).setRoles(List.of(new UserRole(10L)));
        users.get(1).setRoles(List.of(new UserRole(20L)));

        repo.refresh(users);

        List<UserRoleBinding> expected = List.of(
            new UserRoleBinding(null, new UserRole(10L), users.get(0)),
            new UserRoleBinding(null, new UserRole(20L), users.get(1))
        );
        verify(mRoleBindingRepo, times(1)).refreshRoles(expected);

        verify(mStatusRepo, times(1)).refreshAccessors(users);
        verify(mSalutationRepo, times(1)).refreshAccessors(users);
    }

    @Test
    public void testRefreshAccessors_CallsAccessorRefresher() {
        UserAccessor accessor = mock(UserAccessor.class);
        repo.refreshAccessors(List.of(accessor));

        verify(mRefresher, times(1)).refreshAccessors(List.of(accessor));
    }
}