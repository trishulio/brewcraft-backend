package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserSalutationAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.impl.EnhancedUserSalutationRepositoryImpl;

public class EnhancedUserSalutationRepositoryTest {
    private AccessorRefresher<Long, UserSalutationAccessor, UserSalutation> mRefresher;

    private EnhancedUserSalutationRepository repo;
    
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedUserSalutationRepositoryImpl(mRefresher);
    }
    
    @Test
    public void testRefresh_DoesNothing() {
        repo.refresh(null);
    }
    
    @Test
    public void testRefreshAccessors_CallsAccessorRefresher() {
        UserSalutationAccessor accessor = mock(UserSalutationAccessor.class);
        List<UserSalutationAccessor> accessors = List.of(accessor);

        repo.refreshAccessors(accessors);
        
        verify(mRefresher, times(1)).refreshAccessors(List.of(accessor));
    }
}
