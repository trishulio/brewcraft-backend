package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddTenantDto;
import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.dto.UpdateTenantDto;
import io.company.brewcraft.model.Tenant;

@Mapper
public interface TenantMapper extends BaseMapper<Tenant, TenantDto, AddTenantDto, UpdateTenantDto> {
    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

    @Override
    TenantDto toDto(Tenant invoice);

    @Override
    @Mapping(target = Tenant.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Tenant.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Tenant.ATTR_IS_READY, ignore = true)
    Tenant fromUpdateDto(UpdateTenantDto dto);

    @Override
    @Mapping(target = Tenant.ATTR_ID, ignore = true)
    @Mapping(target = Tenant.ATTR_VERSION, ignore = true)
    @Mapping(target = Tenant.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Tenant.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Tenant.ATTR_IS_READY, ignore = true)
    Tenant fromAddDto(AddTenantDto dto);
}