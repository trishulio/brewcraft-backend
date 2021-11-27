package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.MaterialLotAccessor;

public class MaterialLotRefresherTest {

    private MaterialLotRefresher materialLotRefresher;

    private InvoiceItemRepository mItemRepo;
    private StorageRepository mStorageRepo;

    private AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mItemRepo =  mock(InvoiceItemRepository.class);
        mStorageRepo = mock(StorageRepository.class);
        mRefresher = mock(AccessorRefresher.class);

        materialLotRefresher = new MaterialLotRefresher(mItemRepo, mStorageRepo, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MaterialLot> lots = List.of(new MaterialLot(1L), new MaterialLot(2L));

        materialLotRefresher.refresh(lots);

        verify(mItemRepo, times(1)).refreshAccessors(lots);
        verify(mStorageRepo, times(1)).refreshAccessors(lots);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<MaterialLotAccessor> accessors = List.of(mock(MaterialLotAccessor.class), mock(MaterialLotAccessor.class));

        materialLotRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
