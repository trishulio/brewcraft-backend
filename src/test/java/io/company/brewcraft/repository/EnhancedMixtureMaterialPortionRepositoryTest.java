package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.service.MixtureMaterialPortionAccessor;

public class EnhancedMixtureMaterialPortionRepositoryTest {

    private EnhancedMixtureMaterialPortionRepository repo;

    private MixtureRepository mixtureRepositoryMock;

    private MaterialLotRepository materialLotRepositoryMock;

    private AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mixtureRepositoryMock = mock(MixtureRepository.class);
        materialLotRepositoryMock = mock(MaterialLotRepository.class);
        refresherMock = mock(AccessorRefresher.class);

        repo = new EnhancedMixtureMaterialPortionRepositoryImpl(mixtureRepositoryMock, materialLotRepositoryMock, refresherMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MixtureMaterialPortion> materialPortions = List.of(new MixtureMaterialPortion(1L));

        repo.refresh(materialPortions);

        verify(mixtureRepositoryMock, times(1)).refreshAccessors(materialPortions);
        verify(materialLotRepositoryMock, times(1)).refreshAccessors(materialPortions);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<MixtureMaterialPortionAccessor> accessors = List.of(mock(MixtureMaterialPortionAccessor.class), mock(MixtureMaterialPortionAccessor.class));

        repo.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }

}
