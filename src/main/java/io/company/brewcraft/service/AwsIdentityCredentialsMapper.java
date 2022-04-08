package io.company.brewcraft.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.amazonaws.services.cognitoidentity.model.Credentials;

import io.company.brewcraft.model.IaasAuthorization;

@Mapper(uses = LocalDateTimeMapper.class)
public interface AwsIdentityCredentialsMapper extends IaasEntityMapper<Credentials, IaasAuthorization> {
    final AwsIdentityCredentialsMapper INSTANCE = Mappers.getMapper(AwsIdentityCredentialsMapper.class);

    @Override
    @Mapping(ignore = true, target = IaasAuthorization.ATTR_ID) // AccessKey is the ID
    @Mapping(source = "accessKeyId", target = IaasAuthorization.ATTR_ACCESS_KEY)
    @Mapping(source = "secretKey", target = IaasAuthorization.ATTR_ACCESS_SECRET)
    @Mapping(source = "sessionToken", target = IaasAuthorization.ATTR_SESSION_TOKEN)
    @Mapping(source = "expiration", target = IaasAuthorization.ATTR_EXPIRATION)
    IaasAuthorization fromIaasEntity(Credentials credentials);
}
