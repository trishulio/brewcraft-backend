package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.repository.user.impl.UserRoleBindingRefresher;

public class UserRoleBindingRefresherTest {

    private UserRoleRepository mRoleRepo;

    private UserRoleBindingRefresher userRoleBindingRefresher;

    @BeforeEach
    public void init() {
        mRoleRepo = mock(UserRoleRepository.class);
<<<<<<< HEAD:src/test/java/io/company/brewcraft/repository/user/EnhancedUserRoleBindingRepositoryImplTest.java
        repo = new EnhancedUserRoleBindingRepositoryImpl( mRoleRepo);
=======
        userRoleBindingRefresher = new UserRoleBindingRefresher(mUserRepo, mRoleRepo);
>>>>>>> 8dc54b6 (Rename EnhancedRepos to Refreshers):src/test/java/io/company/brewcraft/repository/user/UserRoleBindingRefresherTest.java
    }

    @Test
    public void testRefresh_RefreshersChildEntities() {
        List<UserRoleBinding> bindings = List.of(new UserRoleBinding(1L), new UserRoleBinding(2L));
        userRoleBindingRefresher.refresh(bindings);

        verify(mRoleRepo, times(1)).refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));
    }

    @Test
    public void testRefreshRoles_RefreshesRoles() {
        List<UserRoleBinding> bindings = List.of(new UserRoleBinding(1L), new UserRoleBinding(2L));
        userRoleBindingRefresher.refresh(bindings);

        verify(mRoleRepo, times(1)).refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));
    }
}