package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddMixtureRecordingDto;
import io.company.brewcraft.dto.MeasureDto;
import io.company.brewcraft.dto.MixtureRecordingDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateMixtureRecordingDto;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.service.MixtureRecordingService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;

public class MixtureRecordingControllerTest {

    private MixtureRecordingController mixtureRecordingController;

    private MixtureRecordingService mixtureRecordingService;

    @BeforeEach
    public void init() {
        mixtureRecordingService = mock(MixtureRecordingService.class);

        mixtureRecordingController = new MixtureRecordingController(mixtureRecordingService, new AttributeFilter());
    }

    @Test
    public void testGetMixtureRecordings() {
        MixtureRecording mixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(3L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        List<MixtureRecording> mixtureRecordingList = List.of(mixtureRecording);
        Page<MixtureRecording> mPage = mock(Page.class);
        doReturn(mixtureRecordingList.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mixtureRecordingService).getMixtureRecordings(null, null, 1, 10, new TreeSet<>(List.of("id")), true);

        PageDto<MixtureRecordingDto> dto = mixtureRecordingController.getMixtureRecordings(null, null, new TreeSet<>(List.of("id")), true, 1, 10);

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());
        assertEquals(1, dto.getContent().size());
        MixtureRecordingDto mixtureRecordingDto = dto.getContent().get(0);

        assertEquals(1L, mixtureRecordingDto.getId());
        assertEquals(new MeasureDto(3L), mixtureRecordingDto.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());
    }

    @Test
    public void testGetMixtureRecording() {
        MixtureRecording mixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(3L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(mixtureRecording).when(mixtureRecordingService).getMixtureRecording(1L);

        MixtureRecordingDto mixtureRecordingDto = mixtureRecordingController.getMixtureRecording(1L);

        assertEquals(1L, mixtureRecordingDto.getId());
        assertEquals(new MeasureDto(3L), mixtureRecordingDto.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());
    }

    @Test
    public void testGetMixtureRecording_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
        when(mixtureRecordingService.getMixtureRecording(1L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> mixtureRecordingController.getMixtureRecording(1L),
                "Mixture Recording not found with id: 1");
    }

    @Test
    public void testAddMixtureRecording() {
        AddMixtureRecordingDto addMixtureRecordingDto = new AddMixtureRecordingDto(2L, 1L, new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4));

        MixtureRecording mixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MixtureRecording> addMixtureRecordingCaptor = ArgumentCaptor.forClass(MixtureRecording.class);

        doReturn(mixtureRecording).when(mixtureRecordingService).addMixtureRecording(addMixtureRecordingCaptor.capture());

        MixtureRecordingDto mixtureRecordingDto = mixtureRecordingController.addMixtureRecording(addMixtureRecordingDto);

        // Assert added mixture recording
        assertEquals(null, addMixtureRecordingCaptor.getValue().getId());
        assertEquals(new Mixture(2L), addMixtureRecordingCaptor.getValue().getMixture());
        assertEquals(new Measure(1L), addMixtureRecordingCaptor.getValue().getMeasure());
        assertEquals(new BigDecimal("100"), addMixtureRecordingCaptor.getValue().getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addMixtureRecordingCaptor.getValue().getRecordedAt());
        assertEquals(null, addMixtureRecordingCaptor.getValue().getCreatedAt());
        assertEquals(null, addMixtureRecordingCaptor.getValue().getLastUpdated());
        assertEquals(null, addMixtureRecordingCaptor.getValue().getVersion());

        // Assert returned mixture recording
        assertEquals(1L, mixtureRecordingDto.getId());
        assertEquals(new MeasureDto(1L), mixtureRecordingDto.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());
    }

    @Test
    public void testPutMixtureRecording() {
        UpdateMixtureRecordingDto updateMixtureRecordingDto = new UpdateMixtureRecordingDto(2L, 1L, new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MixtureRecording mixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MixtureRecording> putMixtureRecordingCaptor = ArgumentCaptor.forClass(MixtureRecording.class);

        doReturn(mixtureRecording).when(mixtureRecordingService).putMixtureRecording(eq(1L), putMixtureRecordingCaptor.capture());

        MixtureRecordingDto mixtureRecordingDto = mixtureRecordingController.putMixtureRecording(updateMixtureRecordingDto, 1L);

        // Assert put mixture recording
        assertEquals(null, putMixtureRecordingCaptor.getValue().getId());
        assertEquals(new Mixture(2L), putMixtureRecordingCaptor.getValue().getMixture());
        assertEquals(new Measure(1L), putMixtureRecordingCaptor.getValue().getMeasure());
        assertEquals(new BigDecimal("100"), putMixtureRecordingCaptor.getValue().getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), putMixtureRecordingCaptor.getValue().getRecordedAt());
        assertEquals(null, putMixtureRecordingCaptor.getValue().getCreatedAt());
        assertEquals(null, putMixtureRecordingCaptor.getValue().getLastUpdated());
        assertEquals(1, putMixtureRecordingCaptor.getValue().getVersion());

        // Assert returned mixture recording
        assertEquals(1L, mixtureRecordingDto.getId());
        assertEquals(new MeasureDto(1L), mixtureRecordingDto.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());
    }

    @Test
    public void testPatchMixtureRecording() {
        UpdateMixtureRecordingDto updateMixtureRecordingDto = new UpdateMixtureRecordingDto(2L, 1L, new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MixtureRecording mixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MixtureRecording> patchMixtureRecordingCaptor = ArgumentCaptor.forClass(MixtureRecording.class);

        doReturn(mixtureRecording).when(mixtureRecordingService).patchMixtureRecording(eq(1L), patchMixtureRecordingCaptor.capture());

        MixtureRecordingDto mixtureRecordingDto = mixtureRecordingController.patchMixtureRecording(updateMixtureRecordingDto, 1L);

        // Assert patch mixture recording
        assertEquals(null, patchMixtureRecordingCaptor.getValue().getId());
        assertEquals(new Mixture(2L), patchMixtureRecordingCaptor.getValue().getMixture());
        assertEquals(new Measure(1L), patchMixtureRecordingCaptor.getValue().getMeasure());
        assertEquals(new BigDecimal("100"), patchMixtureRecordingCaptor.getValue().getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), patchMixtureRecordingCaptor.getValue().getRecordedAt());
        assertEquals(null, patchMixtureRecordingCaptor.getValue().getCreatedAt());
        assertEquals(null, patchMixtureRecordingCaptor.getValue().getLastUpdated());
        assertEquals(1, patchMixtureRecordingCaptor.getValue().getVersion());

        // Assert returned mixture recording
        assertEquals(1L, mixtureRecordingDto.getId());
        assertEquals(new MeasureDto(1L), mixtureRecordingDto.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());
    }

    @Test
    public void testDeleteMixtureRecording() {
        mixtureRecordingController.deleteMixtureRecording(1L);

        verify(mixtureRecordingService, times(1)).deleteMixtureRecording(1L);
    }
}
