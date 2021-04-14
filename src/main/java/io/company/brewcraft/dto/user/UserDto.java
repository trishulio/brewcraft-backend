package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserTitle;

public class UserDto extends BaseDto implements BaseUserDto {

    private Long id;

    private String displayName;

    private String firstName;

    private String lastName;

    private String email;

    private UserTitle title;

    private String imageUrl;

    private String phoneNumber;

    private UserStatus status;

    private Integer version;

    public UserDto(){

    }

    public UserDto(Long id, String displayName, String firstName, String lastName, String email, UserTitle title, String imageUrl, String phoneNumber, UserStatus status, Integer version) {
        this.id = id;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public UserTitle getTitle() {
        return title;
    }

    public void setTitle(UserTitle title) {
        this.title = title;
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
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
