package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.service.BrewStageAccessor;

public class EnhancedBrewStageRepositoryImplTest {
    private EnhancedBrewStageRepository brewStageRepository;

    private BrewTaskRepository brewTaskRepositoryMock;
    
    private BrewStageStatusRepository brewStageStatusRepositoryMock;
    
    private BrewRepository brewRepositoryMock;
    
    private AccessorRefresher<Long, BrewStageAccessor, BrewStage> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
    	brewTaskRepositoryMock = mock(BrewTaskRepository.class);
    	brewStageStatusRepositoryMock = mock(BrewStageStatusRepository.class);
    	brewRepositoryMock = mock(BrewRepository.class);
        refresherMock = mock(AccessorRefresher.class);

        brewStageRepository = new EnhancedBrewStageRepositoryImpl(brewTaskRepositoryMock, brewStageStatusRepositoryMock, brewRepositoryMock, refresherMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<BrewStage> brewStages = List.of(new BrewStage(1L));

        brewStageRepository.refresh(brewStages);

        verify(brewTaskRepositoryMock, times(1)).refreshAccessors(brewStages);
        verify(brewStageStatusRepositoryMock, times(1)).refreshAccessors(brewStages);
        verify(brewRepositoryMock, times(1)).refreshAccessors(brewStages);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<BrewStageAccessor> accessors = List.of(mock(BrewStageAccessor.class), mock(BrewStageAccessor.class));

        brewStageRepository.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }

}
