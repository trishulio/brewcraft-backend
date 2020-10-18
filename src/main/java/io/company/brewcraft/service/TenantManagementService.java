package io.company.brewcraft.service;

import java.util.List;
import java.util.UUID;

import io.company.brewcraft.dto.TenantDto;

public interface TenantManagementService {

    public List<TenantDto> getTenants();
    
    public TenantDto getTenant(UUID id);

    public UUID addTenant(TenantDto tenant);
    
    public void updateTenant(TenantDto tenant, UUID id);

    public void deleteTenant(UUID id);
    
}
