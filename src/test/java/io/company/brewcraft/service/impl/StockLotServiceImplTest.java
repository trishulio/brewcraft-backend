package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.repository.StockLotRepository;
import io.company.brewcraft.service.StockLotService;
import io.company.brewcraft.service.StockLotServiceImpl;

public class StockLotServiceImplTest {

    private StockLotService stockLotService;

    private StockLotRepository stockLotRepositoryMock;

    @BeforeEach
    public void init() {
        stockLotRepositoryMock = mock(StockLotRepository.class);

        stockLotService = new StockLotServiceImpl(stockLotRepositoryMock);
    }

    @Test
    public void testGetMixtureRecording_ReturnsMixtureRecording() {
        doReturn(List.of(new StockLot(1L), new StockLot(2L))).when(stockLotRepositoryMock).findAllById(Set.of(1L, 2L));

        List<StockLot> stockLots = stockLotService.getAllByIds(Set.of(1L, 2L));

        assertEquals(List.of(new StockLot(1L), new StockLot(2L)), stockLots);
    }

}
