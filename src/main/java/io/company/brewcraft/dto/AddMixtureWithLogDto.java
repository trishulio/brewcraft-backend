package io.company.brewcraft.dto;

public class AddMixtureWithLogDto extends BaseDto {
	
	private AddMixtureDto addMixtureDto;
	
	private AddBrewLogDto addBrewLogDto;

	public AddMixtureWithLogDto(AddMixtureDto addMixtureDto, AddBrewLogDto addBrewLogDto) {
		super();
		this.addMixtureDto = addMixtureDto;
		this.addBrewLogDto = addBrewLogDto;
	}

	public AddMixtureDto getAddMixtureDto() {
		return addMixtureDto;
	}

	public void setAddMixtureDto(AddMixtureDto addMixtureDto) {
		this.addMixtureDto = addMixtureDto;
	}

	public AddBrewLogDto getAddBrewLogDto() {
		return addBrewLogDto;
	}

	public void setAddBrewLogDto(AddBrewLogDto addBrewLogDto) {
		this.addBrewLogDto = addBrewLogDto;
	}
}
