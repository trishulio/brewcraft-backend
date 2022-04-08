package io.company.brewcraft.model;

import java.util.List;

public interface MaterialPortionsAccessor<T> {
    List<T> getMaterialPortions();

    void setMaterialPortions(List<T> materialPortions);

}
