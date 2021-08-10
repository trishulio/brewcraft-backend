package io.company.brewcraft.service;

import io.company.brewcraft.model.Mixture;

public interface MixtureAccessor {
    final String ATTR_MIXTURE = "mixture";

    Mixture getMixture();

    void setMixture(Mixture mixture);
}
