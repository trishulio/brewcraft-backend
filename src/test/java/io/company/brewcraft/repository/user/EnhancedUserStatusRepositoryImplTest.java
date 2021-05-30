package io.company.brewcraft.repository.user;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserStatusAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.impl.EnhancedUserStatusRepositoryImpl;

public class EnhancedUserStatusRepositoryImplTest {

    private AccessorRefresher<Long, UserStatusAccessor, UserStatus> mRefresher;

    private EnhancedUserStatusRepository repo;
    
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedUserStatusRepositoryImpl(mRefresher);
    }
    
    @Test
    public void testRefresh_DoesNothing() {
        repo.refresh(null);
    }
    
    @Test
    public void testRefreshAccessors_CallsAccessorRefresher() {
        UserStatusAccessor accessor = mock(UserStatusAccessor.class);
        List<UserStatusAccessor> accessors = List.of(accessor);

        repo.refreshAccessors(accessors);
        
        verify(mRefresher, times(1)).refreshAccessors(List.of(accessor));
    }
}
