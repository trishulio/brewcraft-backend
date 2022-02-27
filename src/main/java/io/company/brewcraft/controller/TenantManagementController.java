package io.company.brewcraft.controller;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddTenantDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.dto.UpdateTenantDto;
import io.company.brewcraft.model.BaseTenant;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.UpdateTenant;
import io.company.brewcraft.service.impl.TenantManagementService;
import io.company.brewcraft.service.mapper.TenantMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/operations/tenants")
public class TenantManagementController extends BaseController {
    private TenantManagementService tenantService;

    private CrudControllerService<UUID, Tenant, BaseTenant, UpdateTenant, TenantDto, AddTenantDto, UpdateTenantDto> controller;

    @Autowired
    public TenantManagementController(TenantManagementService tenantService, AttributeFilter filter) {
        this(new CrudControllerService<>(filter, TenantMapper.INSTANCE, tenantService, "Tenant"), tenantService);
    }

    public TenantManagementController(
            CrudControllerService<UUID, Tenant, BaseTenant, UpdateTenant, TenantDto, AddTenantDto, UpdateTenantDto> controller,
            TenantManagementService tenantService
        ) {
        super();
        this.controller = controller;
        this.tenantService = tenantService;
    }

    @GetMapping
    public PageDto<TenantDto> getAll(
        List<UUID> ids,
        List<String> names,
        List<URL> urls,
        Boolean isReady,
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size,
        Set<String> attributes
    ) {
        Page<Tenant> tenants = this.tenantService.getAll(ids, names, urls, isReady, sort, orderAscending, page, size);
        
        return this.controller.getAll(tenants, attributes);
    }

    @GetMapping("/{id}")
    public TenantDto getTenant(@PathVariable UUID id, Set<String> attributes) {
        return this.controller.get(id, attributes);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<TenantDto> addTenant(@Valid @NotNull @RequestBody List<AddTenantDto> addDtos) {
        return this.controller.add(addDtos);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<TenantDto> updateTenant(@Valid @NotNull @RequestBody List<UpdateTenantDto> updateDtos) {
        return this.controller.put(updateDtos);
    }

    @PatchMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<TenantDto> patchTenant(@Valid @NotNull @RequestBody List<UpdateTenantDto> updateDtos) {
        return this.controller.patch(updateDtos);
    }
}
