package io.company.brewcraft.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantIaasResources;
import io.company.brewcraft.service.TenantIaasService;

@RestController
@RequestMapping(path = "/api/v1/tenant/iaas")
public class TenantIaasController extends BaseController {

    private TenantIaasService service;
    
    @Autowired
    public TenantIaasController(TenantIaasService service) {
        this.service = service;
    }
    
    @GetMapping
    public List<TenantIaasResources> get() {
        return this.service.get(List.of(new Tenant(UUID.fromString("eae07f11-4c9a-4a3b-8b23-9c05d695ab67"))));
    }
    
    @PostMapping
    public void post() {
        this.service.put((List.of(new Tenant(UUID.fromString("eae07f11-4c9a-4a3b-8b23-9c05d695ab67")))));
    }
    
    @DeleteMapping
    public void delete() {
        this.service.delete(List.of(new Tenant(UUID.fromString("eae07f11-4c9a-4a3b-8b23-9c05d695ab67"))));
    }    
}
