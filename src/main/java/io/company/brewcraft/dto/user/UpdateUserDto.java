package io.company.brewcraft.dto.user;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.dto.BaseDto;

public class UpdateUserDto extends BaseDto {

    private String displayName;

    private String firstName;

    private String lastName;

    private String email;

    private Long statusId;

    private Long salutationId;

    private String phoneNumber;

    private String imageUrl;

    private List<UpdateUserRoleDto> roles;

    @NotNull
    private Integer version;

    public UpdateUserDto() { 
    }

    public UpdateUserDto(String displayName, String firstName, String lastName, String email, Long statusId, Long salutationId, String phoneNumber, String imageUrl, List<UpdateUserRoleDto> roles, @NotNull Integer version) {       
        setDisplayName(displayName);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setStatusId(statusId);
        setSalutationId(salutationId);
        setPhoneNumber(phoneNumber);
        setRoles(roles);
        setImageUrl(imageUrl);
        setVersion(version);
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getSalutationId() {
        return salutationId;
    }

    public void setSalutationId(Long salutationId) {
        this.salutationId = salutationId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<UpdateUserRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<UpdateUserRoleDto> roles) {
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
