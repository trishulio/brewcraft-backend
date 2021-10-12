package io.company.brewcraft.service;

import io.company.brewcraft.model.IdentityAccessor;
import io.company.brewcraft.model.Versioned;

public interface UpdatableEntity<ID> extends IdentityAccessor<ID>, Versioned {
}
