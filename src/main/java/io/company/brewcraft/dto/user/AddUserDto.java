package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.common.FixedTypeDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AddUserDto extends BaseDto implements BaseUserDto {

    @NotEmpty
    private String userName;

    @NotEmpty
    private String displayName;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    @NotNull
    private FixedTypeDto status;

    @NotNull
    private FixedTypeDto salutation;

    @NotEmpty
    private String phoneNumber;

    private List<AddUserRoleDto> roles;

    private String imageUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    public List<AddUserRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<AddUserRoleDto> roles) {
        this.roles = roles;
    }
}
