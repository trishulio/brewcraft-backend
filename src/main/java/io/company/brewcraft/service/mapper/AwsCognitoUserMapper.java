package io.company.brewcraft.service.mapper;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserType;

import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.service.IaasEntityMapper;
import io.company.brewcraft.service.LocalDateTimeMapper;

public interface AwsCognitoUserMapper  extends IaasEntityMapper<UserType, IaasUser> {
    final AwsCognitoUserMapper INSTANCE = Mappers.getMapper(AwsCognitoUserMapper.class);

    @Override
    default IaasUser fromIaasEntity(UserType userType) {
        IaasUser iaasUser = null;
        
        if (userType != null) {
            iaasUser = new IaasUser();
            iaasUser.setUserName(userType.getUsername());
            iaasUser.setCreatedAt(LocalDateTimeMapper.INSTANCE.fromUtilDate(userType.getUserCreateDate()));
            iaasUser.setLastUpdated(LocalDateTimeMapper.INSTANCE.fromUtilDate(userType.getUserLastModifiedDate()));
            List<AttributeType> attributes = userType.getAttributes();
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