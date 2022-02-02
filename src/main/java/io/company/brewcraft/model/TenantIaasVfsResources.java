package io.company.brewcraft.model;

public class TenantIaasVfsResources extends BaseEntity implements UpdateTenantIaasVfsResources {
    private TenantObjectStore objectStore;
    private TenantIaasRole role;
    
    public TenantIaasVfsResources(TenantObjectStore objectStore, TenantIaasRole role) {
        setObjectStore(objectStore);
        setRole(role);
    }
    
    public TenantIaasVfsResourcesId getId() {
        
    }

    public TenantObjectStore getObjectStore() {
        return objectStore;
    }

    public void setObjectStore(TenantObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    public TenantIaasRole getRole() {
        return role;
    }

    public void setRole(TenantIaasRole role) {
        this.role = role;
    }

    @Override
    public TenantIaasRole getTenantRole() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTenantRole(TenantIaasRole tenantRole) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public TenantObjectStore getTenantObjectStoreAccesor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTenantObjectStore(TenantObjectStore tenantObjectStore) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setId(TenantIaasVfsResourcesId id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public TenantIaasVfsResourcesId getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getVersion() {
        // TODO Auto-generated method stub
        return null;
    }

}
