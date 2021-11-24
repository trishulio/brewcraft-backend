package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentMixturesAccessor;

public class EnhancedMixtureRepositoryImplTest {
    private EnhancedMixtureRepository repo;

    private MixtureRepository mixtureRepositoryMock;

    private EquipmentRepository equipmentRepositoryMock;

    private BrewStageRepository brewStageRepositoryMock;

    private AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureAccessorMock;

    private CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixturesAccessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mixtureRepositoryMock = mock(MixtureRepository.class);
        equipmentRepositoryMock = mock(EquipmentRepository.class);
        brewStageRepositoryMock = mock(BrewStageRepository.class);
        mixtureAccessorMock = mock(AccessorRefresher.class);
        parentMixturesAccessorMock = mock(CollectionAccessorRefresher.class);

        repo = new EnhancedMixtureRepositoryImpl(mixtureRepositoryMock, equipmentRepositoryMock, brewStageRepositoryMock, mixtureAccessorMock, parentMixturesAccessorMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Mixture> mixtures = List.of(new Mixture(1L));

        List<Mixture> parentMixtures = List.of(new Mixture(2L));
        mixtures.get(0).setParentMixtures(parentMixtures);

        repo.refresh(mixtures);

        verify(equipmentRepositoryMock, times(1)).refreshAccessors(mixtures);
        verify(brewStageRepositoryMock, times(1)).refreshAccessors(mixtures);
        verify(mixtureRepositoryMock, times(1)).refreshParentMixturesAccessors(mixtures);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<MixtureAccessor> accessors = List.of(mock(MixtureAccessor.class), mock(MixtureAccessor.class));

        repo.refreshAccessors(accessors);

        verify(mixtureAccessorMock, times(1)).refreshAccessors(accessors);
    }

}
