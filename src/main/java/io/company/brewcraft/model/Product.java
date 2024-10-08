package io.company.brewcraft.model;

import java.net.URI;
import java.net.URISyntaxException;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.service.CriteriaJoin;

@Entity(name = "PRODUCT")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Product extends BaseEntity implements UpdateProduct, Identified<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_DELETED_AT = "deletedAt";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", sequenceName = "product_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_category_id", referencedColumnName = "id", nullable = false)
    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @CriteriaJoin
    private List<ProductMeasureValue> targetMeasures;

    @Column(name = "image_source")
    private String imageSrc;

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

    public Product(Long id) {
        this();
        this.id = id;
    }

    public Product(Long id, String name, String description, ProductCategory category, List<ProductMeasureValue> targetMeasures, URI imageSrc, LocalDateTime createdAt, LocalDateTime lastUpdated, LocalDateTime deletedAt, Integer version) {
        this(id);
        this.name = name;
        this.description = description;
        this.category = category;
        this.targetMeasures = targetMeasures;
        setImageSrc(imageSrc);
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.deletedAt = deletedAt;
        this.version = version;
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
    public ProductCategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    @Override
    public List<ProductMeasureValue> getTargetMeasures() {
        return targetMeasures;
    }

    @Override
    public void setTargetMeasures(List<ProductMeasureValue> trgtMeasures) {
        if (trgtMeasures != null) {
            trgtMeasures.forEach(measure -> measure.setProduct(this));
        }

        if (this.getTargetMeasures() != null) {
            this.getTargetMeasures().clear();
            this.getTargetMeasures().addAll(trgtMeasures);
        } else {
            this.targetMeasures = trgtMeasures;
        }
    }

    @Override
    public void addTargetMeasure(ProductMeasureValue productMeasureValue) {
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

    @Override
    public URI getImageSrc() {
        URI uri = null;
        if (this.imageSrc != null) {
            try {
                uri = new URI(this.imageSrc);
            } catch (URISyntaxException e) {
                throw new RuntimeException(String.format("Failed to convert to URI, value: %s", this.imageSrc), e);
            }
        }

        return uri;
    }

    @Override
    public void setImageSrc(URI imageSrc) {
        if (imageSrc != null) {
            this.imageSrc = imageSrc.toString();
        } else {
            this.imageSrc = null;
        }
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
