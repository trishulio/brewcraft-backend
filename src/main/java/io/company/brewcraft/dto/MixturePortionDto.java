package io.company.brewcraft.dto;

public class MixturePortionDto extends BaseDto {

    private Long id;
    
    private MixtureDto mixture;

    private QuantityDto quantity;
            
    private Integer version;

    public MixturePortionDto() {
        super();
    }

    public MixturePortionDto(Long id) {
        this();
        this.id = id;
    }

    public MixturePortionDto(Long id, MixtureDto mixture, QuantityDto quantity, Integer version) {
        this(id);
        this.mixture = mixture;
        this.quantity = quantity;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MixtureDto getMixture() {
        return mixture;
    }

    public void setMixture(MixtureDto mixture) {
        this.mixture = mixture;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}