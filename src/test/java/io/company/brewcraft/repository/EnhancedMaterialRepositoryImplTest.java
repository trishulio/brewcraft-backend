package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Material;
import io.company.brewcraft.service.MaterialAccessor;

public class EnhancedMaterialRepositoryImplTest {

    private EnhancedMaterialRepository repo;

    private AccessorRefresher<Long, MaterialAccessor, Material> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedMaterialRepositoryImpl(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<MaterialAccessor> accessors = List.of(mock(MaterialAccessor.class), mock(MaterialAccessor.class));
        
        repo.refreshAccessors(accessors);
        
        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
