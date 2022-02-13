package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.model.IaasAuthorizationDto;

@Mapper
public interface IaasAuthorizationMapper {
    static final IaasAuthorizationMapper INSTANCE = Mappers.getMapper(IaasAuthorizationMapper.class);

    IaasAuthorizationDto toDto(IaasAuthorization authorization);
}
