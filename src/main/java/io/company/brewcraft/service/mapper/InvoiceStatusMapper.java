package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.ShipmentStatus;

@Mapper
public interface InvoiceStatusMapper {
    InvoiceStatusMapper INSTANCE = Mappers.getMapper(InvoiceStatusMapper.class);

    @Mapping(target = InvoiceStatus.ATTR_ID)
    @Mapping(target = InvoiceStatus.ATTR_VERSION, ignore = true)
    @Mapping(target = InvoiceStatus.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = InvoiceStatus.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = InvoiceStatus.ATTR_NAME, ignore = true)
    InvoiceStatus fromDto(Long id);

    @Mapping(target = InvoiceStatus.ATTR_ID, source = "id")
    InvoiceStatus fromDto(InvoiceStatusDto invoiceStatus);

    @Mapping(target = "id", source = InvoiceStatus.ATTR_ID)
    InvoiceStatusDto toDto(InvoiceStatus invoiceStatus);
}
