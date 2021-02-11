package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.pojo.InvoiceStatus;

@Mapper
public interface InvoiceStatusMapper {
    InvoiceStatusMapper INSTANCE = Mappers.getMapper(InvoiceStatusMapper.class);

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    InvoiceStatus fromDto(String name);

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    InvoiceStatus fromDto(InvoiceStatusDto status);
    
    InvoiceStatusDto toDto(InvoiceStatus status);

    InvoiceStatus fromEntity(InvoiceStatusEntity entity);

    InvoiceStatusEntity toEntity(InvoiceStatus status);
}
