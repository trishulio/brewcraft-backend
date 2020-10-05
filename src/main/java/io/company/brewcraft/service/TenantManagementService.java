package io.company.brewcraft.service;

import java.util.List;
import java.util.UUID;

import io.company.brewcraft.dto.TenantDto;

public interface TenantManagementService {

    public List<TenantDto> getTenants();

    public UUID addTenant(TenantDto tenant);

    public TenantDto getTenant(UUID id);

    public void deleteTenant(UUID id);

}
