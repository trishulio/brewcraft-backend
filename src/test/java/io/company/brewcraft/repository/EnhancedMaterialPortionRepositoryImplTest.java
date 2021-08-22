package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.service.MaterialPortionAccessor;

public class EnhancedMaterialPortionRepositoryImplTest {

    private EnhancedMaterialPortionRepository repo;

    private MixtureRepository mixtureRepositoryMock;

    private MaterialLotRepository materialLotRepositoryMock;
    
    private AccessorRefresher<Long, MaterialPortionAccessor, MaterialPortion> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mixtureRepositoryMock = mock(MixtureRepository.class);
        materialLotRepositoryMock = mock(MaterialLotRepository.class);
        refresherMock = mock(AccessorRefresher.class);

        repo = new EnhancedMaterialPortionRepositoryImpl(mixtureRepositoryMock, materialLotRepositoryMock, refresherMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MaterialPortion> materialPortions = List.of(new MaterialPortion(1L));

        repo.refresh(materialPortions);

        verify(mixtureRepositoryMock, times(1)).refreshAccessors(materialPortions);
        verify(materialLotRepositoryMock, times(1)).refreshAccessors(materialPortions);
    }
    
    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<MaterialPortionAccessor> accessors = List.of(mock(MaterialPortionAccessor.class), mock(MaterialPortionAccessor.class));

        repo.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }

}
