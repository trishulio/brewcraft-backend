package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.TenantIaasIdpResources;

public class TenantIaasIdpResourcesMapper {
    public List<TenantIaasIdpResources> fromComponents(List<IaasIdpTenant> idpTenants) {
        List<TenantIaasIdpResources> resources = new ArrayList<>();

        Iterator<IaasIdpTenant> idpTenantsIterator = idpTenants.iterator();
        while (idpTenantsIterator.hasNext()) {
            resources.add(fromComponents(idpTenantsIterator.next()));
        }
        
        return resources;
    }
    
    public TenantIaasIdpResources fromComponents(IaasIdpTenant idpTenant) {
        return new TenantIaasIdpResources(idpTenant);
    }
}
