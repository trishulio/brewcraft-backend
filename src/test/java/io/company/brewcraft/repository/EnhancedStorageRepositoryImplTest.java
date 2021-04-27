package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.StorageAccessor;

public class EnhancedStorageRepositoryImplTest {

    private EnhancedStorageRepository repo;

    private AccessorRefresher<Long, StorageAccessor, Storage> mRefresher;

    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedStorageRepositoryImpl(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<StorageAccessor> accessors = List.of(mock(StorageAccessor.class), mock(StorageAccessor.class));

        repo.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
