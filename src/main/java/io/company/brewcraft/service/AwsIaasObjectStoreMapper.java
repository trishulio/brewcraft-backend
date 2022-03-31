package io.company.brewcraft.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.amazonaws.services.s3.model.Bucket;

import io.company.brewcraft.model.IaasObjectStore;

@Mapper(uses = LocalDateTimeMapper.class)
public interface AwsIaasObjectStoreMapper extends IaasEntityMapper<Bucket, IaasObjectStore> {
    final AwsIaasObjectStoreMapper INSTANCE = Mappers.getMapper(AwsIaasObjectStoreMapper.class);

    @Override
    @Mapping(ignore = true, target = IaasObjectStore.ATTR_ID) // Name is the ID
    @Mapping(source = "name", target = IaasObjectStore.ATTR_NAME)
    @Mapping(source = "creationDate", target = IaasObjectStore.ATTR_CREATED_AT)
    @Mapping(ignore = true, target = IaasObjectStore.ATTR_LAST_UPDATED)
    IaasObjectStore fromIaasEntity(Bucket bucket);
}
