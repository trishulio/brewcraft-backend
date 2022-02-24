package io.company.brewcraft.dto;

public class FinishedGoodLotPortionDto extends BaseDto {

    private Long id;

    private FinishedGoodLotDto finishedGoodLot;

    private QuantityDto quantity;

    private Integer version;

    public FinishedGoodLotPortionDto() {
        super();
    }

    public FinishedGoodLotPortionDto(Long id) {
        this();
        this.id = id;
    }

    public FinishedGoodLotPortionDto(Long id, FinishedGoodLotDto finishedGoodLot, QuantityDto quantity, Integer version) {
        this(id);
        this.finishedGoodLot = finishedGoodLot;
        this.quantity = quantity;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinishedGoodLotDto getFinishedGoodLot() {
        return finishedGoodLot;
    }

    public void setFinishedGoodLot(FinishedGoodLotDto finishedGoodLot) {
        this.finishedGoodLot = finishedGoodLot;
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