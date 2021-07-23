package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.ParentMixtureAccessor;

public class EnhancedMixtureRepositoryImplTest {
    private EnhancedMixtureRepository repo;

    private MixtureRepository mixtureRepositoryMock;
    
    private EquipmentRepository equipmentRepositoryMock;
    
    private BrewStageRepository brewStageRepositoryMock;
    
    private AccessorRefresher<Long, ParentMixtureAccessor, Mixture> accessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
    	mixtureRepositoryMock = mock(MixtureRepository.class);
    	equipmentRepositoryMock = mock(EquipmentRepository.class);
    	brewStageRepositoryMock = mock(BrewStageRepository.class);
        accessorMock = mock(AccessorRefresher.class);

        repo = new EnhancedMixtureRepositoryImpl(mixtureRepositoryMock, equipmentRepositoryMock, brewStageRepositoryMock, accessorMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Mixture> mixtures = List.of(new Mixture(1L));

        repo.refresh(mixtures);

        verify(mixtureRepositoryMock, times(1)).refreshAccessors(mixtures);
        verify(equipmentRepositoryMock, times(1)).refreshAccessors(mixtures);
        verify(brewStageRepositoryMock, times(1)).refreshAccessors(mixtures);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<ParentMixtureAccessor> accessors = List.of(mock(ParentMixtureAccessor.class), mock(ParentMixtureAccessor.class));

        repo.refreshAccessors(accessors);

        verify(accessorMock, times(1)).refreshAccessors(accessors);
    }

}
