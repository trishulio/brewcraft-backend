package io.company.brewcraft.model.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.Identified;

@Entity
@Table(name = "_user")
public class User extends BaseEntity implements BaseUser<UserRole>, UpdateUser<UserRole>, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_NAME = "userName";
    public static final String FIELD_DISPLAY_NAME = "displayName";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PHONE_NUMBER = "phoneNumber";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_SALUTATION = "salutation";
    public static final String FIELD_ROLES = "roleBindings";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "user_name", updatable = false)
    private String userName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserRoleBinding> roleBindings;

    @Column(name = "image_url")
    @URL
    private String imageUrl;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id", referencedColumnName = "id")
    private UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_salutation_id", referencedColumnName = "id")
    private UserSalutation salutation;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    public User() {
    }
    
    public User(Long id) {
        this();
        setId(id);
    }
    
    public User(Long id, String userName, String displayName, String firstName, String lastName, String email, String phoneNumber, String imageUrl, UserStatus status, UserSalutation salutation, List<UserRole> roles, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setUserName(userName);
        setDisplayName(displayName);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setStatus(status);
        setImageUrl(imageUrl);
        setRoles(roles);
        setSalutation(salutation);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public UserStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setRoles(List<UserRole> roles) {
        // Reusing existing bindings instead of adding new to avoid creating dangling child entities.
        if (roleBindings == null) {
            roleBindings = new ArrayList<>();
        }

        Map<Long, UserRoleBinding> existingBindings = roleBindings
                                                      .stream()
                                                      .collect(Collectors.toMap(
                                                          binding -> binding.getRole().getId(),
                                                          binding -> binding
                                                      ));
        
        Map<Long, UserRoleBinding> newBindings = roles.stream().collect(Collectors.toMap(role -> role.getId(), role -> {
            UserRoleBinding existing = existingBindings.remove(role.getId());
            if (existing == null) {
                existing = new UserRoleBinding();
            }
            
            existing.setRole(role);
            existing.setUser(this);
            
            return existing;
        }));
        
        this.roleBindings.clear();
        
        newBindings.forEach((roleId, binding) -> {
            this.roleBindings.add(binding);
        });        
    }

    @Override
    public List<UserRole> getRoles() {
        if (this.roleBindings == null) {
            return null;
        }
        
        return this.roleBindings.stream()
                                .map(binding -> binding.getRole())
                                .collect(Collectors.toList());
    }
    
    /**
     * Used by the repository to directly access the bindings.
     * Refrain from using this is business logic. Prefer, getRoles()
     * method. 
     * @return
     */
    public List<UserRoleBinding> getRoleBindings() {
        return this.roleBindings;
    }

    @Override
    public UserSalutation getSalutation() {
        return salutation;
    }

    @Override
    public void setSalutation(UserSalutation salutation) {
        this.salutation = salutation;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
