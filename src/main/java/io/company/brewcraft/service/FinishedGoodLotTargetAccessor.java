package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodLot;

public interface FinishedGoodLotTargetAccessor {
    final String ATTR_FINISHED_GOOD_LOT_TARGET = "finishedGoodLotTarget";

    FinishedGoodLot getFinishedGoodLotTarget();

    void setFinishedGoodLotTarget(FinishedGoodLot finishedGoodLotTarget);
}
