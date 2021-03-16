package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.pojo.PurchaseOrder;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

    PurchaseOrder fromDto(PurchaseOrderDto dto);

    PurchaseOrderDto toDto(PurchaseOrder po);
}
