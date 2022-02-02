package io.company.brewcraft.service;

public class IaasResourceService {

    private IaasVfsResourceService vfsResourceService;
    
    public IaasResources getResources(String tenantId) {
        VfsStorage storage = vfsResourceService.getStorage();
        
        
        IaasResources iaasResource = new IaasResources()
                                         .setStorage(storage);
        
        return iaasResources;
    }
}
