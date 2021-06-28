package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.Selector;

@SuppressWarnings("unchecked")
public class MaterialLotAggregationRepositoryTest {

    private MaterialLotAggregationRepository repo;
    
    private AggregationRepository mRepo;
    
    @BeforeEach
    public void init() {
        mRepo = mock(AggregationRepository.class);
        repo = new MaterialLotAggregationRepository(mRepo);
    }
    
    @Test
    public void testGetAggregation_ReturnsGetAggregationFromBaseRepo() {
        Page<MaterialLot> mPage = new PageImpl<>(List.of(new MaterialLot(1L)));
        doReturn(mPage).when(mRepo).getAggregation(
            eq(MaterialLot.class),
            eq(new Selector().select("select")),
            eq(new Selector().select("group")),
            any(Specification.class),
            eq(PageRequest.of(1, 10))
        );
        
        Page<MaterialLot> page = repo.getAggregation(
            new Selector().select("select"),
            new Selector().select("group"),
            SpecificationBuilder.builder().build(),
            PageRequest.of(1, 10)
        );
        
        Page<MaterialLot> expected = new PageImpl<>(List.of(new MaterialLot(1L)));
        assertEquals(expected, page);
    }
    
    @Test
    public void testGetAggregationNoGroupBy_ReturnsGetAggregationNoGroupByFromBaseRepo() {
        Page<MaterialLot> mPage = new PageImpl<>(List.of(new MaterialLot(1L)));
        doReturn(mPage).when(mRepo).getAggregation(
            eq(MaterialLot.class),
            eq(new Selector().select("select")),
            isNull(),
            any(Specification.class),
            eq(PageRequest.of(1, 10))
        );
        
        Page<MaterialLot> page = repo.getAggregation(
            new Selector().select("select"),
            SpecificationBuilder.builder().build(),
            PageRequest.of(1, 10)
        );
        
        Page<MaterialLot> expected = new PageImpl<>(List.of(new MaterialLot(1L)));
        assertEquals(expected, page);
    }
}
