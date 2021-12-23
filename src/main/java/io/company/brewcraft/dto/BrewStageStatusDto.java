package io.company.brewcraft.dto;


public class BrewStageStatusDto extends BaseDto {
    private Long id;

    private String name;

    public BrewStageStatusDto() {
    }

    public BrewStageStatusDto(Long id) {
        setId(id);
    }

    public BrewStageStatusDto(Long id, String name) {
        this(id);
        setName(name);
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
}
