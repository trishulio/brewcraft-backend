package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.repository.user.impl.EnhancedUserRoleBindingRepositoryImpl;

public class EnhancedUserRoleBindingRepositoryImplTest {

    private UserRepository mUserRepo;
    private UserRoleRepository mRoleRepo;
    
    private EnhancedUserRoleBindingRepository repo;
    
    @BeforeEach
    public void init() {
        mUserRepo = mock(UserRepository.class);
        mRoleRepo = mock(UserRoleRepository.class);
        repo = new EnhancedUserRoleBindingRepositoryImpl(mUserRepo, mRoleRepo);
    }
    
    @Test
    public void testRefresh_RefreshersChildEntities() {
        List<UserRoleBinding> bindings = List.of(new UserRoleBinding(1L), new UserRoleBinding(2L));
        repo.refresh(bindings);
        
        verify(mUserRepo, times(1)).refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));
        verify(mRoleRepo, times(1)).refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));
    }

    @Test
    public void testRefreshRoles_RefreshesRoleChildEntities() {
        List<UserRoleBinding> bindings = List.of(new UserRoleBinding(1L), new UserRoleBinding(2L));
        repo.refresh(bindings);

        verifyNoInteractions(mUserRepo);
        verify(mRoleRepo, times(1)).refreshAccessors(List.of(new UserRoleBinding(1L), new UserRoleBinding(2L)));        
    }
}
