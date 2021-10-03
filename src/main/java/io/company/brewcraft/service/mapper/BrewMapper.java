package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddBrewDto;
import io.company.brewcraft.dto.BrewDto;
import io.company.brewcraft.dto.UpdateBrewDto;
import io.company.brewcraft.model.Brew;

@Mapper(uses = { ProductMapper.class })
public interface BrewMapper {

    BrewMapper INSTANCE = Mappers.getMapper(BrewMapper.class);

    @Mapping(target = "parentBrew", source = "parentBrewId")
    Brew fromDto(BrewDto dto);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Brew.ATTR_VERSION, ignore = true)
    @Mapping(target = "parentBrew", source = "parentBrewId")
    @Mapping(target = "product", source = "productId")
    Brew fromDto(AddBrewDto dto);

    Brew fromDto(Long id);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "parentBrew", source = "parentBrewId")
    @Mapping(target = "product", source = "productId")
    Brew fromDto(UpdateBrewDto dto);

    @Mapping(target = "parentBrewId", source = "parentBrew.id")
    BrewDto toDto(Brew brew);

}
