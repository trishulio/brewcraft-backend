package io.company.brewcraft.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.impl.TenantManagementServiceImpl;
import io.company.brewcraft.service.mapper.TenantMapper;

@Configuration
public class ServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TenantManagementService.class)
    public TenantManagementService tenantManagementService(TransactionTemplate transactionTemplate, TenantRepository tenantRepository) {
        TenantManagementService tenantService = new TenantManagementServiceImpl(transactionTemplate, tenantRepository, TenantMapper.INSTANCE);
        return tenantService;
    }
}
