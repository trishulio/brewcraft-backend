package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.MeasureRefresher;
import io.company.brewcraft.repository.MixtureRecordingRefresher;
import io.company.brewcraft.repository.MixtureRefresher;
import io.company.brewcraft.service.MixtureRecordingAccessor;

public class MixtureRecordingRefresherTest {
    private MixtureRecordingRefresher mixtureRecordingRefresher;

    private MeasureRefresher measureRefresherMock;

    private MixtureRefresher mixtureRefresher;

    private AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        measureRefresherMock = mock(MeasureRefresher.class);
        mixtureRefresher = mock(MixtureRefresher.class);
        refresherMock = mock(AccessorRefresher.class);

        mixtureRecordingRefresher = new MixtureRecordingRefresher(measureRefresherMock, mixtureRefresher, refresherMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MixtureRecording> mixtureRecordings = List.of(new MixtureRecording(1L));

        mixtureRecordingRefresher.refresh(mixtureRecordings);

        verify(measureRefresherMock, times(1)).refreshAccessors(mixtureRecordings);
        verify(mixtureRefresher, times(1)).refreshAccessors(mixtureRecordings);
    }
}
