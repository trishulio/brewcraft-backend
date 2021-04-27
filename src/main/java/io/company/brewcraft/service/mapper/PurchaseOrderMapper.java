package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.model.PurchaseOrder;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

    @Mappings({
        @Mapping(target = PurchaseOrder.ATTR_ID)
    })
    PurchaseOrder fromDto(Long id);
    
    PurchaseOrder fromDto(PurchaseOrderDto dto);

    PurchaseOrderDto toDto(PurchaseOrder po);
}
