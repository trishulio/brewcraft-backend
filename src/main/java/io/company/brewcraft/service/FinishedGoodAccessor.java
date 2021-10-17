package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGood;

public interface FinishedGoodAccessor {
    final String ATTR_MIXTURE = "finishedGood";

    FinishedGood getFinishedGood();

    void setFinishedGood(FinishedGood mixture);
}
