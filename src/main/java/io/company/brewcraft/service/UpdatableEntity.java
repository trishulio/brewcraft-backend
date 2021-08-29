package io.company.brewcraft.service;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Versioned;

public interface UpdatableEntity<ID> extends Identified<ID>, Versioned {
}
