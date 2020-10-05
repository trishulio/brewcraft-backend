package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.model.Tenant;

@Mapper
public interface TenantMapper {

    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

    TenantDto tenantToTenantDto(Tenant tenant);

    Tenant tenantDtoToTenant(TenantDto tenant);
}