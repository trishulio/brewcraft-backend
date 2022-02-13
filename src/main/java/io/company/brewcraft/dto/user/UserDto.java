package io.company.brewcraft.dto.user;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.dto.BaseDto;

public class UserDto extends BaseDto {

    private Long id;

    private String userName;

    private String displayName;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String imageSrc;

    private UserStatusDto status;

    private UserSalutationDto salutation;

    private List<UserRoleDto> roles;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private Integer version;

    public UserDto() {
    }

    public UserDto(Long id) {
        this();
        setId(id);
    }

    public UserDto(Long id, String userName, String displayName, String firstName, String lastName, String email, String phoneNumber, String imageSrc, UserStatusDto status, UserSalutationDto salutation, List<UserRoleDto> roles, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setUserName(userName);
        setDisplayName(displayName);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setImageSrc(imageSrc);
        setStatus(status);
        setSalutation(salutation);
        setRoles(roles);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setLastName(lastName);
        setVersion(version);
    }

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

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
