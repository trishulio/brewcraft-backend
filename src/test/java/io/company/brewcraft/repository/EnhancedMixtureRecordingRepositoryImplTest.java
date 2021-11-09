package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.service.MixtureRecordingAccessor;

public class EnhancedMixtureRecordingRepositoryImplTest {

    private EnhancedMixtureRecordingRepository repo;

    private MeasureRepository measureRepositoryMock;

    private MixtureRepository mixtureRepositoryMock;
    
    private AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        measureRepositoryMock = mock(MeasureRepository.class);
        mixtureRepositoryMock = mock(MixtureRepository.class);
        refresherMock = mock(AccessorRefresher.class);

        repo = new EnhancedMixtureRecordingRepositoryImpl(measureRepositoryMock, mixtureRepositoryMock, refresherMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MixtureRecording> mixtureRecordings = List.of(new MixtureRecording(1L));

        repo.refresh(mixtureRecordings);

        verify(measureRepositoryMock, times(1)).refreshAccessors(mixtureRecordings);
        verify(mixtureRepositoryMock, times(1)).refreshAccessors(mixtureRecordings);
    }

}
