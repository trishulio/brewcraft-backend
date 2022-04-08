package io.company.brewcraft.model;

import java.time.LocalDateTime;

import io.company.brewcraft.service.MixtureAccessor;

public interface BaseMixturePortion extends MixtureAccessor, QuantityAccessor {
    LocalDateTime getAddedAt();

    void setAddedAt(LocalDateTime addedAt);

}
