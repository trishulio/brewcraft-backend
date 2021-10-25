package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGood;

public interface FinishedGoodAccessor {
    final String ATTR_FINISHED_GOOD = "finishedGood";

    FinishedGood getFinishedGood();

    void setFinishedGood(FinishedGood finishedGood);
}
