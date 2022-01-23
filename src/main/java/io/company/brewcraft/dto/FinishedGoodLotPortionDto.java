package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class FinishedGoodLotPortionDto extends BaseDto {

    private Long id;

    private Long finishedGoodLotId;

    private QuantityDto quantity;

    private Integer version;

    public FinishedGoodLotPortionDto() {
        super();
    }

    public FinishedGoodLotPortionDto(Long id) {
        this();
        this.id = id;
    }

    public FinishedGoodLotPortionDto(Long id, Long finishedGoodLotId, QuantityDto quantity, Integer version) {
        this(id);
        this.finishedGoodLotId = finishedGoodLotId;
        this.quantity = quantity;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFinishedGoodLotId() {
        return finishedGoodLotId;
    }

    public void setFinishedGoodLotId(Long finishedGoodLotId) {
        this.finishedGoodLotId = finishedGoodLotId;
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