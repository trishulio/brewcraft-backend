package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.pojo.Invoice;

@Mapper(uses = { QuantityMapper.class, MoneyMapper.class, MaterialMapper.class })
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);
    
    InvoiceDto toDto(Invoice invoice);
    
    InvoiceEntity toEntity(Invoice invoice);

    @Mappings({ @Mapping(target = "supplier", ignore = true) })
    Invoice fromDto(InvoiceDto dto);

    @Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "supplier", ignore = true) })
    Invoice fromDto(UpdateInvoiceDto dto);

    @Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "version", ignore = true), @Mapping(target = "supplier", ignore = true) })
    Invoice fromDto(AddInvoiceDto dto);

    Invoice fromEntity(InvoiceEntity entity);
}
