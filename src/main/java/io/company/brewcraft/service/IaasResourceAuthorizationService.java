package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.model.IaasAuthorizationCredentials;
import io.company.brewcraft.model.IaasResourceAuthorization;
import io.company.brewcraft.model.IaasResources;

public class IaasResourceAuthorizationService extends BaseService {

    private IaasAuthorizationService iaasAuthorizationService;
    private IaasResourceService iaasResourceService;
    
    public IaasResourceAuthorization post(IaasAuthorizationCredentials credentials) {
        IaasAuthorization authorization = iaasAuthorizationService.post(credentials);
        IaasResources resources = iaasResourceService.getResources();

        IaasResourceAuthorization resourceAuthorization = new IaasResourceAuthorization(authorization, resources);

        return resourceAuthorization;
    }
    
}
