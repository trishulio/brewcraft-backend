package io.company.brewcraft.dto;

public class MaterialPortionDto extends BaseDto {
	
	private Long id;
    
	private MaterialLotDto materialLot;
    
    private QuantityDto quantity;

	public MaterialPortionDto(Long id, MaterialLotDto materialLot, QuantityDto quantity) {
		super();
		this.id = id;
		this.materialLot = materialLot;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MaterialLotDto getMaterialLot() {
		return materialLot;
	}

	public void setMaterialLot(MaterialLotDto materialLot) {
		this.materialLot = materialLot;
	}

	public QuantityDto getQuantity() {
		return quantity;
	}

	public void setQuantity(QuantityDto quantity) {
		this.quantity = quantity;
	}
}