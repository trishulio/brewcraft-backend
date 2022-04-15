package io.company.brewcraft.service;

import io.company.brewcraft.model.Facility;

public interface FacilityAccessor {
    final String ATTR_FACILITY = "facility";

    Facility getFacility();

    void setFacility(Facility facility);
}
