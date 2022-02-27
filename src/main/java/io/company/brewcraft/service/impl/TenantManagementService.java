package io.company.brewcraft.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.migration.MigrationManager;
import io.company.brewcraft.model.BaseTenant;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantAccessor;
import io.company.brewcraft.model.UpdateTenant;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.CrudService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.TenantIaasService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class TenantManagementService implements CrudService<UUID, Tenant, BaseTenant, UpdateTenant, TenantAccessor> {
    private static final Logger log = LoggerFactory.getLogger(TenantManagementService.class);
    
    private RepoService<UUID, Tenant, TenantAccessor> repoService;
    private UpdateService<UUID, Tenant, BaseTenant, UpdateTenant> updateService;

    private TenantRepository tenantRepository;
    private MigrationManager migrationManager;
    private TenantIaasService iaasService;

    public TenantManagementService(
        RepoService<UUID, Tenant, TenantAccessor> repoService,
        UpdateService<UUID, Tenant, BaseTenant, UpdateTenant> updateService,
        TenantRepository tenantRepository,
        MigrationManager migrationManager,
        TenantIaasService iaasService
    ) {
        this.tenantRepository = tenantRepository;
        this.migrationManager = migrationManager;
        this.iaasService = iaasService;
    }
    
    @PostConstruct
    public void registerMockTenants() {
        // Mock data
        List<Tenant> testTenants = new ArrayList<>();
        testTenants.add(new Tenant(UUID.fromString("eae07f11-4c9a-4a3b-8b23-9c05d695ab67")));

        this.migrationManager.migrateAll(testTenants);
    }

    public Page<Tenant> getAll(
        List<UUID> ids,
        List<String> names,
        List<URL> urls,
        Boolean isReady,
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size 
    ) {
        Specification<Tenant> spec = WhereClauseBuilder.builder()
                                                       .in(new String[] { Tenant.FIELD_ID }, ids)
                                                       .in(new String[] { Tenant.FIELD_NAME }, names)
                                                       .in(new String[] { Tenant.FIELD_URL }, urls)
                                                       .is(new String[] { Tenant.FIELD_IS_READY } , isReady)
                                                       .build();
        return this.repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public boolean exists(Set<UUID> ids) {
        return this.repoService.exists(ids);
    }

    @Override
    public boolean exist(UUID id) {
        return this.repoService.exists(id);
    }

    @Override
    public int delete(Set<UUID> ids) {
        List<Tenant> tenants = this.tenantRepository.findAllById(ids);
        tenants.forEach(tenant -> tenant.setIsReady(false));
        this.repoService.saveAll(tenants);

        this.iaasService.delete(tenants);
        
        List<String> tenantIaasIds = tenants.stream().map(Tenant::getIaasId).toList();
        
        return this.delete(ids);
    }

    @Override
    public int delete(UUID id) {
        return this.delete(Set.of(id));
    }

    @Override
    public Tenant get(UUID id) {
        return this.repoService.get(id);
    }

    @Override
    public List<Tenant> getByIds(Collection<? extends Identified<UUID>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<Tenant> getByAccessorIds(Collection<? extends TenantAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getTenant());
    }

    @Override
    public List<Tenant> add(final List<BaseTenant> additions) {
        if (additions == null) {
            return null;
        }

        final List<Tenant> entities = this.updateService.getAddEntities(additions);

        List<Tenant> tenants = this.repoService.saveAll(entities);
        
        this.migrationManager.migrateAll(tenants);
        this.iaasService.put(tenants);
        
        tenants.forEach(tenant -> tenant.setIsReady(true));
        return this.repoService.saveAll(tenants);
    }

    @Override
    public List<Tenant> put(List<UpdateTenant> updates) {
        if (updates == null) {
            return null;
        }

        final List<Tenant> existing = this.repoService.getByIds(updates);
        final List<Tenant> updated = this.updateService.getPutEntities(existing, updates);

        List<Tenant> tenants = this.repoService.saveAll(updated);
        
        this.migrationManager.migrateAll(tenants);
        this.iaasService.put(tenants);
        
        tenants.forEach(tenant -> tenant.setIsReady(true));
        return this.repoService.saveAll(tenants);
    }

    @Override
    public List<Tenant> patch(List<UpdateTenant> patches) {
        if (patches == null) {
            return null;
        }

        final List<Tenant> existing = this.repoService.getByIds(patches);

        if (existing.size() != patches.size()) {
            final Set<UUID> existingIds = existing.stream().map(tenant -> tenant.getId()).collect(Collectors.toSet());
            final Set<UUID> nonExistingIds = patches.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find tenants with Ids: %s", nonExistingIds));
        }

        final List<Tenant> updated = this.updateService.getPatchEntities(existing, patches);

        List<Tenant> tenants = this.repoService.saveAll(updated);
        
        this.migrationManager.migrateAll(tenants);
        this.iaasService.put(tenants);
        
        tenants.forEach(tenant -> tenant.setIsReady(true));
        return this.repoService.saveAll(tenants);
    }
}
