package io.company.brewcraft.dto.user;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.NullOrNotBlank;

public class UpdateUserDto extends BaseDto {

    @NullOrNotBlank
    private String userName;

    @NullOrNotBlank
    private String displayName;

    @NullOrNotBlank
    private String firstName;

    @NullOrNotBlank
    private String lastName;

    private Long statusId;

    private Long salutationId;

    @NullOrNotBlank
    private String phoneNumber;

    private String imageUrl;

    @Size(min = 1)
    private List<Long> roleIds;

    @NotNull
    private Integer version;

    public UpdateUserDto() {
    }

    public UpdateUserDto(String userName, String displayName, String firstName, String lastName, Long statusId, Long salutationId, String phoneNumber, String imageUrl, List<Long> roleIds, @NotNull Integer version) {
        setUserName(userName);
        setDisplayName(displayName);
        setFirstName(firstName);
        setLastName(lastName);
        setStatusId(statusId);
        setSalutationId(salutationId);
        setPhoneNumber(phoneNumber);
        setRoleIds(roleIds);
        setImageUrl(imageUrl);
        setVersion(version);
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

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
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
