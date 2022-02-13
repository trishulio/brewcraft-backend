package io.company.brewcraft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.model.IaasAuthorizationDto;
import io.company.brewcraft.service.TenantIaasAuthorizationFetch;
import io.company.brewcraft.service.mapper.IaasAuthorizationMapper;

@RestController
@RequestMapping("/api/v1/iaas/auth") // TODO
public class TenantedIaasAuthorizationFetchController {

    private TenantIaasAuthorizationFetch authFetcher;

    @Autowired
    public TenantedIaasAuthorizationFetchController(TenantIaasAuthorizationFetch authFetcher) {
        this.authFetcher = authFetcher;
    }

    @GetMapping
    public IaasAuthorizationDto get() {
        IaasAuthorization authorization = this.authFetcher.fetch();

        return IaasAuthorizationMapper.INSTANCE.toDto(authorization);
    }
}
