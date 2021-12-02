package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.model.user.UserRoleBindingAccessor;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserSalutationAccessor;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserStatusAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.repository.user.impl.UserRefresher;
import io.company.brewcraft.repository.user.impl.UserRoleBindingRefresher;
import io.company.brewcraft.repository.user.impl.UserSalutationRefresher;
import io.company.brewcraft.repository.user.impl.UserStatusRefresher;

public class UserRefresherTest {

    private AccessorRefresher<Long, UserAccessor, User> mRefresher;
    private Refresher<UserStatus, UserStatusAccessor> mStatusRefresher;
    private Refresher<UserSalutation, UserSalutationAccessor> mSalutationRefresher;
    private Refresher<UserRoleBinding, UserRoleBindingAccessor> mRoleBindingRefresher;

    private UserRefresher userRefresher;

    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);
        mStatusRefresher = mock(Refresher.class);
        mSalutationRefresher = mock(Refresher.class);
        mRoleBindingRefresher = mock(Refresher.class);
        userRefresher = new UserRefresher(mRefresher, mStatusRefresher, mSalutationRefresher, mRoleBindingRefresher);
    }

    @Test
    public void testRefresh_RefreshedChildEntitiesAndBindings() {
        List<User> users = List.of(
            new User(1L),
            new User(2L)
        );

        users.get(0).setRoles(List.of(new UserRole(10L)));
        users.get(1).setRoles(List.of(new UserRole(20L)));

        userRefresher.refresh(users);

        List<UserRoleBinding> expected = List.of(
            new UserRoleBinding(null, new UserRole(10L), users.get(0)),
            new UserRoleBinding(null, new UserRole(20L), users.get(1))
        );
        verify(mRoleBindingRefresher, times(1)).refresh(expected);

        verify(mStatusRefresher, times(1)).refreshAccessors(users);
        verify(mSalutationRefresher, times(1)).refreshAccessors(users);
    }

    @Test
    public void testRefreshAccessors_CallsAccessorRefresher() {
        UserAccessor accessor = mock(UserAccessor.class);
        userRefresher.refreshAccessors(List.of(accessor));

        verify(mRefresher, times(1)).refreshAccessors(List.of(accessor));
    }
}