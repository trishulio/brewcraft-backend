package io.company.brewcraft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "material")
@Table
public class MaterialEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_generator")
    @SequenceGenerator(name = "material_generator", sequenceName = "material_sequence", allocationSize = 1)
    private Long id;

    public MaterialEntity() {
        this(null);
    }

    public MaterialEntity(Long id) {
        setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
