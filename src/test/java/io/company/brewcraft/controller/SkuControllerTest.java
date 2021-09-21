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
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddSkuDto;
import io.company.brewcraft.dto.AddSkuMaterialDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.dto.SkuMaterialDto;
import io.company.brewcraft.dto.UpdateSkuDto;
import io.company.brewcraft.dto.UpdateSkuMaterialDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.service.SkuService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.SupportedUnits;
import io.company.brewcraft.util.controller.AttributeFilter;
import tec.uom.se.quantity.Quantities;

@SuppressWarnings("unchecked")
public class SkuControllerTest {

   private SkuController skuController;

   private SkuService skuService;
   private AttributeFilter filter;

   @BeforeEach
   public void init() {
       this.skuService = mock(SkuService.class);
       this.filter = new AttributeFilter();

       this.skuController = new SkuController(this.skuService, this.filter);
   }

   @Test
   public void testGetSkus_CallsServicesWithArguments_AndReturnsPageDtoOfSkuDtosMappedFromSkusPage() {
       final List<Sku> skus = List.of(
               new Sku(1L, new Product(2L), List.of(new SkuMaterial(1L, new Sku(1L), new Material(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)
       );
       final Page<Sku> mPage = mock(Page.class);
       doReturn(skus.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(this.skuService).getSkus(
           Set.of(1L),
           Set.of(2L),
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );

       final PageDto<SkuDto> dto = this.skuController.getSkus(
           Set.of(1L),
           Set.of(2L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       
       final SkuDto sku = dto.getContent().get(0);
       assertEquals(1L, sku.getId());
       assertEquals(new ProductDto(2L), sku.getProduct());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), sku.getQuantity());
       assertEquals(1, sku.getVersion());
       
       assertEquals(1, sku.getMaterials().size());
       final SkuMaterialDto skuMaterial = sku.getMaterials().get(0);
       assertEquals(1L, skuMaterial.getId());
       assertEquals(new MaterialDto(2L), skuMaterial.getMaterial());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), skuMaterial.getQuantity());
       assertEquals(1, skuMaterial.getVersion());
   }

   @Test
   public void testGetSku_ReturnsSkuDtoMappedFromServiceSku() {
       final Sku expectedSku = new Sku(1L, new Product(2L), List.of(new SkuMaterial(1L, new Sku(1L), new Material(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

       doReturn(expectedSku).when(this.skuService).getSku(1L);

       final SkuDto actualSku = this.skuController.getSku(1L);

       assertEquals(1L, actualSku.getId());
       assertEquals(new ProductDto(2L), actualSku.getProduct());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), actualSku.getQuantity());
       assertEquals(1, actualSku.getVersion());
       
       assertEquals(1, actualSku.getMaterials().size());
       final SkuMaterialDto skuMaterial = actualSku.getMaterials().get(0);
       assertEquals(1L, skuMaterial.getId());
       assertEquals(new MaterialDto(2L), skuMaterial.getMaterial());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), skuMaterial.getQuantity());
       assertEquals(1, skuMaterial.getVersion());
   }

   @Test
   public void testGetSku_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       doReturn(null).when(this.skuService).getSku(1L);
       assertThrows(EntityNotFoundException.class, () -> this.skuController.getSku(1L), "Sku not found with id: 1");
   }

   @Test
   public void testDeleteSkus_ReturnsDeleteCountFromService() {
       skuController.deleteSku(1L);

       verify(skuService, times(1)).deleteSku(1L);
   }

   @Test
   public void testAddSku_ReturnsSkuDtoAfterAddingToService() {
       doAnswer(i -> i.getArgument(0)).when(this.skuService).addSkus(anyList());

       final AddSkuDto payload = new AddSkuDto(2L, List.of(new AddSkuMaterialDto(2L, new QuantityDto("hl", BigDecimal.valueOf(100)))), new QuantityDto("hl", BigDecimal.valueOf(100)));

       final SkuDto skuDto = this.skuController.addSku(payload);

       assertEquals(null, skuDto.getId());
       assertEquals(new ProductDto(2L), skuDto.getProduct());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), skuDto.getQuantity());
       assertEquals(null, skuDto.getVersion());
       
       assertEquals(1, skuDto.getMaterials().size());
       final SkuMaterialDto skuMaterial = skuDto.getMaterials().get(0);
       assertEquals(null, skuMaterial.getId());
       assertEquals(new MaterialDto(2L), skuMaterial.getMaterial());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), skuMaterial.getQuantity());
       assertEquals(null, skuMaterial.getVersion());
   }

   @Test
   public void testPutSku_ReturnsSkuDtoAfterUpdatingItToService() {
       doAnswer(i -> i.getArgument(0)).when(this.skuService).putSkus(anyList());

       UpdateSkuDto payload = new UpdateSkuDto(2L, List.of(new UpdateSkuMaterialDto(1L, 2L, new QuantityDto("hl", BigDecimal.valueOf(100)), 1)), new QuantityDto("hl", BigDecimal.valueOf(100)), 1);

       final SkuDto skuDto = this.skuController.putSku(1L, payload);

       assertEquals(1L, skuDto.getId());
       assertEquals(new ProductDto(2L), skuDto.getProduct());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), skuDto.getQuantity());
       assertEquals(1, skuDto.getVersion());
       
       assertEquals(1, skuDto.getMaterials().size());
       final SkuMaterialDto skuMaterial = skuDto.getMaterials().get(0);
       assertEquals(1L, skuMaterial.getId());
       assertEquals(new MaterialDto(2L), skuMaterial.getMaterial());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), skuMaterial.getQuantity());
       assertEquals(1, skuMaterial.getVersion());
   }

   @Test
   public void testPatchSku_ReturnsSkuDtoAfterPatchingItToService() {
       doAnswer(i -> i.getArgument(0)).when(this.skuService).patchSkus(anyList());

       UpdateSkuDto payload = new UpdateSkuDto(2L, List.of(new UpdateSkuMaterialDto(1L, 2L, new QuantityDto("hl", BigDecimal.valueOf(100)), 1)), new QuantityDto("hl", BigDecimal.valueOf(100)), 1);

       final SkuDto skuDto = this.skuController.patchSku(1L, payload);

       assertEquals(1L, skuDto.getId());
       assertEquals(new ProductDto(2L), skuDto.getProduct());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), skuDto.getQuantity());
       assertEquals(1, skuDto.getVersion());
       
       assertEquals(1, skuDto.getMaterials().size());
       final SkuMaterialDto skuMaterial = skuDto.getMaterials().get(0);
       assertEquals(1L, skuMaterial.getId());
       assertEquals(new MaterialDto(2L), skuMaterial.getMaterial());
       assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100)), skuMaterial.getQuantity());
       assertEquals(1, skuMaterial.getVersion());
   }
}
