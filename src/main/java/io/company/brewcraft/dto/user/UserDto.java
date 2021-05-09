package io.company.brewcraft.dto.user;

import java.util.List;

import io.company.brewcraft.dto.BaseDto;

public class UserDto extends BaseDto {

    private Long id;

    private String userName;

    private String displayName;

    private String firstName;

    private String lastName;

    private String email;

    private UserStatusDto status;

    private UserSalutationDto salutation;

    private String phoneNumber;

    private List<UserRoleDto> roles;

    private String imageUrl;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatusDto getStatus() {
        return status;
    }

    public void setStatus(UserStatusDto status) {
        this.status = status;
    }

    public UserSalutationDto getSalutation() {
        return salutation;
    }

    public void setSalutation(UserSalutationDto salutation) {
        this.salutation = salutation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
    }

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
