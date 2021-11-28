package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.BrewTaskRefresher;
import io.company.brewcraft.service.BrewTaskAccessor;

public class BrewTaskRefresherTest {
    private BrewTaskRefresher brewTaskRefresher;

    private AccessorRefresher<Long, BrewTaskAccessor, BrewTask> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        refresherMock = mock(AccessorRefresher.class);

        brewTaskRefresher = new BrewTaskRefresher(refresherMock);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<BrewTaskAccessor> accessors = List.of(mock(BrewTaskAccessor.class), mock(BrewTaskAccessor.class));

        brewTaskRefresher.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }
}
