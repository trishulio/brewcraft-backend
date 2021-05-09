package io.company.brewcraft.dto;

import io.company.brewcraft.model.Versioned;

public interface UpdateMaterialCategory<T extends UpdateMaterialCategory<T>> extends BaseMaterialCategory<T>, Versioned {
}
