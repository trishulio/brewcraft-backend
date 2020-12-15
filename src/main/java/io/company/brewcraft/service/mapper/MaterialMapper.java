package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialEntity;

@Mapper
public interface MaterialMapper {

    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    Material fromEntity(MaterialEntity entity);

    Material fromDto(MaterialDto dto);

    MaterialDto toDto(Material material);

    MaterialEntity toEntity(Material material);
}
