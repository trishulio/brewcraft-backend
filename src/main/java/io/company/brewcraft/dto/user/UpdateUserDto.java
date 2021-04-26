package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.common.FixedTypeDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdateUserDto extends BaseDto implements BaseUserDto {

    private String displayName;

    private String firstName;

    private String lastName;

    private String email;

    private FixedTypeDto status;

    private FixedTypeDto salutation;

    private String phoneNumber;

    private List<AddUserRoleDto> roles;

    private String imageUrl;

    @NotNull
    private Integer version;

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public FixedTypeDto getStatus() {
        return status;
    }

    public void setStatus(FixedTypeDto status) {
        this.status = status;
    }

    @Override
    public FixedTypeDto getSalutation() {
        return salutation;
    }

    public void setSalutation(FixedTypeDto salutation) {
        this.salutation = salutation;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public List<AddUserRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<AddUserRoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
