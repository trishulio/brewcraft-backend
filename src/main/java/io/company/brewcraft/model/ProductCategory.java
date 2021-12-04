package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.persistence.Column;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "PRODUCT_CATEGORY")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class ProductCategory extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_PARENT_CATEGORY = "parentCategory";
    public static final String FIELD_SUBCATEGORIES = "subcategories";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_category_generator")
    @SequenceGenerator(name = "product_category_generator", sequenceName = "product_category_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    private ProductCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    @JsonIgnore
    private Set<ProductCategory> subcategories;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public ProductCategory() {
        super();
    }

    public ProductCategory(Long id) {
        this();
        this.id = id;
    }

    public ProductCategory(Long id, String name, ProductCategory parentCategory, Set<ProductCategory> subcategories,
            LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        this.name = name;
        this.parentCategory = parentCategory;
        this.subcategories = subcategories;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
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

    public ProductCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ProductCategory parentCategory) {
        this.parentCategory = parentCategory;

        if (parentCategory != null) {
            parentCategory.addSubcategory(this);
        }
    }

    public Set<ProductCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<ProductCategory> subcategories) {
        if (subcategories != null) {
            subcategories.forEach(subcategory -> subcategory.setParentCategory(this));
        }

        if (this.getSubcategories() != null) {
            this.getSubcategories().clear();
            this.getSubcategories().addAll(subcategories);
        } else {
            this.subcategories = subcategories;
        }
    }

    public void addSubcategory(ProductCategory subcategory) {
        if (this.subcategories == null) {
            this.subcategories = new HashSet<>();
        }

        if (subcategory.getParentCategory() != this) {
            subcategory.setParentCategory(this);
        }

        if (!subcategories.contains(subcategory)) {
            this.subcategories.add(subcategory);
        }
    }

    public void removeSubcategory(ProductCategory subcategory) {
        if (this.subcategories != null) {
            subcategory.setParentCategory(null);
            this.subcategories.remove(subcategory);
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created) {
        this.createdAt = created;
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

    @JsonIgnore
    public ProductCategory getRootCategory() {
        ProductCategory root = this;

        while (root.getParentCategory() != null) {
            root = root.getParentCategory();
        }

        return root;
    }

    /*
     * Returns all descendant category id's using iterative DFS
     */
    @JsonIgnore
    public Set<Long> getDescendantCategoryIds() {
        Set<Long> ids = new HashSet<>();
        Stack<ProductCategory> stack = new Stack<ProductCategory>();

        if (this.getSubcategories() != null) {
            stack.addAll(this.getSubcategories());
        }

        while (!stack.isEmpty()) {
            ProductCategory category = stack.pop();
            ids.add(category.getId());

            if (category.getSubcategories() != null) {
                stack.addAll(category.getSubcategories());
            }
        }

        return ids;
    }
}
