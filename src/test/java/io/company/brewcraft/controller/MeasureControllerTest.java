package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.MeasureDto;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.service.MeasureService;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class MeasureControllerTest {

   private MeasureController measureController;

   private MeasureService measureService;

   @BeforeEach
   public void init() {
       measureService = mock(MeasureService.class);

       measureController = new MeasureController(measureService, new AttributeFilter());
   }

   @Test
   public void testGetMeasures() { 
       Page<Measure> mPage = new PageImpl<>(List.of(new Measure(1L, "abv"), new Measure(2L, "ibu")));

       doReturn(mPage).when(measureService).getMeasures(
           null,
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );

       PageDto<MeasureDto> dto = measureController.getMeasures(
    		   null,
               new TreeSet<>(List.of("id")),
               true,
               1,
               10
       );

       assertEquals(1, dto.getTotalPages());
       assertEquals(List.of(new MeasureDto(1L, "abv"), new MeasureDto(2L, "ibu")), dto.getContent());
   }
  
}