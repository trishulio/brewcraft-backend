package io.company.brewcraft.service;

import com.amazonaws.services.cognitoidentity.model.Credentials;

import io.company.brewcraft.model.IaasAuthorization;

public class AwsIdentityCredentialsMapper {

    private LocalDateTimeMapper dtMapper;

    public AwsIdentityCredentialsMapper(LocalDateTimeMapper dtMapper) {
        this.dtMapper = dtMapper;
    }

    public IaasAuthorization toIaasAuthorization(Credentials credentials) {
        IaasAuthorization authorization = null;

        if (credentials != null) {
            authorization = new IaasAuthorization();

            authorization.setAccessKey(credentials.getAccessKey());
            authorization.setAccessSecret(credentials.getAccessSecret());
            authorization.setSessionToken(credentials.getSessionToken());
            authorization.setExpiration(this.dtMapper.fromUtilDate(credentials.getExpiration()));
        }

        return authorization;
    }
}
