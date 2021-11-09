package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.model.procurement.ProcurementId;

@Mapper
public interface ProcurementIdMapper {
    final ProcurementIdMapper INSTANCE = Mappers.getMapper(ProcurementIdMapper.class);

    ProcurementIdDto toDto(ProcurementId id);

    ProcurementId fromDto(ProcurementIdDto id);
}
