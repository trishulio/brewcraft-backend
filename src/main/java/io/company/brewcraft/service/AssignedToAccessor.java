package io.company.brewcraft.service;

import io.company.brewcraft.model.user.User;

public interface AssignedToAccessor {
    final String ATTR_ASSIGNED_TO = "assignedTo";

    User getAssignedTo();

    void setAssignedTo(User user);
}
