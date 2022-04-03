package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddBrewDto;
import io.company.brewcraft.dto.BrewDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.UpdateBrewDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class BrewControllerTest {

   private BrewController brewController;

   private BrewService brewService;

   @BeforeEach
   public void init() {
       brewService = mock(BrewService.class);

       brewController = new BrewController(brewService, new AttributeFilter());
   }

   @Test
   public void testGetBrews() {
       Brew brew = new Brew(1L, "testName", "testDesc", "2", new Product(3L), new Brew(2L), null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), new User(7L), new User(8L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       List<Brew> brewList = List.of(brew);
       Page<Brew> mPage = mock(Page.class);
       doReturn(brewList.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(brewService).getBrews(
           Set.of(1L),
           Set.of("a123"),
           Set.of("test"),
           Set.of(3L),
           Set.of(4L),
           Set.of(5L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           Set.of(7L),
           Set.of(8L),
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );

       PageDto<BrewDto> dto = brewController.getBrews(
               Set.of(1L),
               Set.of("a123"),
               Set.of("test"),
               Set.of(3L),
               Set.of(4L),
               Set.of(5L),
               LocalDateTime.of(1999, 1, 1, 12, 0),
               LocalDateTime.of(2000, 1, 1, 12, 0),
               LocalDateTime.of(2001, 1, 1, 12, 0),
               LocalDateTime.of(2002, 1, 1, 12, 0),
               Set.of(7L),
               Set.of(8L),
               new TreeSet<>(List.of("id")),
               true,
               1,
               10
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       BrewDto brewDto = dto.getContent().get(0);

       assertEquals(1L, brewDto.getId());
       assertEquals("testName", brewDto.getName());
       assertEquals("testDesc", brewDto.getDescription());
       assertEquals("2", brewDto.getBatchId());
       assertEquals(new ProductDto(3L), brewDto.getProduct());
       assertEquals(2L, brewDto.getParentBrewId());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewDto.getEndedAt());
       assertEquals(new UserDto(7L), brewDto.getAssignedTo());
       assertEquals(new UserDto(8L), brewDto.getOwnedBy());
       assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getCreatedAt());
       assertEquals(1, brewDto.getVersion());
   }

   @Test
   public void testGetBrew() {
       Brew brew = new Brew(1L, "testName", "testDesc", "2", new Product(3L), new Brew(2L), null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), new User(7L), new User(8L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       doReturn(brew).when(brewService).getBrew(1L);

       BrewDto brewDto = brewController.getBrew(1L);

       assertEquals(1L, brewDto.getId());
       assertEquals("testName", brewDto.getName());
       assertEquals("testDesc", brewDto.getDescription());
       assertEquals("2", brewDto.getBatchId());
       assertEquals(new ProductDto(3L), brewDto.getProduct());
       assertEquals(2L, brewDto.getParentBrewId());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewDto.getEndedAt());
       assertEquals(new UserDto(7L), brewDto.getAssignedTo());
       assertEquals(new UserDto(8L), brewDto.getOwnedBy());
       assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getCreatedAt());
       assertEquals(1, brewDto.getVersion());
   }

   @Test
   public void testGetBrew_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       when(brewService.getBrew(1L)).thenReturn(null);
       assertThrows(EntityNotFoundException.class, () -> brewController.getBrew(1L), "Product not found with id: 1");
   }

   @Test
   public void testAddBrew() {
       AddBrewDto addBrewDto = new AddBrewDto("testName", "testDesc", "2", 3L, 4L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 7L, 8L);

       Brew brew = new Brew(1L, "testName", "testDesc", "2", new Product(3L), new Brew(4L), null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), new User(7L), new User(8L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       ArgumentCaptor<Brew> addBrewCaptor = ArgumentCaptor.forClass(Brew.class);

       doReturn(brew).when(brewService).addBrew(addBrewCaptor.capture());

       BrewDto brewDto = brewController.addBrew(addBrewDto);

       //Assert added brew
       assertEquals(null, addBrewCaptor.getValue().getId());
       assertEquals("testName", addBrewCaptor.getValue().getName());
       assertEquals("testDesc", addBrewCaptor.getValue().getDescription());
       assertEquals("2", addBrewCaptor.getValue().getBatchId());
       assertEquals(new Product(3L), addBrewCaptor.getValue().getProduct());
       assertEquals(4L, addBrewCaptor.getValue().getParentBrew().getId());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addBrewCaptor.getValue().getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addBrewCaptor.getValue().getEndedAt());
       assertEquals(new User(7L), addBrewCaptor.getValue().getAssignedTo());
       assertEquals(new User(8L), addBrewCaptor.getValue().getOwnedBy());
       assertEquals(null, addBrewCaptor.getValue().getCreatedAt());
       assertEquals(null, addBrewCaptor.getValue().getVersion());

       //Assert returned brew
       assertEquals(1L, brewDto.getId());
       assertEquals("testName", brewDto.getName());
       assertEquals("testDesc", brewDto.getDescription());
       assertEquals("2", brewDto.getBatchId());
       assertEquals(new ProductDto(3L), brewDto.getProduct());
       assertEquals(4L, brewDto.getParentBrewId());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewDto.getEndedAt());
       assertEquals(new UserDto(7L), brewDto.getAssignedTo());
       assertEquals(new UserDto(8L), brewDto.getOwnedBy());
       assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getCreatedAt());
       assertEquals(1, brewDto.getVersion());
   }

   @Test
   public void testPutBrew() {
       UpdateBrewDto updateBrewDto = new UpdateBrewDto("testName", "testDesc", "2", 3L, 4L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 7L, 8L, 1);

       Brew brew = new Brew(1L, "testName", "testDesc", "2", new Product(3L), new Brew(4L), null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), new User(7L), new User(8L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       ArgumentCaptor<Brew> putBrewCaptor = ArgumentCaptor.forClass(Brew.class);

       doReturn(brew).when(brewService).putBrew(eq(1L), putBrewCaptor.capture());

       BrewDto brewDto = brewController.putBrew(1L, updateBrewDto);

       //Assert put brew
       assertEquals(null, putBrewCaptor.getValue().getId());
       assertEquals("testName", putBrewCaptor.getValue().getName());
       assertEquals("testDesc", putBrewCaptor.getValue().getDescription());
       assertEquals("2", putBrewCaptor.getValue().getBatchId());
       assertEquals(new Product(3L), putBrewCaptor.getValue().getProduct());
       assertEquals(4L, putBrewCaptor.getValue().getParentBrew().getId());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), putBrewCaptor.getValue().getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), putBrewCaptor.getValue().getEndedAt());
       assertEquals(new User(7L), putBrewCaptor.getValue().getAssignedTo());
       assertEquals(new User(8L), putBrewCaptor.getValue().getOwnedBy());
       assertEquals(null, putBrewCaptor.getValue().getCreatedAt());
       assertEquals(1, putBrewCaptor.getValue().getVersion());

       //Assert returned brew
       assertEquals(1L, brewDto.getId());
       assertEquals("testName", brewDto.getName());
       assertEquals("testDesc", brewDto.getDescription());
       assertEquals("2", brewDto.getBatchId());
       assertEquals(new ProductDto(3L), brewDto.getProduct());
       assertEquals(4L, brewDto.getParentBrewId());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewDto.getEndedAt());
       assertEquals(new UserDto(7L), brewDto.getAssignedTo());
       assertEquals(new UserDto(8L), brewDto.getOwnedBy());
       assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getCreatedAt());
       assertEquals(1, brewDto.getVersion());
   }

   @Test
   public void testPatchBrew() {
       UpdateBrewDto updateBrewDto = new UpdateBrewDto("testName", "testDesc", "2", 3L, 4L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 7L, 8L, 1);

       Brew brew = new Brew(1L, "testName", "testDesc", "2", new Product(3L), new Brew(4L), null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), new User(7L), new User(8L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

       ArgumentCaptor<Brew> patchBrewCaptor = ArgumentCaptor.forClass(Brew.class);

       doReturn(brew).when(brewService).patchBrew(eq(1L), patchBrewCaptor.capture());

       BrewDto brewDto = brewController.patchBrew(1L, updateBrewDto);

       //Assert patched brew
       assertEquals(null, patchBrewCaptor.getValue().getId());
       assertEquals("testName", patchBrewCaptor.getValue().getName());
       assertEquals("testDesc", patchBrewCaptor.getValue().getDescription());
       assertEquals("2", patchBrewCaptor.getValue().getBatchId());
       assertEquals(new Product(3L), patchBrewCaptor.getValue().getProduct());
       assertEquals(4L, patchBrewCaptor.getValue().getParentBrew().getId());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), patchBrewCaptor.getValue().getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), patchBrewCaptor.getValue().getEndedAt());
       assertEquals(new User(7L), patchBrewCaptor.getValue().getAssignedTo());
       assertEquals(new User(8L), patchBrewCaptor.getValue().getOwnedBy());
       assertEquals(null, patchBrewCaptor.getValue().getCreatedAt());
       assertEquals(1, patchBrewCaptor.getValue().getVersion());

       //Assert returned brew
       assertEquals(1L, brewDto.getId());
       assertEquals("testName", brewDto.getName());
       assertEquals("testDesc", brewDto.getDescription());
       assertEquals("2", brewDto.getBatchId());
       assertEquals(new ProductDto(3L), brewDto.getProduct());
       assertEquals(4L, brewDto.getParentBrewId());
       assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewDto.getStartedAt());
       assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewDto.getEndedAt());
       assertEquals(new UserDto(7L), brewDto.getAssignedTo());
       assertEquals(new UserDto(8L), brewDto.getOwnedBy());
       assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getCreatedAt());
       assertEquals(1, brewDto.getVersion());
   }

   @Test
   public void testDeleteBrew() {
       brewController.deleteBrew(1L);

       verify(brewService, times(1)).deleteBrew(1L);
   }
}