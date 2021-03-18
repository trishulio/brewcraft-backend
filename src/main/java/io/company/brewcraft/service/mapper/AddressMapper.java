package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.model.FacilityAddressEntity;
import io.company.brewcraft.model.SupplierAddress;


@Mapper()
public interface AddressMapper {
    
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
        
    AddressDto addressToAddressDto(FacilityAddressEntity address);
    
    AddressDto addressToAddressDto(SupplierAddress address);

    SupplierAddress addressDtoToSupplierAddress(AddressDto addressDto);
    
    FacilityAddressEntity addressDtoToFacilityAddress(AddressDto addressDto);
            
}
