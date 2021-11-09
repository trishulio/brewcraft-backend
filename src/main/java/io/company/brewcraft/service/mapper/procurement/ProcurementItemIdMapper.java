package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.model.procurement.ProcurementItemId;

@Mapper
public interface ProcurementItemIdMapper {
    final ProcurementItemIdMapper INSTANCE = Mappers.getMapper(ProcurementItemIdMapper.class);

    ProcurementItemIdDto toDto(ProcurementItemId id);

    ProcurementItemId fromDto(ProcurementItemIdDto id);
}
