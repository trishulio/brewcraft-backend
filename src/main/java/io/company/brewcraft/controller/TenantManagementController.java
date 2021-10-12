package io.company.brewcraft.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.model.IdentityAccessor;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/operations")
public class TenantManagementController extends BaseController {
    private TenantManagementService tenantService;

    public TenantManagementController(TenantManagementService tenantService, AttributeFilter filter) {
        super(filter);
        this.tenantService = tenantService;
    }

    @GetMapping("/tenants")
    public List<TenantDto> getAll() {
        return tenantService.getTenants();
    }

    @GetMapping("/tenants/{id}")
    public TenantDto getTenant(@PathVariable UUID id) {
        return tenantService.getTenant(id);
    }

    @PostMapping("/tenants")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> addTenant(@Valid @RequestBody TenantDto newTenant) {
        UUID id = tenantService.addTenant(newTenant);
        return Map.of(IdentityAccessor.ATTR_ID, id.toString());
    }

    @PutMapping("/tenants/{id}")
    public void updateTenant(@Valid @RequestBody TenantDto updatedTenant, @PathVariable UUID id) {
        tenantService.updateTenant(updatedTenant, id);
    }

    @DeleteMapping("/tenants/{id}")
    public void deleteTenant(@PathVariable UUID id) {
        tenantService.deleteTenant(id);
    }
}
