package io.company.brewcraft.model;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.CrudEntity;

@Entity(name = "tenant")
@Table(name="TENANT")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Tenant extends BaseEntity implements UpdateTenant, CrudEntity<UUID>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_URL = "url";
    public static final String FIELD_IS_READY = "isReady";

    @Id
    @GeneratedValue()
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private URL url;
    
    @Column(name = "is_ready")
    private Boolean isReady;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Tenant() {
        super();
        this.isReady = false;
    }

    public Tenant(UUID id) {
        setId(id);
    }

    public Tenant(UUID id, String name, URL url, Boolean isReady, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this(id);
        this.name = name;
        this.url = url;
        this.isReady = isReady;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
    

//    @Override
//    public String getIaasId() {
//        String iaasId = null;
//        if (getId() != null) {
//            iaasId = getId().toString();
//        }
//        
//        return iaasId;
//    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public Boolean getIsReady() {
        return isReady;
    }

    @Override
    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
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
        // Not implemented due to lack of use-case
        return -1;
    }
}
