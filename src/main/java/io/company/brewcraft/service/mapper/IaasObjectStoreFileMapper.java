package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddIaasObjectStoreFileDto;
import io.company.brewcraft.dto.IaasObjectStoreFileDto;
import io.company.brewcraft.dto.UpdateIaasObjectStoreFileDto;
import io.company.brewcraft.model.IaasObjectStoreFile;

@Mapper
public interface IaasObjectStoreFileMapper extends BaseMapper<IaasObjectStoreFile, IaasObjectStoreFileDto, AddIaasObjectStoreFileDto, UpdateIaasObjectStoreFileDto> {
    final IaasObjectStoreFileMapper INSTANCE = Mappers.getMapper(IaasObjectStoreFileMapper.class);

    @Override
    @Mapping(target = IaasObjectStoreFile.ATTR_ID, ignore = true)
    @Mapping(target = IaasObjectStoreFile.ATTR_FILE_KEY, ignore = true)
    @Mapping(target = IaasObjectStoreFile.ATTR_FILE_URL, ignore = true)
    @Mapping(target = IaasObjectStoreFile.ATTR_EXPIRATION, source = "expiration")
    IaasObjectStoreFile fromAddDto(AddIaasObjectStoreFileDto dto);

    @Override
    @Mapping(target = IaasObjectStoreFile.ATTR_ID, ignore = true)
    @Mapping(target = IaasObjectStoreFile.ATTR_FILE_URL, ignore = true)
    @Mapping(target = IaasObjectStoreFile.ATTR_EXPIRATION, source = "expiration")
    IaasObjectStoreFile fromUpdateDto(UpdateIaasObjectStoreFileDto dto);

    @Override
    @Mapping(target = "fileKey", source = IaasObjectStoreFile.ATTR_FILE_KEY)
    @Mapping(target = "fileUrl", source = IaasObjectStoreFile.ATTR_FILE_URL)
    @Mapping(target = "expiration", source = IaasObjectStoreFile.ATTR_EXPIRATION)
    IaasObjectStoreFileDto toDto(IaasObjectStoreFile e);
}