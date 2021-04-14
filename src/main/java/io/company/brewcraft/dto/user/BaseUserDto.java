package io.company.brewcraft.dto.user;

import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserTitle;

public interface BaseUserDto {

    String getDisplayName();

    String getFirstName();

    String getLastName();

    String getEmail();

    UserTitle getTitle();

    String getImageUrl();

    String getPhoneNumber();

    UserStatus getStatus();
}
