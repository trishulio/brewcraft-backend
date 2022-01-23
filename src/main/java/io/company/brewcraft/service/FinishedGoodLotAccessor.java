package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodLot;

public interface FinishedGoodLotAccessor {
    final String ATTR_FINISHED_GOOD_LOT = "finishedGoodLot";

    FinishedGoodLot getFinishedGoodLot();

    void setFinishedGoodLot(FinishedGoodLot finishedGoodLot);
}
