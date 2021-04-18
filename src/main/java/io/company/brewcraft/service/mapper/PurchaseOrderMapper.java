package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.model.PurchaseOrder;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

    default PurchaseOrder fromDto(Long id) {
        PurchaseOrder po = null;
        if (id != null) {
            po = new PurchaseOrder(id);
        }
        
        return po;
    }
    
    PurchaseOrder fromDto(PurchaseOrderDto dto);

    PurchaseOrderDto toDto(PurchaseOrder po);
}
