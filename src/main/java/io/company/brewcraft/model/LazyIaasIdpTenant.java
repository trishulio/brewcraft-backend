package io.company.brewcraft.model;

import java.util.Set;
import java.util.UUID;

import io.company.brewcraft.service.impl.IdpTenantIaasRepository;

public class LazyIaasIdpTenant extends IaasIdpTenant {
    public String tenantId;
    private IaasIdpTenant delegate;
    private IdpTenantIaasRepository iaasIdpRepo;

    public LazyIaasIdpTenant(UUID tenantId, IdpTenantIaasRepository iaasIdpRepo) {
        this.tenantId = tenantId.toString();
        this.iaasIdpRepo = iaasIdpRepo;
    }

    @Override
    public String getName() {
        return tenantId;
    }

    @Override
    public void setName(String name) {
        this.tenantId = name;
    }

    @Override
    public String getDescription() {
        return delegate().getDescription();
    }

    @Override
    public void setDescription(String description) {
        delegate().setDescription(description);
    }

    @Override
    public IaasRole getIaasRole() {
        return delegate().getIaasRole();
    }

    @Override
    public void setIaasRole(IaasRole tenantRole) {
        delegate().setIaasRole(tenantRole);
    }

    @Override
    public void setId(String id) {
        delegate().setId(id);
        this.tenantId = id;
    }

    @Override
    public String getId() {
        return delegate().getId();
    }

    @Override
    public Integer getVersion() {
        return delegate().getVersion();
    }

    private IaasIdpTenant delegate() {
        if (this.delegate == null) {
            this.delegate = this.iaasIdpRepo.get(Set.of(this.tenantId)).get(0);
        }

        return this.delegate;
    }
}
