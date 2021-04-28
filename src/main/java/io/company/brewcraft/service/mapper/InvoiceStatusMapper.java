package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.model.InvoiceStatus;

@Mapper
public interface InvoiceStatusMapper {
    InvoiceStatusMapper INSTANCE = Mappers.getMapper(InvoiceStatusMapper.class);

    @Mappings({
        @Mapping(target = InvoiceStatus.ATTR_ID)
    })
    InvoiceStatus fromDto(String id);

    @Mappings({
        @Mapping(target = InvoiceStatus.ATTR_ID, source = "name"),
    })
    InvoiceStatus fromDto(InvoiceStatusDto status);

    @Mappings({
        @Mapping(target = "name", source = InvoiceStatus.ATTR_ID)
    })
    InvoiceStatusDto toDto(InvoiceStatus status);
}
