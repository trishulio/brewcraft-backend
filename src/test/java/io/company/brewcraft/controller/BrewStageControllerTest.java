package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddBrewStageDto;
import io.company.brewcraft.dto.BrewDto;
import io.company.brewcraft.dto.BrewStageDto;
import io.company.brewcraft.dto.BrewStageStatusDto;
import io.company.brewcraft.dto.BrewTaskDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateBrewStageDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class BrewStageControllerTest {

   private BrewStageController brewStageController;

   private BrewStageService brewStageService;

   @BeforeEach
   public void init() {
       brewStageService = mock(BrewStageService.class);

       brewStageController = new BrewStageController(brewStageService, new AttributeFilter());
   }

   @Test
   public void testGetBrewStages() {
       BrewStage brewStage = new BrewStage(1L, new Brew(2L), new BrewStageStatus(3L, "COMPLETE"), new BrewTask(4L, "MASH"), List.of(new Mixture()), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       List<BrewStage> brewStageList = List.of(brewStage);
       Page<BrewStage> mPage = mock(Page.class);
       doReturn(brewStageList.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(brewStageService).getBrewStages(
           null, null, null, null, null, null, null, null,
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );

       PageDto<BrewStageDto> dto = brewStageController.getBrewStages(
               null, null, null, null, null, null, null, null,
               new TreeSet<>(List.of("id")),
               true,
               1,
               10
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       BrewStageDto brewStageDto = dto.getContent().get(0);

       assertEquals(1L, brewStageDto.getId());
       assertEquals(new BrewDto(2L), brewStageDto.getBrew());
       assertEquals(new BrewStageStatusDto(3L, "COMPLETE"), brewStageDto.getStatus());
       assertEquals(new BrewTaskDto(4L, "MASH"), brewStageDto.getTask());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewStageDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewStageDto.getEndedAt());
       assertEquals(1, brewStageDto.getVersion());
   }

   @Test
   public void testGetBrewStage() {
       BrewStage brewStage = new BrewStage(1L, new Brew(2L), new BrewStageStatus(3L, "COMPLETE"), new BrewTask(4L, "MASH"), List.of(new Mixture()), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       doReturn(brewStage).when(brewStageService).getBrewStage(1L);

       BrewStageDto brewStageDto = brewStageController.getBrewStage(1L);

       assertEquals(1L, brewStageDto.getId());
       assertEquals(new BrewDto(2L), brewStageDto.getBrew());
       assertEquals(new BrewStageStatusDto(3L, "COMPLETE"), brewStageDto.getStatus());
       assertEquals(new BrewTaskDto(4L, "MASH"), brewStageDto.getTask());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewStageDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewStageDto.getEndedAt());
       assertEquals(1, brewStageDto.getVersion());
   }

   @Test
   public void testGetBrewStage_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       when(brewStageService.getBrewStage(1L)).thenReturn(null);
       assertThrows(EntityNotFoundException.class, () -> brewStageController.getBrewStage(1L), "Brew Stage not found with id: 1");
   }

   @Test
   public void testAddBrewStage() {
       AddBrewStageDto addBrewStageDto = new AddBrewStageDto(2L, 3L, 4L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4));

       BrewStage brewStage = new BrewStage(null, new Brew(2L), new BrewStageStatus(3L), new BrewTask(4L), null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), null, null, null);
       BrewStage addedBrewStage = new BrewStage(1L, new Brew(2L), new BrewStageStatus(3L, "COMPLETE"), new BrewTask(4L, "MASH"), List.of(new Mixture()), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       doReturn(List.of(addedBrewStage)).when(brewStageService).addBrewStages(List.of(brewStage));

       List<BrewStageDto> brewStageDtos = brewStageController.addBrewStages(List.of(addBrewStageDto));

       assertEquals(1, brewStageDtos.size());
       assertEquals(1L, brewStageDtos.get(0).getId());
       assertEquals(new BrewDto(2L), brewStageDtos.get(0).getBrew());
       assertEquals(new BrewStageStatusDto(3L, "COMPLETE"), brewStageDtos.get(0).getStatus());
       assertEquals(new BrewTaskDto(4L, "MASH"), brewStageDtos.get(0).getTask());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewStageDtos.get(0).getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewStageDtos.get(0).getEndedAt());
       assertEquals(1, brewStageDtos.get(0).getVersion());
   }

   @Test
   public void testPutBrewStage() {
       UpdateBrewStageDto updateBrewStageDto = new UpdateBrewStageDto(2L, 3L, 4L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

       BrewStage brewStage = new BrewStage(1L, new Brew(2L), new BrewStageStatus(3L, "COMPLETE"), new BrewTask(4L, "MASH"), List.of(new Mixture()), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       ArgumentCaptor<BrewStage> putBrewStageCaptor = ArgumentCaptor.forClass(BrewStage.class);

       doReturn(brewStage).when(brewStageService).putBrewStage(eq(1L), putBrewStageCaptor.capture());

       BrewStageDto brewStageDto = brewStageController.putBrewStage(updateBrewStageDto, 1L);

       //Assert put brew stage
       assertEquals(null, putBrewStageCaptor.getValue().getId());
       assertEquals(new Brew(2L), putBrewStageCaptor.getValue().getBrew());
       assertEquals(new BrewStageStatus(3L), putBrewStageCaptor.getValue().getStatus());
       assertEquals(new BrewTask(4L), putBrewStageCaptor.getValue().getTask());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), putBrewStageCaptor.getValue().getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), putBrewStageCaptor.getValue().getEndedAt());
       assertEquals(1, putBrewStageCaptor.getValue().getVersion());

       //Assert returned brew stage
       assertEquals(1L, brewStageDto.getId());
       assertEquals(new BrewDto(2L), brewStageDto.getBrew());
       assertEquals(new BrewStageStatusDto(3L, "COMPLETE"), brewStageDto.getStatus());
       assertEquals(new BrewTaskDto(4L, "MASH"), brewStageDto.getTask());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewStageDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewStageDto.getEndedAt());
       assertEquals(1, brewStageDto.getVersion());
   }

   @Test
   public void testPatchBrewStage() {
       UpdateBrewStageDto updateBrewStageDto = new UpdateBrewStageDto(2L, 3L, 4L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

       BrewStage brewStage = new BrewStage(1L, new Brew(2L), new BrewStageStatus(3L, "COMPLETE"), new BrewTask(4L, "MASH"), List.of(new Mixture()), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       ArgumentCaptor<BrewStage> patchBrewStageCaptor = ArgumentCaptor.forClass(BrewStage.class);

       doReturn(brewStage).when(brewStageService).patchBrewStage(eq(1L), patchBrewStageCaptor.capture());

       BrewStageDto brewStageDto = brewStageController.patchBrewStage(1L, updateBrewStageDto);

       //Assert patch brew stage
       assertEquals(null, patchBrewStageCaptor.getValue().getId());
       assertEquals(new Brew(2L), patchBrewStageCaptor.getValue().getBrew());
       assertEquals(new BrewStageStatus(3L), patchBrewStageCaptor.getValue().getStatus());
       assertEquals(new BrewTask(4L), patchBrewStageCaptor.getValue().getTask());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), patchBrewStageCaptor.getValue().getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), patchBrewStageCaptor.getValue().getEndedAt());
       assertEquals(1, patchBrewStageCaptor.getValue().getVersion());

       //Assert returned brew stage
       assertEquals(1L, brewStageDto.getId());
       assertEquals(new BrewDto(2L), brewStageDto.getBrew());
       assertEquals(new BrewStageStatusDto(3L, "COMPLETE"), brewStageDto.getStatus());
       assertEquals(new BrewTaskDto(4L, "MASH"), brewStageDto.getTask());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewStageDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewStageDto.getEndedAt());
       assertEquals(1, brewStageDto.getVersion());
   }

   @Test
   public void testDeleteBrewStage() {
       brewStageController.deleteBrewStage(1L);

       verify(brewStageService, times(1)).deleteBrewStage(1L);
   }
}