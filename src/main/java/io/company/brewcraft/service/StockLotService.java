package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.StockLot;

public interface StockLotService {
    List<StockLot> getAllByIds(Set<Long> ids);

}
