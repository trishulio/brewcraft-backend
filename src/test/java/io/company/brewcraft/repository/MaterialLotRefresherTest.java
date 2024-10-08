package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.InvoiceItemRefresher;
import io.company.brewcraft.repository.MaterialLotRefresher;
import io.company.brewcraft.repository.StorageRefresher;
import io.company.brewcraft.service.MaterialLotAccessor;

public class MaterialLotRefresherTest {
    private MaterialLotRefresher materialLotRefresher;

    private InvoiceItemRefresher mItemRefresher;
    private StorageRefresher mStorageRefresher;

    private AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mItemRefresher =  mock(InvoiceItemRefresher.class);
        mStorageRefresher = mock(StorageRefresher.class);
        mRefresher = mock(AccessorRefresher.class);

        materialLotRefresher = new MaterialLotRefresher(mItemRefresher, mStorageRefresher, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MaterialLot> lots = List.of(new MaterialLot(1L), new MaterialLot(2L));

        materialLotRefresher.refresh(lots);

        verify(mItemRefresher, times(1)).refreshAccessors(lots);
        verify(mStorageRefresher, times(1)).refreshAccessors(lots);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<MaterialLotAccessor> accessors = List.of(mock(MaterialLotAccessor.class), mock(MaterialLotAccessor.class));

        materialLotRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
