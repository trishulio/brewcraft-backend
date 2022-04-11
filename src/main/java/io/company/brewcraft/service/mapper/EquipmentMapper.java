package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityEquipmentDto;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Facility;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, AddressMapper.class })
public interface EquipmentMapper {
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    Equipment fromDto(Long id);

    FacilityBaseDto facilityToFacilityBaseDto(Facility facility);

    EquipmentDto toDto(Equipment equipment);

    FacilityEquipmentDto toFacilityEquipmentDto(Equipment equipment);

    Equipment fromDto(AddEquipmentDto equipment);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Equipment fromDto(UpdateEquipmentDto equipment);

    Equipment fromDto(FacilityEquipmentDto equipment);
}
