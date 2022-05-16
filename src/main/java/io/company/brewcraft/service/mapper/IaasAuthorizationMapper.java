package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.IaasAuthorizationDto;
import io.company.brewcraft.model.IaasAuthorization;

@Mapper
public interface IaasAuthorizationMapper {
    static final IaasAuthorizationMapper INSTANCE = Mappers.getMapper(IaasAuthorizationMapper.class);

    @Mapping(source = IaasAuthorization.ATTR_ACCESS_KEY_ID, target = "accessKeyId")
    @Mapping(source = IaasAuthorization.ATTR_ACCESS_SECRET_KEY, target = "accessSecretKey")
    @Mapping(source = IaasAuthorization.ATTR_SESSION_TOKEN, target = "sessionToken")
    @Mapping(source = IaasAuthorization.ATTR_EXPIRATION, target = "expiration")
    IaasAuthorizationDto toDto(IaasAuthorization authorization);
}
