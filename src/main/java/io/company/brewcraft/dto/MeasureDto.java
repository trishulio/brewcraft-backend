package io.company.brewcraft.dto;

public class MeasureDto extends BaseDto {

    private Long id;

    private String name;

    private Integer version;

    public MeasureDto() {
    }

    public MeasureDto(Long id) {
        setId(id);
    }

    public MeasureDto(Long id, String name, Integer version) {
        this(id);
        setName(name);
        setVersion(version);
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
