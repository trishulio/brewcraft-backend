package io.company.brewcraft.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.migration.MigrationManager;
import io.company.brewcraft.migration.TenantRegister;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialCategoryRepository;
import io.company.brewcraft.repository.MaterialLotAggregationRepository;
import io.company.brewcraft.repository.MaterialPortionRepository;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.repository.MixtureRecordingRepository;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.ProductCategoryRepository;
import io.company.brewcraft.repository.ProductMeasureValueRepository;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.QuantityUnitRepository;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.service.BrewStageStatusServiceImpl;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.BrewTaskServiceImpl;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.InvoiceStatusService;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.MaterialLotInventoryService;
import io.company.brewcraft.service.MaterialPortionService;
import io.company.brewcraft.service.MaterialPortionServiceImpl;
import io.company.brewcraft.service.MaterialService;
import io.company.brewcraft.service.MeasureService;
import io.company.brewcraft.service.MixtureRecordingService;
import io.company.brewcraft.service.MixtureRecordingServiceImpl;
import io.company.brewcraft.service.MixtureService;
import io.company.brewcraft.service.MixtureServiceImpl;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.ProductMeasureValueService;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.impl.BrewServiceImpl;
import io.company.brewcraft.service.impl.BrewStageServiceImpl;
import io.company.brewcraft.service.impl.EquipmentServiceImpl;
import io.company.brewcraft.service.impl.FacilityServiceImpl;
import io.company.brewcraft.service.impl.MaterialCategoryServiceImpl;
import io.company.brewcraft.service.impl.MaterialLotService;
import io.company.brewcraft.service.impl.MaterialServiceImpl;
import io.company.brewcraft.service.impl.MeasureServiceImpl;
import io.company.brewcraft.service.impl.ProductCategoryServiceImpl;
import io.company.brewcraft.service.impl.ProductMeasureValueServiceImpl;
import io.company.brewcraft.service.impl.ProductServiceImpl;
import io.company.brewcraft.service.impl.QuantityUnitServiceImpl;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.service.impl.StorageServiceImpl;
import io.company.brewcraft.service.impl.SupplierContactServiceImpl;
import io.company.brewcraft.service.impl.SupplierServiceImpl;
import io.company.brewcraft.service.impl.TenantManagementServiceImpl;
import io.company.brewcraft.service.impl.procurement.ProcurementServiceImpl;
import io.company.brewcraft.service.impl.user.UserServiceImpl;
import io.company.brewcraft.service.mapper.TenantMapper;
import io.company.brewcraft.service.procurement.ProcurementService;
import io.company.brewcraft.service.user.UserService;
import io.company.brewcraft.util.ThreadLocalUtilityProvider;
import io.company.brewcraft.util.UtilityProvider;
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
    public SupplierContactService supplierContactService(SupplierContactRepository supplierContactRepository, SupplierService supplierService) {
        SupplierContactService supplierContactService = new SupplierContactServiceImpl(supplierContactRepository, supplierService);
        return supplierContactService;
    }

    @Bean
    public InvoiceItemService invoiceItemService(UtilityProvider utilProvider) {
        return new InvoiceItemService(utilProvider);
    }

    @Bean
    @ConditionalOnMissingBean(InvoiceService.class)
    public InvoiceService invoiceService(InvoiceRepository invoiceRepo, InvoiceItemService invoiceItemService) {
        return new InvoiceService(invoiceRepo, invoiceItemService);
    }

    @Bean
    @ConditionalOnMissingBean(PurchaseOrderService.class)
    public PurchaseOrderService purchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        return new PurchaseOrderService(purchaseOrderRepository);
    }

    @Bean
    @ConditionalOnMissingBean(InvoiceStatusService.class)
    public InvoiceStatusService invoiceStatusService(InvoiceStatusRepository invoiceStatusRepository) {
        return new InvoiceStatusService(invoiceStatusRepository);
    }

    @Bean
    @ConditionalOnMissingBean(AttributeFilter.class)
    public AttributeFilter attributeFilter() {
        return new AttributeFilter();
    }

    @Bean
    @ConditionalOnMissingBean(FacilityService.class)
    public FacilityService facilityService(FacilityRepository facilityRepository) {
        FacilityService facilityService = new FacilityServiceImpl(facilityRepository);
        return facilityService;
    }

    @Bean
    @ConditionalOnMissingBean(EquipmentService.class)
    public EquipmentService equipmentService(EquipmentRepository equipmentRepository, FacilityService facilityService) {
        EquipmentService equipmentService = new EquipmentServiceImpl(equipmentRepository, facilityService);
        return equipmentService;
    }

    @Bean
    @ConditionalOnMissingBean(StorageService.class)
    public StorageService storageService(StorageRepository storageRepository, FacilityService facilityService) {
        StorageService storageService = new StorageServiceImpl(storageRepository, facilityService);
        return storageService;
    }

    @Bean
    @ConditionalOnMissingBean(MaterialService.class)
    public MaterialService materialService(MaterialRepository materialRepository, MaterialCategoryService materialCategoryService, QuantityUnitService quantityUnitService) {
        MaterialService materialService = new MaterialServiceImpl(materialRepository, materialCategoryService, quantityUnitService);
        return materialService;
    }

    @Bean
    @ConditionalOnMissingBean(MaterialCategoryService.class)
    public MaterialCategoryService materialCategoryService(MaterialCategoryRepository materialCategoryRepository) {
        MaterialCategoryService materialCategoryService = new MaterialCategoryServiceImpl(materialCategoryRepository);
        return materialCategoryService;
    }

    @Bean
    @ConditionalOnMissingBean(QuantityUnitService.class)
    public QuantityUnitService quantityUnitService(QuantityUnitRepository quantityUnitRepository) {
        QuantityUnitService quantityUnitService = new QuantityUnitServiceImpl(quantityUnitRepository);
        return quantityUnitService;
    }

    @Bean
    @ConditionalOnMissingBean(ShipmentService.class)
    public ShipmentService shipmentService(ShipmentRepository repo, MaterialLotService itemService) {
        ShipmentService shipmentService = new ShipmentService(repo, itemService);

        return shipmentService;
    }

    @Bean
    @ConditionalOnMissingBean(MaterialLotService.class)
    public MaterialLotService materialLotService(UtilityProvider utilProvider) {
        MaterialLotService itemService = new MaterialLotService(utilProvider);

        return itemService;
    }

    @Bean
    @ConditionalOnMissingBean(UtilityProvider.class)
    public UtilityProvider utilityProvider() {
        return new ThreadLocalUtilityProvider();
    }

    @Bean
    @ConditionalOnMissingBean(ProductService.class)
    public ProductService productService(ProductRepository productRepository, ProductCategoryService productCategoryService, ProductMeasureValueService productMeasureValueService, MeasureService productMeasureService) {
        ProductService productService = new ProductServiceImpl(productRepository, productCategoryService, productMeasureValueService, productMeasureService);
        return productService;
    }

    @Bean
    @ConditionalOnMissingBean(ProductCategoryService.class)
    public ProductCategoryService productCategoryService(ProductCategoryRepository productCategoryRepository) {
        ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(productCategoryRepository);
        return productCategoryService;
    }

    @Bean
    @ConditionalOnMissingBean(MeasureService.class)
    public MeasureService measureService(MeasureRepository measureRepository) {
        MeasureService measureService = new MeasureServiceImpl(measureRepository);
        return measureService;
    }

    @Bean
    @ConditionalOnMissingBean(ProductMeasureValueService.class)
    public ProductMeasureValueService productMeasureValueService(ProductMeasureValueRepository productMeasureValueRepository) {
        ProductMeasureValueService productMeasureValueService = new ProductMeasureValueServiceImpl(productMeasureValueRepository);
        return productMeasureValueService;
    }


    @Bean
    @ConditionalOnMissingBean(UserService.class)
    public UserService userService(UserRepository userRepository, IdpUserRepository idpRepo) {
        return new UserServiceImpl(userRepository, idpRepo);
    }

    @Bean
    @ConditionalOnMissingBean(ProcurementService.class)
    public ProcurementService procurementService(InvoiceService invoiceService, PurchaseOrderService purchaseOrderService, ShipmentService shipmentService) {
        return new ProcurementServiceImpl(invoiceService, purchaseOrderService, shipmentService);
    }
    
    @Bean
    @ConditionalOnMissingBean(MaterialLotInventoryService.class)
    public MaterialLotInventoryService lotInventoryService(MaterialLotAggregationRepository lotAggrRepo) {
        return new MaterialLotInventoryService(lotAggrRepo);
    }
    
    @Bean
    @ConditionalOnMissingBean(BrewService.class)
    public BrewService brewService(BrewRepository brewRepository) {
        BrewService brewService = new BrewServiceImpl(brewRepository);
        return brewService;
    }
    
    @Bean
    @ConditionalOnMissingBean(BrewTaskService.class)
    public BrewTaskService brewTaskService(BrewTaskRepository brewTaskRepository) {
        BrewTaskService brewTaskService = new BrewTaskServiceImpl(brewTaskRepository);
        return brewTaskService;
    }
    
    @Bean
    @ConditionalOnMissingBean(BrewStageService.class)
    public BrewStageService brewStageService(BrewStageRepository brewStageRepository) {
        BrewStageService brewStageService = new BrewStageServiceImpl(brewStageRepository);
        return brewStageService;
    }
    
    @Bean
    @ConditionalOnMissingBean(BrewStageStatusService.class)
    public BrewStageStatusService brewStageStatusService(BrewStageStatusRepository brewStageStatusRepository) {
        BrewStageStatusService brewStageStatusService = new BrewStageStatusServiceImpl(brewStageStatusRepository);
        return brewStageStatusService;
    }
    
    @Bean
    @ConditionalOnMissingBean(MixtureService.class)
    public MixtureService mixtureService(MixtureRepository mixtureRepository) {
    	MixtureService mixtureService = new MixtureServiceImpl(mixtureRepository);
        return mixtureService;
    }
    
    @Bean
    @ConditionalOnMissingBean(MaterialPortionService.class)
    public MaterialPortionService materialPortionService(MaterialPortionRepository materialPortionRepository) {
    	MaterialPortionService materialPortionService = new MaterialPortionServiceImpl(materialPortionRepository);
        return materialPortionService;
    }
    
    @Bean
    @ConditionalOnMissingBean(MixtureRecordingService.class)
    public MixtureRecordingService mixtureRecordingService(MixtureRecordingRepository mixtureRecordingRepository) {
    	MixtureRecordingService mixtureRecordingService = new MixtureRecordingServiceImpl(mixtureRecordingRepository);
        return mixtureRecordingService;
    }
}
