package io.company.brewcraft.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.service.TenantManagementService;

@RestController
@RequestMapping(path = "/operations")
public class TenantManagementController {
    private TenantManagementService tenantService;

    public TenantManagementController(TenantManagementService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/tenants")
    public List<TenantDto> getAll() {
        return tenantService.getTenants();
    }

    @PostMapping("/tenants")
    public UUID addTenant(@Valid @RequestBody TenantDto newTenant) {
        return tenantService.addTenant(newTenant);
    }

    @GetMapping("/tenants/{id}")
    public TenantDto getTenant(@PathVariable UUID id) {
        return tenantService.getTenant(id);
    }

    @DeleteMapping("/tenants/{id}")
    public void deleteTenant(@PathVariable UUID id) {
        tenantService.deleteTenant(id);
    }
}
