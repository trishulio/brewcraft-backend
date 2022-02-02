package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.CrudEntity;

@Entity(name = "tenant_policy")
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class TenantPolicy extends BaseEntity implements UpdateTenantPolicy, CrudEntity<Long>, Audited {
    private static final Logger log = LoggerFactory.getLogger(TenantPolicy.class);

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenant_policy_generator")
    @SequenceGenerator(name = "tenant_policy_generator", sequenceName = "tenant_policy_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "iaas_resource_name")
    private String iaasResourceName;

    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    @ManyToOne
    private Tenant tenant;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public TenantPolicy() {
        super();
    }

    public TenantPolicy(Long id) {
        this();
        setId(id);
    }

    public TenantPolicy(Long id, String iaasResourceName, Tenant tenant, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setIaasResourceName(iaasResourceName);
        setTenant(tenant);
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
    public String getIaasResourceName() {
        return iaasResourceName;
    }

    @Override
    public void setIaasResourceName(String iaasResourceName) {
        this.iaasResourceName = iaasResourceName;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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
