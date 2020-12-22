package io.company.brewcraft.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.migration.MigrationManager;
import io.company.brewcraft.migration.TenantRegister;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.impl.SupplierContactServiceImpl;
import io.company.brewcraft.service.impl.SupplierServiceImpl;
import io.company.brewcraft.service.impl.TenantManagementServiceImpl;
import io.company.brewcraft.service.mapper.TenantMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@Configuration
public class ServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TenantManagementService.class)
    public TenantManagementService tenantManagementService(TenantRepository tenantRepository, MigrationManager migrationManager, TenantRegister tenantRegister) {
        TenantManagementService tenantService = new TenantManagementServiceImpl(tenantRepository, migrationManager, TenantMapper.INSTANCE);
        return tenantService;
    }
    
    @Bean
    @ConditionalOnMissingBean(SupplierService.class)
    public SupplierService supplierService(SupplierRepository supplierRepository) {
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository);
        return supplierService;
    }
    
    @Bean
    @ConditionalOnMissingBean(SupplierContactService.class)
    public SupplierContactService supplierContactService(SupplierContactRepository supplierContactRepository, SupplierRepository supplierRepository) {
        SupplierContactService supplierContactService = new SupplierContactServiceImpl(supplierContactRepository, supplierRepository);
        return supplierContactService;
    }
    
    @Bean
    @ConditionalOnMissingBean(InvoiceService.class)
    public InvoiceService invoiceService(InvoiceRepository invoiceRepo, SupplierService supplierService) {
        return new InvoiceService(invoiceRepo, supplierService);
    }


    @Bean
    @ConditionalOnMissingBean(AttributeFilter.class)
    public AttributeFilter attributeFilter() {
        return new AttributeFilter();
    }
}
