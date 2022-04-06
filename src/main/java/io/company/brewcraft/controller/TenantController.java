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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddTenantDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.dto.UpdateTenantDto;
import io.company.brewcraft.model.BaseTenant;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.UpdateTenant;
import io.company.brewcraft.service.impl.TenantService;
import io.company.brewcraft.service.mapper.TenantMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/operations/tenants")
public class TenantManagementController extends BaseController {
    private TenantService tenantService;

    private CrudControllerService<UUID, Tenant, BaseTenant, UpdateTenant, TenantDto, AddTenantDto, UpdateTenantDto> controller;

    @Autowired
    public TenantManagementController(TenantService tenantService, AttributeFilter filter) {
        this(new CrudControllerService<>(filter, TenantMapper.INSTANCE, tenantService, "Tenant"), tenantService);
    }

    public TenantManagementController(
            CrudControllerService<UUID, Tenant, BaseTenant, UpdateTenant, TenantDto, AddTenantDto, UpdateTenantDto> controller,
            TenantService tenantService
        ) {
        super();
        this.controller = controller;
        this.tenantService = tenantService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<TenantDto> getAll(
        @RequestParam(required = false, name = "ids") Set<UUID> ids,
        @RequestParam(required = false, name = "names") Set<String> names,
        @RequestParam(required = false, name = "urls") Set<URL> urls,
        @RequestParam(required = false, name = "is_ready") Boolean isReady,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        Page<Tenant> tenants = this.tenantService.getAll(ids, names, urls, isReady, sort, orderAscending, page, size);

        return this.controller.getAll(tenants, attributes);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public TenantDto getTenant(@PathVariable(required = true, name = "id") UUID id,  @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
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

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public long deleteTenants(@RequestParam("ids") Set<UUID> ids) {
        return this.controller.delete(ids);
    }
}
