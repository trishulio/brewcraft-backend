package io.company.brewcraft.dto;

public class EquipmentTypeDto extends BaseDto {
    private Long id;

    private String name;

    private Integer version;

    public EquipmentTypeDto() {
        super();
    }

    public EquipmentTypeDto(Long id) {
        this();
        this.id = id;
    }

    public EquipmentTypeDto(Long id, String name, Integer version) {
        this(id);
        this.name = name;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
