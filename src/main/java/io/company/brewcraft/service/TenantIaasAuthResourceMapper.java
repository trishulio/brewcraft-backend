package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.List;

import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.TenantIaasAuthResources;

public class TenantIaasAuthResourceMapper {

    public List<TenantIaasAuthResources> fromComponents(List<IaasRole> roles) {
        List<TenantIaasAuthResources> resources = null;
        
        if (roles != null) {
            resources = new ArrayList<>(roles.size());
            
            for (int i = 0; i < roles.size(); i++) {
                TenantIaasAuthResources resource = new TenantIaasAuthResources(roles.get(i));
                resources.add(resource);
            }            
        }

        return resources;
    }

}
