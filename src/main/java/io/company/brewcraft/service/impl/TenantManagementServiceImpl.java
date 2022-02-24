package io.company.brewcraft.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.migration.MigrationManager;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.TenantIaasService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.TenantMapper;
import io.company.brewcraft.service.user.Group;

@Transactional
public class TenantManagementServiceImpl implements TenantManagementService {
    private static final Logger log = LoggerFactory.getLogger(TenantManagementServiceImpl.class);

    private TenantRepository tenantRepository;
    private MigrationManager migrationManager;
    private IdpUserRepository idpUserRepo;
    private TenantIaasService iaasService;

    private TenantMapper tenantMapper;

    public TenantManagementServiceImpl(TenantRepository tenantRepository, MigrationManager migrationManager, TenantIaasService iaasService, IdpUserRepository idpUserRepo, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.migrationManager = migrationManager;
        this.idpUserRepo = idpUserRepo;
        this.iaasService = iaasService;
        this.tenantMapper = tenantMapper;
    }

    @Override
    public List<TenantDto> getTenants() {
        return tenantRepository.findAll().stream().map(tenant -> tenantMapper.tenantToTenantDto(tenant)).toList();
    }

    @Override
    public TenantDto getTenant(UUID id) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tenant", id.toString()));

        return tenantMapper.tenantToTenantDto(tenant);
    }

    @Override
    public UUID addTenant(TenantDto tenantDto) {
        Tenant tenant = tenantMapper.tenantDtoToTenant(tenantDto);

        tenant = tenantRepository.saveAndFlush(tenant);

        String tenantId = tenant.getId().toString().replace("-", "_");

        try {
            migrationManager.migrate(tenantId);
        } catch (Exception e) {
            tenantRepository.deleteById(tenant.getId());
            throw new EntityNotFoundException(null, null);
        }

        log.info("Registering idp user group: {}", tenantId);
        idpUserRepo.putUserGroup(new Group(tenantId));

        iaasService.put(List.of(tenant));

        return tenant.getId();
    }

    @Override
    public void updateTenant(TenantDto tenantDto, UUID id) {
        Tenant tenantToUpdate = tenantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tenant", id.toString()));
        tenantToUpdate.setName(tenantDto.getName());
        tenantToUpdate.setUrl(tenantDto.getUrl());

        Tenant tenant = tenantRepository.save(tenantToUpdate);
        iaasService.put(List.of(tenant));
    }

    @Override
    public void deleteTenant(UUID id) {
        tenantRepository.deleteById(id);

//        TODO
//        migrationManager.demigrate()
        iaasService.delete(List.of(new Tenant(id)));
    }
}
