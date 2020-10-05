package io.company.brewcraft.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.TenantMapper;

public class TenantManagementServiceImpl implements TenantManagementService {
    public static final Logger log = LoggerFactory.getLogger(TenantManagementServiceImpl.class);

    private TenantRepository tenantRepository;

    private TenantMapper tenantMapper;

    private TransactionTemplate transactionTemplate;

    public TenantManagementServiceImpl(TransactionTemplate transactionTemplate, TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.transactionTemplate = transactionTemplate;
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    public List<TenantDto> getTenants() {
        return transactionTemplate.execute(new TransactionCallback<List<TenantDto>>() {
            public List<TenantDto> doInTransaction(TransactionStatus transactionStatus) {
                List<TenantDto> tenants = null;
                try {
                    tenants = tenantRepository.findAll().stream().map(tenant -> tenantMapper.tenantToTenantDto(tenant)).collect(Collectors.toList());
                } catch (Exception e) {
                    log.error("Error getting tenants", e);
                    throw new RuntimeException("Error getting tenants list");
                }

                return tenants;
            }
        });
    }

    public UUID addTenant(TenantDto tenantDto) {
        return transactionTemplate.execute(new TransactionCallback<UUID>() {
            public UUID doInTransaction(TransactionStatus transactionStatus) {
                Tenant tenant = tenantMapper.tenantDtoToTenant(tenantDto);
                UUID id = null;
                try {
                    id = tenantRepository.save(tenant);
                } catch (DuplicateKeyException e) {
                    throw new DuplicateKeyException("Tenant already exists", e);
                } catch (Exception e) {
                    log.error("Error adding tenant with name: {}, domain: {}", tenantDto.getName(),
                            tenantDto.getDomain(), e);
                    throw new RuntimeException("Error adding tenant");
                }

                return id;
            }
        });
    }

    public TenantDto getTenant(UUID id) {
        return transactionTemplate.execute(new TransactionCallback<TenantDto>() {
            public TenantDto doInTransaction(TransactionStatus transactionStatus) {
                TenantDto tenantDto = null;
                try {
                    tenantDto = tenantMapper.tenantToTenantDto(tenantRepository.findById(id));
                } catch (EmptyResultDataAccessException e) {
                    throw new EntityNotFoundException("Tenant", id.toString());
                } catch (Exception e) {
                    log.error("Error getting tenant with id: {}", id.toString(), e);
                    throw new RuntimeException("Error getting tenant");
                }
                return tenantDto;
            }
        });
    }

    public void deleteTenant(UUID id) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                int result;
                try {
                    result = tenantRepository.deleteById(id);
                } catch (Exception e) {
                    log.error("Error deleting tenant with id: {}", id.toString(), e);
                    throw new RuntimeException("Error deleting tenant");
                }

                if (result == 0) {
                    throw new EntityNotFoundException("Tenant", id.toString());
                }
            }
        });
    }
}
