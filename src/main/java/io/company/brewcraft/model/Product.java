package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "PRODUCT")
public class Product extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_DELETED_AT = "deletedAt";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", sequenceName = "product_sequence", allocationSize = 1)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "product_category_id", referencedColumnName = "id", nullable = false)
    private ProductCategory category;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductMeasureValue> targetMeasures;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @Version
    private Integer version;

    public Product() {
        super();
    }

    public Product(Long id, String name, String description, ProductCategory category, List<ProductMeasureValue> targetMeasures, 
            LocalDateTime createdAt, LocalDateTime lastUpdated, LocalDateTime deletedAt, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.targetMeasures = targetMeasures;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.deletedAt = deletedAt;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public List<ProductMeasureValue> getTargetMeasures() {
        return targetMeasures;
    }

    public void setTargetMeasures(List<ProductMeasureValue> trgtMeasures) {
        if (trgtMeasures != null) {
            trgtMeasures.stream().forEach(measure -> measure.setProduct(this));
        }
        
        if (this.getTargetMeasures() != null) {
            this.getTargetMeasures().clear();
            this.getTargetMeasures().addAll(trgtMeasures);
        } else {
            this.targetMeasures = trgtMeasures;
        }    
    }
    
    public void AddTargetMeasure(ProductMeasureValue productMeasureValue) {
        if (targetMeasures == null) {
            targetMeasures = new ArrayList<>();
        }
        
        if (productMeasureValue.getProduct() != this) {
            productMeasureValue.setProduct(this);
        }

        if (!targetMeasures.contains(productMeasureValue)) {
            this.targetMeasures.add(productMeasureValue);
        }
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    } 
}
