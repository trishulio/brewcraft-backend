package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MixtureRecording;

public class EnhancedMixtureRecordingRepositoryImplTest {

    private EnhancedMixtureRecordingRepository repo;
    
    private ProductMeasureRepository productMeasureRepositoryMock;
    
    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
    	productMeasureRepositoryMock = mock(ProductMeasureRepository.class);

        repo = new EnhancedMixtureRecordingRepositoryImpl(productMeasureRepositoryMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<MixtureRecording> mixtureRecordings = List.of(new MixtureRecording(1L));

        repo.refresh(mixtureRecordings);

        verify(productMeasureRepositoryMock, times(1)).refreshAccessors(mixtureRecordings);
    }

}
