package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentMixtureAccessor;

public class EnhancedMixtureRepositoryImplTest {
    private EnhancedMixtureRepository repo;

    private MixtureRepository mixtureRepositoryMock;

    private EquipmentRepository equipmentRepositoryMock;

    private BrewStageRepository brewStageRepositoryMock;

    private AccessorRefresher<Long, ParentMixtureAccessor, Mixture> parentMixtureAccessorMock;

    private AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureAccessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mixtureRepositoryMock = mock(MixtureRepository.class);
        equipmentRepositoryMock = mock(EquipmentRepository.class);
        brewStageRepositoryMock = mock(BrewStageRepository.class);
        parentMixtureAccessorMock = mock(AccessorRefresher.class);
        mixtureAccessorMock = mock(AccessorRefresher.class);

        repo = new EnhancedMixtureRepositoryImpl(mixtureRepositoryMock, equipmentRepositoryMock, brewStageRepositoryMock, parentMixtureAccessorMock, mixtureAccessorMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Mixture> mixtures = List.of(new Mixture(1L));

        repo.refresh(mixtures);

        verify(mixtureRepositoryMock, times(1)).refreshParentMixtureAccessors(mixtures);
        verify(equipmentRepositoryMock, times(1)).refreshAccessors(mixtures);
        verify(brewStageRepositoryMock, times(1)).refreshAccessors(mixtures);
    }

    @Test
    public void testRefreshParentMixtureAccessors_CallsRefresherAccessor() {
        List<ParentMixtureAccessor> accessors = List.of(mock(ParentMixtureAccessor.class), mock(ParentMixtureAccessor.class));

        repo.refreshParentMixtureAccessors(accessors);

        verify(parentMixtureAccessorMock, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<MixtureAccessor> accessors = List.of(mock(MixtureAccessor.class), mock(MixtureAccessor.class));

        repo.refreshAccessors(accessors);

        verify(mixtureAccessorMock, times(1)).refreshAccessors(accessors);
    }

}
