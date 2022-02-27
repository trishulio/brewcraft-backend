package io.company.brewcraft.model;

public interface IaasTenant {
    final String ATTR_IAAS_ID = "iaasId";
    final String ATTR_IAAS_ROLE = "iaasRole";

    String getIaasId();

    IaasRole getIaasRole();
    
    void setIaasRole(IaasRole role);
}
