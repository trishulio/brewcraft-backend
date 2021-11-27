package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.StorageAccessor;

public class StorageRefresherTest {

    private StorageRefresher storageRefresher;

    private AccessorRefresher<Long, StorageAccessor, Storage> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        storageRefresher = new StorageRefresher(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<StorageAccessor> accessors = List.of(mock(StorageAccessor.class), mock(StorageAccessor.class));

        storageRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
