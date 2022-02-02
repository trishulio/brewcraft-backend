package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.CrudEntity;

@Entity(name = "tenant_iaasRole")
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class TenantIaasRole extends BaseEntity implements UpdateTenantIaasRole, CrudEntity<Long>, Audited {
    private static final Logger log = LoggerFactory.getLogger(TenantIaasRole.class);

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenant_iaasRole_generator")
    @SequenceGenerator(name = "tenant_iaas_role_generator", sequenceName = "tenant_iaas_role_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    @ManyToOne
    private Tenant tenant;
    
    @OneToMany
    public List<TenantPolicy> policies;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public TenantIaasRole() {
        super();
    }

    public TenantIaasRole(Long id) {
        this();
        setId(id);
    }

    public TenantIaasRole(Long id, String roleName, Tenant tenant, List<TenantPolicy> policies, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setRoleName(roleName);
        setTenant(tenant);
        setPolicies(policies);
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
    public String getRoleName() {
        return roleName;
    }

    @Override
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public Tenant getTenant() {
        return tenant;
    }

    @Override
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public List<TenantPolicy> getPolicies() {
        return policies;
    }

    @Override
    public void setPolicies(List<TenantPolicy> policies) {
        this.policies = policies;
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

    public void setVersion(Integer version) {
        this.version = version;
    }
}