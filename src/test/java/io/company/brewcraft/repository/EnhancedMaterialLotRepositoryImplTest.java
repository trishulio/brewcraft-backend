package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.MaterialLotAccessor;

public class EnhancedMaterialLotRepositoryImplTest {

    private EnhancedMaterialLotRepository repo;

    private InvoiceItemRepository mItemRepo;
    private MaterialRepository mMaterialRepo;
    private StorageRepository mStorageRepo;

    private AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mItemRepo =  mock(InvoiceItemRepository.class);
        mMaterialRepo = mock(MaterialRepository.class);
        mStorageRepo = mock(StorageRepository.class);
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedMaterialLotRepositoryImpl(mItemRepo, mMaterialRepo, mStorageRepo, mRefresher);
    }
    
    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MaterialLot> lots = List.of(new MaterialLot(1L), new MaterialLot(2L));

        repo.refresh(lots);

        verify(mItemRepo, times(1)).refreshAccessors(lots);
        verify(mMaterialRepo, times(1)).refreshAccessors(lots);
        verify(mStorageRepo, times(1)).refreshAccessors(lots);
    }
    
    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<MaterialLotAccessor> accessors = List.of(mock(MaterialLotAccessor.class), mock(MaterialLotAccessor.class));
        
        repo.refreshAccessors(accessors);
        
        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
