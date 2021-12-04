package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.BrewRefresher;
import io.company.brewcraft.repository.BrewStageRefresher;
import io.company.brewcraft.repository.BrewStageStatusRefresher;
import io.company.brewcraft.repository.BrewTaskRefresher;
import io.company.brewcraft.service.BrewStageAccessor;

public class BrewStageRefresherTest {
    private BrewStageRefresher brewStsgeRefresher;

    private BrewTaskRefresher brewTaskRefresher;

    private BrewStageStatusRefresher brewStageStatusRefresher;

    private BrewRefresher brewRefresherMock;

    private AccessorRefresher<Long, BrewStageAccessor, BrewStage> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        brewTaskRefresher = mock(BrewTaskRefresher.class);
        brewStageStatusRefresher = mock(BrewStageStatusRefresher.class);
        brewRefresherMock = mock(BrewRefresher.class);
        refresherMock = mock(AccessorRefresher.class);

        brewStsgeRefresher = new BrewStageRefresher(brewTaskRefresher, brewStageStatusRefresher, brewRefresherMock, refresherMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<BrewStage> brewStages = List.of(new BrewStage(1L));

        brewStsgeRefresher.refresh(brewStages);

        verify(brewTaskRefresher, times(1)).refreshAccessors(brewStages);
        verify(brewStageStatusRefresher, times(1)).refreshAccessors(brewStages);
        verify(brewRefresherMock, times(1)).refreshAccessors(brewStages);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<BrewStageAccessor> accessors = List.of(mock(BrewStageAccessor.class), mock(BrewStageAccessor.class));

        brewStsgeRefresher.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }
}
