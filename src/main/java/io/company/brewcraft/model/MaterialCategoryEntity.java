package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

@Entity(name = "MATERIAL_CATEGORY")
public class MaterialCategoryEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_PARENT_CATEGORY = "parentCategory";
    public static final String FIELD_SUBCATEGORIES = "subcategories";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_category_generator")
    @SequenceGenerator(name = "material_category_generator", sequenceName = "material_category_sequence", allocationSize = 1)
    private Long id;
    
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    private MaterialCategoryEntity parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private Set<MaterialCategoryEntity> subcategories;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;
    
    public MaterialCategoryEntity() {
        
    }

    public MaterialCategoryEntity(Long id, String name, MaterialCategoryEntity parentCategory, Set<MaterialCategoryEntity> subcategories,
            LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super();
        this.id = id;
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

    public MaterialCategoryEntity getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(MaterialCategoryEntity parentCategory) {
        this.parentCategory = parentCategory;
        
        if (parentCategory != null) {
            parentCategory.addSubcategory(this);
        }
    }

    public Set<MaterialCategoryEntity> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<MaterialCategoryEntity> subcategories) {
        if (subcategories != null) {
            subcategories.stream().forEach(subcategory -> subcategory.setParentCategory(this));
        }
        
        if (this.getSubcategories() != null) {
            this.getSubcategories().clear();
            this.getSubcategories().addAll(subcategories);
        } else {
            this.subcategories = subcategories;
        }
    }
    
    public void addSubcategory(MaterialCategoryEntity subcategory) {
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
    
    public void removeSubcategory(MaterialCategoryEntity subcategory) {
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
 
}
