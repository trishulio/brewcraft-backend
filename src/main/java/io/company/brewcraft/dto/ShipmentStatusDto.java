package io.company.brewcraft.dto;

public class ShipmentStatusDto extends BaseDto {
    private Long id;
    private String name;

    public ShipmentStatusDto() {
    }

    public ShipmentStatusDto(String name) {
        this();
        setName(name);
    }

    public ShipmentStatusDto(Long id, String name) {
        this(name);
        setId(id);
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
