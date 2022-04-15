package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.BaseEquipment;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.service.EquipmentService;

@SuppressWarnings("unchecked")
public class EquipmentControllerTest {
   private EquipmentController controller;

   private CrudControllerService<
               Long,
               Equipment,
               BaseEquipment,
               UpdateEquipment,
               EquipmentDto,
               AddEquipmentDto,
               UpdateEquipmentDto
           > mCrudController;

   private EquipmentService mService;

   @BeforeEach
   public void init() {
       this.mCrudController = mock(CrudControllerService.class);
       this.mService = mock(EquipmentService.class);
       this.controller = new EquipmentController(mCrudController, mService);
   }

   @Test
   public void testGetAllEquipment_ReturnsDtosFromController() {
       doReturn(new PageImpl<>(List.of(new Equipment(1L)))).when(mService).getEquipment(
           Set.of(1L),
           Set.of(2L),
           Set.of(3L),
           Set.of(4L),
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );
       doReturn(new PageDto<>(List.of(new EquipmentDto(1L)), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new Equipment(1L))), Set.of(""));

       PageDto<EquipmentDto> page = this.controller.getAllEquipment(
           Set.of(1L),
           Set.of(2L),
           Set.of(3L),
           Set.of(4L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10,
           Set.of("")
       );

       PageDto<EquipmentDto> expected = new PageDto<>(List.of(new EquipmentDto(1L)), 1, 1);
       assertEquals(expected, page);
   }

   @Test
   public void testGetEquipment_ReturnsDtoFromController() {
       doReturn(new EquipmentDto(1L)).when(mCrudController).get(1L, Set.of(""));

       EquipmentDto dto = this.controller.getEquipment(1L, Set.of(""));

       EquipmentDto expected = new EquipmentDto(1L);
       assertEquals(expected, dto);
   }

   @Test
   public void testDeleteEquipment_ReturnsDeleteCountFromController() {
       doReturn(1L).when(mCrudController).delete(Set.of(1L));

       assertEquals(1L, this.controller.deleteEquipment(Set.of(1L)));
   }

   @Test
   public void testAddIEquipment_AddsToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new EquipmentDto(1L))).when(mCrudController).add(List.of(new AddEquipmentDto()));

       List<EquipmentDto> dtos = this.controller.addEquipment(List.of(new AddEquipmentDto()));

       assertEquals(List.of(new EquipmentDto(1L)), dtos);
   }

   @Test
   public void testUpdateEquipment_PutsToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new EquipmentDto(1L))).when(mCrudController).put(List.of(new UpdateEquipmentDto(1L)));

       List<EquipmentDto> dtos = this.controller.updateEquipment(List.of(new UpdateEquipmentDto(1L)));

       assertEquals(List.of(new EquipmentDto(1L)), dtos);
   }

   @Test
   public void testPatchEquipment_PatchToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new EquipmentDto(1L))).when(mCrudController).patch(List.of(new UpdateEquipmentDto(1L)));

       List<EquipmentDto> dtos = this.controller.patchEquipment(List.of(new UpdateEquipmentDto(1L)));

       assertEquals(List.of(new EquipmentDto(1L)), dtos);
   }
}
