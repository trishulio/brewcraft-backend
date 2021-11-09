package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
        doReturn(mPage).when(mixtureRecordingService).getMixtureRecordings(null, null, null, null, 1, 10, new TreeSet<>(List.of("id")), true);

        PageDto<MixtureRecordingDto> dto = mixtureRecordingController.getMixtureRecordings(null, null, null, null, new TreeSet<>(List.of("id")), true, 1, 10);

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
    public void testAddMixtureRecordings() {
        AddMixtureRecordingDto addMixtureRecordingDto = new AddMixtureRecordingDto(2L, 1L, new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4));

        MixtureRecording mixtureRecording = new MixtureRecording(null, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, null);
        MixtureRecording addedmixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(addedmixtureRecording)).when(mixtureRecordingService).addMixtureRecordings(List.of(mixtureRecording));

        List<MixtureRecordingDto> mixtureRecordingDtos = mixtureRecordingController.addMixtureRecordings(List.of(addMixtureRecordingDto));

        assertEquals(1, mixtureRecordingDtos.size());
        assertEquals(1L, mixtureRecordingDtos.get(0).getId());
        assertEquals(new MeasureDto(1L), mixtureRecordingDtos.get(0).getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDtos.get(0).getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDtos.get(0).getRecordedAt());
        assertEquals(1, mixtureRecordingDtos.get(0).getVersion());
    }

    @Test
    public void testPutMixtureRecording() {
        UpdateMixtureRecordingDto updateMixtureRecordingDto = new UpdateMixtureRecordingDto(null, 2L, 1L, new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MixtureRecording mixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, 1);
        MixtureRecording putMixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(putMixtureRecording)).when(mixtureRecordingService).putMixtureRecordings(List.of(mixtureRecording));

        MixtureRecordingDto mixtureRecordingDto = mixtureRecordingController.putMixtureRecording(updateMixtureRecordingDto, 1L);

        // Assert returned mixture recording
        assertEquals(1L, mixtureRecordingDto.getId());
        assertEquals(new MeasureDto(1L), mixtureRecordingDto.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());
    }

    @Test
    public void testPatchMixtureRecording() {
        UpdateMixtureRecordingDto updateMixtureRecordingDto = new UpdateMixtureRecordingDto(null, 2L, 1L, new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MixtureRecording mixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, 1);
        MixtureRecording patchedMixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);


        doReturn(List.of(patchedMixtureRecording)).when(mixtureRecordingService).patchMixtureRecordings(List.of(mixtureRecording));

        MixtureRecordingDto mixtureRecordingDto = mixtureRecordingController.patchMixtureRecording(updateMixtureRecordingDto, 1L);

        // Assert returned mixture recording
        assertEquals(1L, mixtureRecordingDto.getId());
        assertEquals(new MeasureDto(1L), mixtureRecordingDto.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());
    }

    @Test
    public void testDeleteMixtureRecording() {
        mixtureRecordingController.deleteMixtureRecordings(Set.of(1L, 2L));

        verify(mixtureRecordingService, times(1)).deleteMixtureRecordings(Set.of(1L, 2L));
    }
}
