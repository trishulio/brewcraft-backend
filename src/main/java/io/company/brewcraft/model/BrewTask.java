package io.company.brewcraft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "BREW_TASK")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class BrewTask extends BaseEntity implements BaseBrewTask, IdentityAccessor<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brew_task_generator")
    @SequenceGenerator(name = "brew_task_generator", sequenceName = "brew_task_sequence", allocationSize = 1)
    private Long id;

    private String name;

    public BrewTask() {
        super();
    }

    public BrewTask(Long id) {
        this();
        setId(id);
    }

    public BrewTask(Long id, String name) {
        this(id);
        setName(name);
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
}
