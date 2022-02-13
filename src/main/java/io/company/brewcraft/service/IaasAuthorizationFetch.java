package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.model.IaasAuthorizationCredentials;

public interface IaasAuthorizationFetch {
    IaasAuthorization fetch(IaasAuthorizationCredentials loginCredentials);
}
