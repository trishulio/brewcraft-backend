package io.company.brewcraft.service.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;

import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.service.IaasEntityMapper;
import io.company.brewcraft.service.LocalDateTimeMapper;

@Mapper(uses = { LocalDateTimeMapper.class })
public interface AwsCognitoAdminGetUserResultMapper extends IaasEntityMapper<AdminGetUserResult, IaasUser> {
    final AwsCognitoAdminGetUserResultMapper INSTANCE = Mappers.getMapper(AwsCognitoAdminGetUserResultMapper.class);

    @Override
    default IaasUser fromIaasEntity(AdminGetUserResult result) {
        IaasUser iaasUser = null;

        if (result != null) {
            iaasUser = new IaasUser();
            iaasUser.setUserName(result.getUsername());
            iaasUser.setCreatedAt(LocalDateTimeMapper.INSTANCE.fromUtilDate(result.getUserCreateDate()));
            iaasUser.setLastUpdated(LocalDateTimeMapper.INSTANCE.fromUtilDate(result.getUserLastModifiedDate()));

            List<AttributeType> attributes = result.getUserAttributes();
            if (!CollectionUtils.isEmpty(attributes)) {
                for(AttributeType attr: attributes) {
                    if (CognitoPrincipalContext.ATTRIBUTE_EMAIL.equalsIgnoreCase(attr.getName())) {
                        iaasUser.setEmail(attr.getValue());
                    }
                }
            }

            iaasUser.setPhoneNumber(null);
        }

        return iaasUser;
    }
}
