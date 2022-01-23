package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodLotMixturePortion;

public interface FinishedGoodLotMixturePortionAccessor {
    final String ATTR_MIXTURE_PORTION = "mixturePortion";

    FinishedGoodLotMixturePortion getMixturePortion();

    void setMixturePortion(FinishedGoodLotMixturePortion mixturePortion);
}
