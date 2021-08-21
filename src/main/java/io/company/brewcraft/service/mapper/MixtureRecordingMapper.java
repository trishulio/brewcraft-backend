package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMixtureRecordingDto;
import io.company.brewcraft.dto.MixtureRecordingDto;
import io.company.brewcraft.dto.UpdateMixtureRecordingDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.MixtureRecording;

@Mapper(uses = { MeasureMapper.class, MixtureMapper.class })
public interface MixtureRecordingMapper {

    MixtureRecordingMapper INSTANCE = Mappers.getMapper(MixtureRecordingMapper.class);

    @Mapping(target = "mixtureId", source = "mixture.id")
    MixtureRecordingDto toDto(MixtureRecording mixtureRecording);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Brew.ATTR_VERSION, ignore = true)
    @Mapping(target = "measure", source = "measureId")
    @Mapping(target = "mixture", source = "mixtureId")
    MixtureRecording fromDto(AddMixtureRecordingDto dto);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "measure", source = "measureId")
    @Mapping(target = "mixture", source = "mixtureId")
    MixtureRecording fromDto(UpdateMixtureRecordingDto dto);
}
