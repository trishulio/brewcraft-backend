package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "SKU")
public class Sku extends BaseEntity implements UpdateSku<SkuMaterial>, CrudEntity<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_PRODUCT = "product";
    public static final String FIELD_MATERIALS = "materials";
    public static final String FIELD_QUANTITY = "quantity";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sku_generator")
    @SequenceGenerator(name = "sku_generator", sequenceName = "sku_sequence", allocationSize = 1)
    private Long id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SkuMaterial> materials;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "quantity_value")) })
    @AssociationOverrides({
            @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "quantity_unit", referencedColumnName = "symbol")) })
    private QuantityEntity quantity;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public Sku() {
    }

    public Sku(Long id) {
        this();
        setId(id);
    }

    public Sku(Long id, String name, String description, Product product, List<SkuMaterial> materials, Quantity<?> quantity, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setName(name);
        setDescription(description);
        setProduct(product);
        setMaterials(materials);
        setQuantity(quantity);
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public List<SkuMaterial> getMaterials() {
        return materials;
    }

    @Override
    public void setMaterials(List<SkuMaterial> materials) {
        if (this.materials != null) {
            if (materials == null) {
                this.materials.clear();
            } else {
                this.materials.stream().filter(material -> !materials.contains(material)).collect(Collectors.toList()).forEach(this::removeMaterial);
            }
        }

        if (materials != null) {
            if (this.materials == null) {
                this.materials = new ArrayList<>();
                materials.stream().collect(Collectors.toList()).forEach(this::addMaterial);
            } else {
                materials.stream().filter(material -> !this.materials.contains(material)).collect(Collectors.toList()).forEach(this::addMaterial);
            }
        }
    }

    public void addMaterial(SkuMaterial material) {
        if (material == null) {
            return;
        }

        if (this.materials == null) {
            this.materials = new ArrayList<>();
        }

        if (material.getSku() != this) {
            if (material.getSku() != null) {
                material.getSku().removeMaterial(material);
            }
            material.setSku(this);
        }

        if (!this.materials.contains(material)) {
            this.materials.add(material);
        }
    }

    public boolean removeMaterial(SkuMaterial material) {
        if (material == null || this.materials == null) {
            return false;
        }

        boolean removed = this.materials.remove(material);

        if (removed) {
            material.setSku(null);
        }

        return removed;
    }

    @Override
    public Quantity<?> getQuantity() {
        return QuantityMapper.INSTANCE.fromEntity(this.quantity);
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime created) {
        this.createdAt = created;
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
