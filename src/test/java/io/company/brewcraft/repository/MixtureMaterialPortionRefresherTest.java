package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.MaterialLotRefresher;
import io.company.brewcraft.repository.MixtureMaterialPortionRefresher;
import io.company.brewcraft.repository.MixtureRefresher;
import io.company.brewcraft.service.MixtureMaterialPortionAccessor;

public class MixtureMaterialPortionRefresherTest {
    private MixtureMaterialPortionRefresher mixtureMaterialPortionRefresher;

    private MixtureRefresher mixtureRefresherMock;

    private MaterialLotRefresher materialLotRefresherMock;

    private AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mixtureRefresherMock = mock(MixtureRefresher.class);
        materialLotRefresherMock = mock(MaterialLotRefresher.class);
        refresherMock = mock(AccessorRefresher.class);

        mixtureMaterialPortionRefresher = new MixtureMaterialPortionRefresher(mixtureRefresherMock, materialLotRefresherMock, refresherMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MixtureMaterialPortion> materialPortions = List.of(new MixtureMaterialPortion(1L));

        mixtureMaterialPortionRefresher.refresh(materialPortions);

        verify(mixtureRefresherMock, times(1)).refreshAccessors(materialPortions);
        verify(materialLotRefresherMock, times(1)).refreshAccessors(materialPortions);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<MixtureMaterialPortionAccessor> accessors = List.of(mock(MixtureMaterialPortionAccessor.class), mock(MixtureMaterialPortionAccessor.class));

        mixtureMaterialPortionRefresher.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }
}
