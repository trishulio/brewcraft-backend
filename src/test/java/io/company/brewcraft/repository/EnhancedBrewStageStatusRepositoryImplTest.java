package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.service.BrewStageStatusAccessor;

public class EnhancedBrewStageStatusRepositoryImplTest {
    private EnhancedBrewStageStatusRepository brewStageStatusRepository;

    private AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        refresherMock = mock(AccessorRefresher.class);

        brewStageStatusRepository = new EnhancedBrewStageStatusRepositoryImpl(refresherMock);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<BrewStageStatusAccessor> accessors = List.of(mock(BrewStageStatusAccessor.class), mock(BrewStageStatusAccessor.class));

        brewStageStatusRepository.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }
}
