package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.MeasureDto;
import io.company.brewcraft.model.Measure;

@Mapper(uses = {  })
public interface MeasureMapper {

    MeasureMapper INSTANCE = Mappers.getMapper(MeasureMapper.class);

    Measure fromDto(Long id);

    Measure fromDto(MeasureDto dto);

    MeasureDto toDto(Measure measure);
}
