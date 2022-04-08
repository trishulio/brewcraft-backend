package io.company.brewcraft.model;

import java.util.List;

public interface MixturePortionsAccessor<T> {
    List<T> getMixturePortions();

    void setMixturePortions(List<T> mixturePortions);

}
