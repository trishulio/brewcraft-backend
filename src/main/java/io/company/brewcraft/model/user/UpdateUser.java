package io.company.brewcraft.model.user;

import java.util.List;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateUser<R extends UpdateUserRole> extends UpdatableEntity<Long>, UserStatusAccessor, UserSalutationAccessor {
    String getDisplayName();

    void setDisplayName(String displayName);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getEmail();

    void setEmail(String email);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    List<R> getRoles();

    void setRoles(List<R> roles);
}
