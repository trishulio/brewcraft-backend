package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.common.FixedTypeDto;

import java.util.List;

public interface BaseUserDto {

    String getDisplayName();

    String getFirstName();

    String getLastName();

    String getEmail();

    FixedTypeDto getStatus();

    FixedTypeDto getSalutation();

    List<? extends BaseUserRoleDto> getRoles();

    String getImageUrl();

    String getPhoneNumber();
}
