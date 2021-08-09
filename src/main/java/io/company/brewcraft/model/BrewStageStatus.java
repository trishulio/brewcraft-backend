package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "BREW_STAGE_STATUS")
public class BrewStageStatus extends BaseEntity implements BaseBrewStageStatus, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    public static final String STATUS_IN_PROGRESS = "IN-PROGRESS";
    public static final String STATUS_COMPLETE = "COMPLETE";
    public static final String STATUS_FAILED = "FAILED";
    public static final String STATUS_SPLIT = "SPLIT";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brew_stage_status_generator")
    @SequenceGenerator(name = "brew_stage_status_generator", sequenceName = "brew_stage_status_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public BrewStageStatus() {
    }

    public BrewStageStatus(Long id) {
        this();
        setId(id);
    }

    public BrewStageStatus(Long id, String name) {
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
