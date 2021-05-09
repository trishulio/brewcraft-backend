package io.company.brewcraft.model.user;

import java.util.List;

interface BaseUser<T extends BaseUserRole<? extends BaseUserRoleType, ? extends BaseUser<T>>> extends UserStatusAccessor, UserSalutationAccessor {
    final String ATTR_DISPLAY_NAME = "displayName";
    final String ATTR_FIRST_NAME = "firstName";
    final String ATTR_LAST_NAME = "lastName";
    final String ATTR_EMAIL = "email";
    final String ATTR_IMAGE_URL = "imageUrl";
    final String ATTR_PHONE_NUMBER_ = "phoneNumber";
    final String ATTR_USER_NAME = "userName";
    final String ATTR_ROLES = "roles";

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

    String getUserName();

    void setUserName(String userName);

    List<T> getRoles();

    void setRoles(List<T> roles);
}
