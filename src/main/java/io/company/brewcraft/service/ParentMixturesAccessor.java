package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.Mixture;

public interface ParentMixturesAccessor {
    final String ATTR_PARENT_MIXTURE = "parentMixtures";

    List<Mixture> getParentMixtures();

    void setParentMixtures(List<Mixture> parentMixtures);
}