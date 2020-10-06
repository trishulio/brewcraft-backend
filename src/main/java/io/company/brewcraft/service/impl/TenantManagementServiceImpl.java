package io.company.brewcraft.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                return tenantRepository.findAll().stream().map(tenant -> tenantMapper.tenantToTenantDto(tenant)).collect(Collectors.toList());
            }
        });
    }

    public UUID addTenant(TenantDto tenantDto) {
        return transactionTemplate.execute(new TransactionCallback<UUID>() {
            public UUID doInTransaction(TransactionStatus transactionStatus) {
                Tenant tenant = tenantMapper.tenantDtoToTenant(tenantDto);
                return tenantRepository.save(tenant);
            }
        });
    }

    public TenantDto getTenant(UUID id) {
        return transactionTemplate.execute(new TransactionCallback<TenantDto>() {
            public TenantDto doInTransaction(TransactionStatus transactionStatus) {
                return tenantMapper.tenantToTenantDto(tenantRepository.findById(id));
            }
        });
    }

    public void deleteTenant(UUID id) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                int result = tenantRepository.deleteById(id);

                if (result == 0) {
                    throw new EntityNotFoundException("Tenant", id.toString());
                }
            }
        });
    }
}
