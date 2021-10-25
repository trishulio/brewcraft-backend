package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodMixturePortion;

public interface FinishedGoodMixturePortionAccessor {
    final String ATTR_MIXTURE_PORTION = "mixturePortion";

    FinishedGoodMixturePortion getMixturePortion();

    void setMixturePortion(FinishedGoodMixturePortion mixturePortion);
}
