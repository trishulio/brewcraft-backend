package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import io.company.brewcraft.dto.UpdateCategory;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;

public class Category extends BaseModel implements UpdateCategory, Identified, Audited {

    private Long id;
    
    private String name;
    
    private Category parentCategory;

    private Set<Category> subcategories;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public Category() {
        super();
    }

    public Category(Long id, String name, Category parentCategory, Set<Category> subcategories,
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
        
        if (parentCategory != null) {
            parentCategory.addSubcategory(this);
        }
    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Category> subcategories) {
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
    
    public void addSubcategory(Category subcategory) {
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
    
    public void removeSubcategory(Category subcategory) {
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
    
    public Category getRootCategory() {
        Category root = this;
        
        while (root.getParentCategory() != null) {
            root = root.getParentCategory();
        }
        
        return root;        
    }
    
    /*
     * Returns all descendant category id's using iterative DFS
     */
    public Set<Long> getDescendantCategoryIds() {
        Set<Long> ids = new HashSet<>();
        Stack<Category> stack = new Stack<Category>();
        
        if (this.getSubcategories() != null) {
            stack.addAll(this.getSubcategories());
        }
        
        while (!stack.isEmpty()) {
            Category category = stack.pop();
            ids.add(category.getId());
            
            if (category.getSubcategories() != null) {
                stack.addAll(category.getSubcategories());
            }
        }
              
        return ids;        
    }
 
}
