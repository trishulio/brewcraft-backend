package io.company.brewcraft.service.mapper;

import java.util.List;
import java.util.UUID;

import com.amazonaws.services.cognitoidp.model.GroupType;

import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.service.LocalDateTimeMapper;

public class IaasIdpTenantMapper {

    private LocalDateTimeMapper dtMapper;

    public IaasIdpTenantMapper(LocalDateTimeMapper dtMapper) {
        this.dtMapper = dtMapper;
    }

    public List<IaasIdpTenant> fromTenants(List<Tenant> tenants) {
        List<IaasIdpTenant> idpTenants = null;

        if (tenants != null) {
            idpTenants = tenants.stream().map(tenant -> fromTenant(tenant)).toList();
        }

        return idpTenants;
    }

    public IaasIdpTenant fromTenant(Tenant tenant) {
        IaasIdpTenant idpTenant = null;

        if (tenant != null) {
            idpTenant = new IaasIdpTenant();

            UUID id = tenant.getId();
            if (id != null) {
                idpTenant.setId(id.toString());
            } else {
                idpTenant.setId(null);
            }
            idpTenant.setCreatedAt(tenant.getCreatedAt());
            idpTenant.setLastUpdated(tenant.getLastUpdated());
            idpTenant.setIaasRole(null);
        }

        return idpTenant;
    }

    public List<IaasIdpTenant> fromGroups(List<GroupType> groups) {
        List<IaasIdpTenant> idpTenants = null;

        if (groups != null) {
            idpTenants = groups.stream().map(group -> fromGroup(group)).toList();
        }

        return idpTenants;
    }

    public IaasIdpTenant fromGroup(GroupType group) {
        IaasIdpTenant idpTenant = null;

        if (group != null) {
            idpTenant = new IaasIdpTenant();

            idpTenant.setCreatedAt(this.dtMapper.fromUtilDate(group.getCreationDate()));
            idpTenant.setLastUpdated(this.dtMapper.fromUtilDate(group.getLastModifiedDate()));
            idpTenant.setId(group.getGroupName());
            idpTenant.setDescription(group.getDescription());
            idpTenant.setIaasRole(null);
        }

        return idpTenant;
    }
}
