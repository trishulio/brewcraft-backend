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

import io.company.brewcraft.dto.AddMixtureDto;
import io.company.brewcraft.dto.BrewStageDto;
import io.company.brewcraft.dto.FacilityEquipmentDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.MixtureRecordingDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateMixtureDto;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.service.MixtureService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

@SuppressWarnings("unchecked")
public class MixtureControllerTest {

   private MixtureController mixtureController;

   private MixtureService mixtureService;

   @BeforeEach
   public void init() {
       mixtureService = mock(MixtureService.class);

       mixtureController = new MixtureController(mixtureService, new AttributeFilter());
   }

   @Test
   public void testGetMixtures() {
	   Mixture mixture = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(4L)), List.of(new MixtureRecording(5L)), new BrewStage(6L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);
   
       List<Mixture> mixtureList = List.of(mixture);
       Page<Mixture> mPage = mock(Page.class);
       doReturn(mixtureList.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(mixtureService).getMixtures(
           null, null, null, null, null, null, null, null, 
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );

       PageDto<MixtureDto> dto = mixtureController.getMixtures(
               null, null, null, null, null, null, null, null,
               new TreeSet<>(List.of("id")),
               true,
               1,
               10
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       MixtureDto mixtureDto = dto.getContent().get(0);

       assertEquals(1L, mixtureDto.getId());
       assertEquals(2L, mixtureDto.getParentMixtureId());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), mixtureDto.getQuantity());
       assertEquals(new FacilityEquipmentDto(3L), mixtureDto.getEquipment());
       assertEquals(List.of(new MaterialPortionDto(4L)), mixtureDto.getMaterialPortions());
       assertEquals(List.of(new MixtureRecordingDto(5L)), mixtureDto.getRecordedMeasures());
       assertEquals(new BrewStageDto(6L), mixtureDto.getBrewStage());
       assertEquals(1, mixtureDto.getVersion());
   }
   
   @Test
   public void testGetMixture() {
	   Mixture mixture = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(4L)), List.of(new MixtureRecording(5L)), new BrewStage(6L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

       doReturn(mixture).when(mixtureService).getMixture(1L);
       
       MixtureDto mixtureDto = mixtureController.getMixture(1L);
       
       assertEquals(1L, mixtureDto.getId());
       assertEquals(2L, mixtureDto.getParentMixtureId());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), mixtureDto.getQuantity());
       assertEquals(new FacilityEquipmentDto(3L), mixtureDto.getEquipment());
       assertEquals(List.of(new MaterialPortionDto(4L)), mixtureDto.getMaterialPortions());
       assertEquals(List.of(new MixtureRecordingDto(5L)), mixtureDto.getRecordedMeasures());
       assertEquals(new BrewStageDto(6L), mixtureDto.getBrewStage());
       assertEquals(1, mixtureDto.getVersion());
   }
   
   @Test
   public void testGetMixture_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       when(mixtureService.getMixture(1L)).thenReturn(null);
       assertThrows(EntityNotFoundException.class, () -> mixtureController.getMixture(1L), "Mixture not found with id: 1");
   }

   @Test
   public void testAddMixture() {
	   AddMixtureDto addMixtureDto = new AddMixtureDto(2L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 3L, 6L);

	   Mixture mixture = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), null, null, new BrewStage(6L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);
   
       ArgumentCaptor<Mixture> addMixtureCaptor = ArgumentCaptor.forClass(Mixture.class);
       
       doReturn(mixture).when(mixtureService).addMixture(addMixtureCaptor.capture());

       MixtureDto mixtureDto = mixtureController.addMixture(addMixtureDto);
       
       //Assert added mixture
       assertEquals(null, addMixtureCaptor.getValue().getId());
       assertEquals(2L, addMixtureCaptor.getValue().getParentMixture().getId());
       assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), addMixtureCaptor.getValue().getQuantity());
       assertEquals(new Equipment(3L), addMixtureCaptor.getValue().getEquipment());
       assertEquals(null, addMixtureCaptor.getValue().getMaterialPortions());
       assertEquals(null, addMixtureCaptor.getValue().getRecordedMeasures());
       assertEquals(new BrewStage(6L), addMixtureCaptor.getValue().getBrewStage());
       assertEquals(null, addMixtureCaptor.getValue().getCreatedAt());
       assertEquals(null, addMixtureCaptor.getValue().getLastUpdated());
       assertEquals(null, addMixtureCaptor.getValue().getVersion());
       
       //Assert returned mixture  
       assertEquals(1L, mixtureDto.getId());
       assertEquals(2L, mixtureDto.getParentMixtureId());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), mixtureDto.getQuantity());
       assertEquals(new FacilityEquipmentDto(3L), mixtureDto.getEquipment());
       assertEquals(null, mixtureDto.getMaterialPortions());
       assertEquals(null, mixtureDto.getRecordedMeasures());
       assertEquals(new BrewStageDto(6L), mixtureDto.getBrewStage());
       assertEquals(1, mixtureDto.getVersion());
   }
   
   @Test
   public void testPutMixture() {
	   UpdateMixtureDto updateMixtureDto = new UpdateMixtureDto(2L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 3L, 6L, 1);
              
	   Mixture mixture = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), null, null, new BrewStage(6L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);
   
       ArgumentCaptor<Mixture> putMixtureCaptor = ArgumentCaptor.forClass(Mixture.class);
       
       doReturn(mixture).when(mixtureService).putMixture(eq(1L), putMixtureCaptor.capture());

       MixtureDto mixtureDto = mixtureController.putMixture(updateMixtureDto, 1L);
       
       //Assert put mixture
       assertEquals(null, putMixtureCaptor.getValue().getId());
       assertEquals(2L, putMixtureCaptor.getValue().getParentMixture().getId());
       assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), putMixtureCaptor.getValue().getQuantity());
       assertEquals(new Equipment(3L), putMixtureCaptor.getValue().getEquipment());
       assertEquals(null, putMixtureCaptor.getValue().getMaterialPortions());
       assertEquals(null, putMixtureCaptor.getValue().getRecordedMeasures());
       assertEquals(new BrewStage(6L), putMixtureCaptor.getValue().getBrewStage());
       assertEquals(null, putMixtureCaptor.getValue().getCreatedAt());
       assertEquals(null, putMixtureCaptor.getValue().getLastUpdated());
       assertEquals(1, putMixtureCaptor.getValue().getVersion());
       
       //Assert returned mixture  
       assertEquals(1L, mixtureDto.getId());
       assertEquals(2L, mixtureDto.getParentMixtureId());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), mixtureDto.getQuantity());
       assertEquals(new FacilityEquipmentDto(3L), mixtureDto.getEquipment());
       assertEquals(null, mixtureDto.getMaterialPortions());
       assertEquals(null, mixtureDto.getRecordedMeasures());
       assertEquals(new BrewStageDto(6L), mixtureDto.getBrewStage());
       assertEquals(1, mixtureDto.getVersion());
   }
   
   @Test
   public void testPatchMixture() {
	   UpdateMixtureDto updateMixtureDto = new UpdateMixtureDto(2L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 3L, 6L, 1);

	   Mixture mixture = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), null, null, new BrewStage(6L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

       ArgumentCaptor<Mixture> patchMixtureCaptor = ArgumentCaptor.forClass(Mixture.class);
       
       doReturn(mixture).when(mixtureService).patchMixture(eq(1L), patchMixtureCaptor.capture());

       MixtureDto mixtureDto = mixtureController.patchMixture(updateMixtureDto, 1L);
       
       //Assert patched mixture
       assertEquals(null, patchMixtureCaptor.getValue().getId());
       assertEquals(2L, patchMixtureCaptor.getValue().getParentMixture().getId());
       assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), patchMixtureCaptor.getValue().getQuantity());
       assertEquals(new Equipment(3L), patchMixtureCaptor.getValue().getEquipment());
       assertEquals(null, patchMixtureCaptor.getValue().getMaterialPortions());
       assertEquals(null, patchMixtureCaptor.getValue().getRecordedMeasures());
       assertEquals(new BrewStage(6L), patchMixtureCaptor.getValue().getBrewStage());
       assertEquals(null, patchMixtureCaptor.getValue().getCreatedAt());
       assertEquals(null, patchMixtureCaptor.getValue().getLastUpdated());
       assertEquals(1, patchMixtureCaptor.getValue().getVersion());
       
       //Assert returned mixture  
       assertEquals(1L, mixtureDto.getId());
       assertEquals(2L, mixtureDto.getParentMixtureId());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), mixtureDto.getQuantity());
       assertEquals(new FacilityEquipmentDto(3L), mixtureDto.getEquipment());
       assertEquals(null, mixtureDto.getMaterialPortions());
       assertEquals(null, mixtureDto.getRecordedMeasures());
       assertEquals(new BrewStageDto(6L), mixtureDto.getBrewStage());
       assertEquals(1, mixtureDto.getVersion());
   }
   
   @Test
   public void testDeleteMixture() {
       mixtureController.deleteMixture(1L);

       verify(mixtureService, times(1)).deleteMixture(1L);
   }

}