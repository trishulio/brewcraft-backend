package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;

public interface FinishedGoodLotFinishedGoodLotPortionAccessor {
    final String ATTR_FINISHED_GOOD_LOT_PORTION = "finishedGoodLotPortion";

    FinishedGoodLotFinishedGoodLotPortion getFinishedGoodLotPortion();

    void setFinishedGoodLotPortion(FinishedGoodLotFinishedGoodLotPortion finishedGoodLotPortion);
}
