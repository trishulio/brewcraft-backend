package io.company.brewcraft.dto;

public class BrewTaskDto extends BaseDto {
    private Long id;

    private String name;

    public BrewTaskDto() {
    }

    public BrewTaskDto(Long id) {
        setId(id);
    }

    public BrewTaskDto(Long id, String name) {
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
