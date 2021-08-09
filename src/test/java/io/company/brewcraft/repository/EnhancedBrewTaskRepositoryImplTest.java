package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.service.BrewTaskAccessor;

public class EnhancedBrewTaskRepositoryImplTest {
    private EnhancedBrewTaskRepository brewTaskRepository;

    private AccessorRefresher<Long, BrewTaskAccessor, BrewTask> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        refresherMock = mock(AccessorRefresher.class);

        brewTaskRepository = new EnhancedBrewTaskRepositoryImpl(refresherMock);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<BrewTaskAccessor> accessors = List.of(mock(BrewTaskAccessor.class), mock(BrewTaskAccessor.class));

        brewTaskRepository.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }
}
