package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.repository.StockLotRepository;

public class StockLotServiceImpl implements StockLotService {
    private static final Logger log = LoggerFactory.getLogger(StockLotServiceImpl.class);

    private StockLotRepository stockLotRepository;

    public StockLotServiceImpl(StockLotRepository stockLotRepository) {
        this.stockLotRepository = stockLotRepository;
    }

    public List<StockLot> getAllByIds(Set<Long> ids) {
        return stockLotRepository.findAllById(ids);
    }
}
