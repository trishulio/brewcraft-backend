package io.company.brewcraft.model.user;


import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.Identified;

@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity implements Identified<Long>, Audited, UpdateUserRole<UserRoleType, User> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ROLE_TYPE = "roleType";
    public static final String FIELD_USER = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_generator")
    @SequenceGenerator(name = "user_role_generator", sequenceName = "user_role_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_type_id", referencedColumnName = "id")
    private UserRoleType roleType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;
    
    public UserRole() {
    }
    
    public UserRole(Long id) {
        setId(id);
    }
    
    public UserRole(UserRoleType roleType, User user) {
        setRoleType(roleType);
        setUser(user);
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
    public UserRoleType getRoleType() {
        return roleType;
    }

    @Override
    public void setRoleType(UserRoleType userRoleType) {
        this.roleType = userRoleType;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
