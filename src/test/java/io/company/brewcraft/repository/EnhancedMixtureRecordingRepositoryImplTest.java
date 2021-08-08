package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MixtureRecording;

public class EnhancedMixtureRecordingRepositoryImplTest {

    private EnhancedMixtureRecordingRepository repo;
    
    private MeasureRepository measureRepositoryMock;
    
    private MixtureRepository mixtureRepositoryMock;
    
    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
    	measureRepositoryMock = mock(MeasureRepository.class);
    	mixtureRepositoryMock = mock(MixtureRepository.class);

        repo = new EnhancedMixtureRecordingRepositoryImpl(measureRepositoryMock, mixtureRepositoryMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MixtureRecording> mixtureRecordings = List.of(new MixtureRecording(1L));

        repo.refresh(mixtureRecordings);

        verify(measureRepositoryMock, times(1)).refreshAccessors(mixtureRecordings);
        verify(mixtureRepositoryMock, times(1)).refreshAccessors(mixtureRecordings);
    }

}
