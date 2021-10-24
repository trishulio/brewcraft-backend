package io.company.brewcraft.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.measure.Quantity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.util.QuantityCalculator;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class BaseMaterialPortionService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(BaseMaterialPortionService.class);

    private StockLotService stockLotService;

    public BaseMaterialPortionService(StockLotService stockLotService) {
        this.stockLotService = stockLotService;
    }

    protected Boolean isQuantityAvailable(Long stockLotId, Quantity<?> quantity) {
        Boolean result = false;

        Map<Long, Boolean> quantityCheckResult = this.areQuantitiesAvailable(Map.of(stockLotId, quantity));

        if(quantityCheckResult.get(stockLotId) == true) {
            result = true;
        }

        return result;
    }

    protected Map<Long, Boolean> areQuantitiesAvailable(Map<Long, Quantity<?>> lotIdToQuantity) {
        Map<Long, Boolean> result = new HashMap<>();
        lotIdToQuantity.forEach((lotId, quantity) -> result.put(lotId, false)); //init result map to false for all lot ids

        List<StockLot> stockLots = stockLotService.getAllByIds(lotIdToQuantity.keySet());

        if (stockLots != null && !stockLots.isEmpty()) {
            stockLots.forEach(stockLot -> {
                Quantity<?> requestedQuantity = lotIdToQuantity.get(stockLot.getId());

                Validator.assertion(requestedQuantity.getUnit().isCompatible(stockLot.getQuantity().getUnit()), RuntimeException.class, "Requested quantity unit is incompatible with material lot unit");

                Quantity<?> availableQuantity = stockLot.getQuantity();

                BigDecimal remainingQuantityValue = new BigDecimal(QuantityCalculator.subtract(availableQuantity, requestedQuantity).getValue().toString());

                //Requested quantity is only available if remaining quantity is >= 0
                if (remainingQuantityValue.compareTo(BigDecimal.ZERO) == 0 || remainingQuantityValue.compareTo(BigDecimal.ZERO) > 0) {
                    result.put(stockLot.getId(), true);
                }
            });
        }

        return result;
    }

}
