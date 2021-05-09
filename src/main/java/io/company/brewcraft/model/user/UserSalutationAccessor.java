package io.company.brewcraft.model.user;

public interface UserSalutationAccessor {
    final String ATTR_SALUTATION = "salutation";

    UserSalutation getSalutation();

    void setSalutation(UserSalutation salutation);
}
