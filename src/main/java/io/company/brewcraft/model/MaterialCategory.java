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

import io.company.brewcraft.dto.UpdateMaterialCategory;

@Entity(name = "MATERIAL_CATEGORY")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class MaterialCategory extends BaseEntity implements UpdateMaterialCategory<MaterialCategory>, Audited, Identified<Long>{
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
    private MaterialCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    @JsonIgnore
    private Set<MaterialCategory> subcategories;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public MaterialCategory() {
        super();
    }

    public MaterialCategory(Long id) {
        this.id = id;
    }

    public MaterialCategory(Long id, String name, MaterialCategory parentCategory, Set<MaterialCategory> subcategories,
            LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        this.name = name;
        this.parentCategory = parentCategory;
        this.subcategories = subcategories;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
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
    public MaterialCategory getParentCategory() {
        return parentCategory;
    }

    @Override
    public void setParentCategory(MaterialCategory parentCategory) {
        this.parentCategory = parentCategory;

        if (parentCategory != null) {
            parentCategory.addSubcategory(this);
        }
    }

    @Override
    public Set<MaterialCategory> getSubcategories() {
        return subcategories;
    }

    @Override
    public void setSubcategories(Set<MaterialCategory> subcategories) {
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

    public void addSubcategory(MaterialCategory subcategory) {
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

    public void removeSubcategory(MaterialCategory subcategory) {
        if (this.subcategories != null) {
            subcategory.setParentCategory(null);
            this.subcategories.remove(subcategory);
        }
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

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonIgnore
    public MaterialCategory getRootCategory() {
        MaterialCategory root = this;

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
        Stack<MaterialCategory> stack = new Stack<MaterialCategory>();

        if (this.getSubcategories() != null) {
            stack.addAll(this.getSubcategories());
        }

        while (!stack.isEmpty()) {
            MaterialCategory category = stack.pop();
            ids.add(category.getId());

            if (category.getSubcategories() != null) {
                stack.addAll(category.getSubcategories());
            }
        }

        return ids;
    }

}
