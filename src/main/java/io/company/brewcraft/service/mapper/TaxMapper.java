package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.model.TaxEntity;
import io.company.brewcraft.pojo.Tax;

@Mapper(uses = { MoneyMapper.class })
public interface TaxMapper {
    TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

    Tax fromDto(TaxDto dto);

    TaxDto toDto(Tax tax);

    Tax fromEntity(TaxEntity entity);

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    TaxEntity toEntity(Tax tax);
}
