package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.service.InvoiceService;

@SuppressWarnings("unchecked")
public class InvoiceControllerTest {
   private InvoiceController controller;

   private CrudControllerService<
               Long,
               Invoice,
               BaseInvoice<? extends BaseInvoiceItem<?>>,
               UpdateInvoice<? extends UpdateInvoiceItem<?>>,
               InvoiceDto,
               AddInvoiceDto,
               UpdateInvoiceDto
           > mCrudController;

   private InvoiceService mService;

   @BeforeEach
   public void init() {
       this.mCrudController = mock(CrudControllerService.class);
       this.mService = mock(InvoiceService.class);
       this.controller = new InvoiceController(mCrudController, mService);
   }

   @Test
   public void testGetAllInvoice_ReturnsDtosFromController() {
       doReturn(new PageImpl<>(List.of(new Invoice(1L)))).when(mService).getInvoices(
           Set.of(1L),
           Set.of(2L),
           Set.of("Invoice Number"),
           Set.of("invoice description"),
           Set.of("item description"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           Set.of(4L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           new BigDecimal("3"),
           new BigDecimal("4"),
           Set.of(99L),
           Set.of(4L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10
       );
       doReturn(new PageDto<>(List.of(new InvoiceDto(1L)), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new Invoice(1L))), Set.of(""));

       PageDto<InvoiceDto> page = this.controller.getAll(
           Set.of(1L),
           Set.of(2L),
           Set.of("Invoice Number"),
           Set.of("invoice description"),
           Set.of("item description"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           Set.of(4L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           new BigDecimal("3"),
           new BigDecimal("4"),
           Set.of(99L),
           Set.of(4L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10,
           Set.of("")
       );

       PageDto<InvoiceDto> expected = new PageDto<>(List.of(new InvoiceDto(1L)), 1, 1);
       assertEquals(expected, page);
   }

   @Test
   public void testGetInvoice_ReturnsDtoFromController() {
       doReturn(new InvoiceDto(1L)).when(mCrudController).get(1L, Set.of(""));

       InvoiceDto dto = this.controller.getInvoice(1L, Set.of(""));

       InvoiceDto expected = new InvoiceDto(1L);
       assertEquals(expected, dto);
   }

   @Test
   public void testDeleteInvoices_ReturnsDeleteCountFromController() {
       doReturn(1L).when(mCrudController).delete(Set.of(1L));

       assertEquals(1L, this.controller.deleteInvoices(Set.of(1L)));
   }

   @Test
   public void testAddInvoices_AddsToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new InvoiceDto(1L))).when(mCrudController).add(List.of(new AddInvoiceDto()));

       List<InvoiceDto> dtos = this.controller.addInvoice(List.of(new AddInvoiceDto()));

       assertEquals(List.of(new InvoiceDto(1L)), dtos);
   }

   @Test
   public void testUpdateInvoices_PutsToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new InvoiceDto(1L))).when(mCrudController).put(List.of(new UpdateInvoiceDto(1L)));

       List<InvoiceDto> dtos = this.controller.updateInvoice(List.of(new UpdateInvoiceDto(1L)));

       assertEquals(List.of(new InvoiceDto(1L)), dtos);
   }

   @Test
   public void testPatchInvoices_PatchToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new InvoiceDto(1L))).when(mCrudController).patch(List.of(new UpdateInvoiceDto(1L)));

       List<InvoiceDto> dtos = this.controller.patchInvoice(List.of(new UpdateInvoiceDto(1L)));

       assertEquals(List.of(new InvoiceDto(1L)), dtos);
   }
}
