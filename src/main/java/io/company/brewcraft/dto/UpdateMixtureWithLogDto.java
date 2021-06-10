package io.company.brewcraft.dto;

public class UpdateMixtureWithLogDto extends BaseDto {
	
	private UpdateMixtureDto updateMixtureDto;
	
	private AddBrewLogDto addBrewLogDto;

	public UpdateMixtureWithLogDto(UpdateMixtureDto updateMixtureDto, AddBrewLogDto addBrewLogDto) {
		super();
		this.updateMixtureDto = updateMixtureDto;
		this.addBrewLogDto = addBrewLogDto;
	}

	public UpdateMixtureDto getUpdateMixtureDto() {
		return updateMixtureDto;
	}

	public void setUpdateMixtureDto(UpdateMixtureDto updateMixtureDto) {
		this.updateMixtureDto = updateMixtureDto;
	}

	public AddBrewLogDto getAddBrewLogDto() {
		return addBrewLogDto;
	}

	public void setAddBrewLogDto(AddBrewLogDto addBrewLogDto) {
		this.addBrewLogDto = addBrewLogDto;
	}
}
