package io.company.brewcraft.configuration;

import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.migration.MigrationManager;
import io.company.brewcraft.migration.TenantRegister;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentAccessor;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialCategoryRepository;
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
import io.company.brewcraft.repository.StockLotRepository;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.service.AggregationService;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.service.BrewStageStatusServiceImpl;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.BrewTaskServiceImpl;
import io.company.brewcraft.service.CrudRepoService;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.InvoiceStatusService;
import io.company.brewcraft.service.LotAggregationService;
import io.company.brewcraft.service.MaterialCategoryService;
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
import io.company.brewcraft.service.PurchaseOrderAccessor;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.SimpleUpdateService;
import io.company.brewcraft.service.StockLotService;
import io.company.brewcraft.service.StockLotServiceImpl;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.UpdateService;
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
        final TenantManagementService tenantService = new TenantManagementServiceImpl(tenantRepository, migrationManager, TenantMapper.INSTANCE);
        return tenantService;
    }

    @Bean
    @ConditionalOnMissingBean(SupplierService.class)
    public SupplierService supplierService(SupplierRepository supplierRepository) {
        final SupplierService supplierService = new SupplierServiceImpl(supplierRepository);
        return supplierService;
    }

    @Bean
    @ConditionalOnMissingBean(SupplierContactService.class)
    public SupplierContactService supplierContactService(SupplierContactRepository supplierContactRepository, SupplierService supplierService) {
        final SupplierContactService supplierContactService = new SupplierContactServiceImpl(supplierContactRepository, supplierService);
        return supplierContactService;
    }

    @Bean
    public InvoiceItemService invoiceItemService(UtilityProvider utilProvider) {
        final UpdateService<Long, InvoiceItem, BaseInvoiceItem<?>, UpdateInvoiceItem<?>> updateService = new SimpleUpdateService<>(utilProvider, BaseInvoiceItem.class, UpdateInvoiceItem.class, InvoiceItem.class, Set.of(BaseInvoiceItem.ATTR_INVOICE));
        return new InvoiceItemService(updateService);
    }

    @Bean
    @ConditionalOnMissingBean(InvoiceService.class)
    public InvoiceService invoiceService(UtilityProvider utilProvider, InvoiceItemService invoiceItemService, final InvoiceRepository invoiceRepo) {
        final UpdateService<Long, Invoice, BaseInvoice<? extends BaseInvoiceItem<?>>, UpdateInvoice<? extends UpdateInvoiceItem<?>>> updateService = new SimpleUpdateService<>(utilProvider, BaseInvoice.class, UpdateInvoice.class, Invoice.class, Set.of(BaseInvoice.ATTR_ITEMS));
        final RepoService<Long, Invoice, InvoiceAccessor> repoService = new CrudRepoService<>(invoiceRepo);
        return new InvoiceService(updateService, invoiceItemService, repoService);
    }

    @Bean
    @ConditionalOnMissingBean(PurchaseOrderService.class)
    public PurchaseOrderService purchaseOrderService(UtilityProvider utilProvider, PurchaseOrderRepository poRepo) {
        final UpdateService<Long, PurchaseOrder, BasePurchaseOrder, UpdatePurchaseOrder> updateService = new SimpleUpdateService<>(utilProvider, BasePurchaseOrder.class, UpdatePurchaseOrder.class, PurchaseOrder.class, Set.of());
        final RepoService<Long, PurchaseOrder, PurchaseOrderAccessor> repoService = new CrudRepoService<>(poRepo);
        return new PurchaseOrderService(updateService, repoService);
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
        final FacilityService facilityService = new FacilityServiceImpl(facilityRepository);
        return facilityService;
    }

    @Bean
    @ConditionalOnMissingBean(EquipmentService.class)
    public EquipmentService equipmentService(EquipmentRepository equipmentRepository, FacilityService facilityService) {
        final EquipmentService equipmentService = new EquipmentServiceImpl(equipmentRepository, facilityService);
        return equipmentService;
    }

    @Bean
    @ConditionalOnMissingBean(StorageService.class)
    public StorageService storageService(StorageRepository storageRepository, FacilityService facilityService) {
        final StorageService storageService = new StorageServiceImpl(storageRepository, facilityService);
        return storageService;
    }

    @Bean
    @ConditionalOnMissingBean(MaterialService.class)
    public MaterialService materialService(MaterialRepository materialRepository, MaterialCategoryService materialCategoryService, QuantityUnitService quantityUnitService) {
        final MaterialService materialService = new MaterialServiceImpl(materialRepository, materialCategoryService, quantityUnitService);
        return materialService;
    }

    @Bean
    @ConditionalOnMissingBean(MaterialCategoryService.class)
    public MaterialCategoryService materialCategoryService(MaterialCategoryRepository materialCategoryRepository) {
        final MaterialCategoryService materialCategoryService = new MaterialCategoryServiceImpl(materialCategoryRepository);
        return materialCategoryService;
    }

    @Bean
    @ConditionalOnMissingBean(QuantityUnitService.class)
    public QuantityUnitService quantityUnitService(QuantityUnitRepository quantityUnitRepository) {
        final QuantityUnitService quantityUnitService = new QuantityUnitServiceImpl(quantityUnitRepository);
        return quantityUnitService;
    }

    @Bean
    @ConditionalOnMissingBean(ShipmentService.class)
    public ShipmentService shipmentService(UtilityProvider utilProvider, ShipmentRepository repo, MaterialLotService lotService) {
        final UpdateService<Long, Shipment, BaseShipment<? extends BaseMaterialLot<?>>, UpdateShipment<? extends UpdateMaterialLot<?>>> updateService = new SimpleUpdateService<>(utilProvider, BaseShipment.class, UpdateShipment.class, Shipment.class, Set.of(BaseShipment.ATTR_LOTS));
        final RepoService<Long, Shipment, ShipmentAccessor> repoService = new CrudRepoService<>(repo);
        final ShipmentService shipmentService = new ShipmentService(updateService, lotService, repoService);

        return shipmentService;
    }

    @Bean
    @ConditionalOnMissingBean(MaterialLotService.class)
    public MaterialLotService materialLotService(UtilityProvider utilProvider) {
        final UpdateService<Long, MaterialLot, BaseMaterialLot<?>, UpdateMaterialLot<?>> updateService = new SimpleUpdateService<>(utilProvider, BaseMaterialLot.class, UpdateMaterialLot.class, MaterialLot.class, Set.of(BaseMaterialLot.ATTR_SHIPMENT));
        final MaterialLotService itemService = new MaterialLotService(updateService);

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
        final ProductService productService = new ProductServiceImpl(productRepository, productCategoryService, productMeasureValueService, productMeasureService);
        return productService;
    }

    @Bean
    @ConditionalOnMissingBean(ProductCategoryService.class)
    public ProductCategoryService productCategoryService(ProductCategoryRepository productCategoryRepository) {
        final ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(productCategoryRepository);
        return productCategoryService;
    }

    @Bean
    @ConditionalOnMissingBean(MeasureService.class)
    public MeasureService measureService(MeasureRepository measureRepository) {
        final MeasureService measureService = new MeasureServiceImpl(measureRepository);
        return measureService;
    }

    @Bean
    @ConditionalOnMissingBean(ProductMeasureValueService.class)
    public ProductMeasureValueService productMeasureValueService() {
        final ProductMeasureValueService productMeasureValueService = new ProductMeasureValueServiceImpl();
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
    @ConditionalOnMissingBean
    public AggregationService aggrService(AggregationRepository aggrRepo) {
        return new AggregationService(aggrRepo);
    }

    @Bean
    @ConditionalOnMissingBean(LotAggregationService.class)
    public LotAggregationService lotInventoryService(AggregationService aggrService) {
        return new LotAggregationService(aggrService);
    }

    @Bean
    @ConditionalOnMissingBean(StockLotService.class)
    public StockLotService stockLotService(StockLotRepository stockLotRepository) {
        final StockLotService stockLotService = new StockLotServiceImpl(stockLotRepository);
        return stockLotService;
    }

    @Bean
    @ConditionalOnMissingBean(BrewService.class)
    public BrewService brewService(BrewRepository brewRepository) {
        final BrewService brewService = new BrewServiceImpl(brewRepository);
        return brewService;
    }

    @Bean
    @ConditionalOnMissingBean(BrewTaskService.class)
    public BrewTaskService brewTaskService(BrewTaskRepository brewTaskRepository) {
        final BrewTaskService brewTaskService = new BrewTaskServiceImpl(brewTaskRepository);
        return brewTaskService;
    }

    @Bean
    @ConditionalOnMissingBean(BrewStageService.class)
    public BrewStageService brewStageService(BrewStageRepository brewStageRepository) {
        final BrewStageService brewStageService = new BrewStageServiceImpl(brewStageRepository);
        return brewStageService;
    }

    @Bean
    @ConditionalOnMissingBean(BrewStageStatusService.class)
    public BrewStageStatusService brewStageStatusService(BrewStageStatusRepository brewStageStatusRepository) {
        final BrewStageStatusService brewStageStatusService = new BrewStageStatusServiceImpl(brewStageStatusRepository);
        return brewStageStatusService;
    }

    @Bean
    @ConditionalOnMissingBean(MixtureService.class)
    public MixtureService mixtureService(MixtureRepository mixtureRepository) {
        final MixtureService mixtureService = new MixtureServiceImpl(mixtureRepository);
        return mixtureService;
    }

    @Bean
    @ConditionalOnMissingBean(MaterialPortionService.class)
    public MaterialPortionService materialPortionService(MaterialPortionRepository materialPortionRepository, StockLotService stockLotService) {
        final MaterialPortionService materialPortionService = new MaterialPortionServiceImpl(materialPortionRepository, stockLotService);
        return materialPortionService;
    }

    @Bean
    @ConditionalOnMissingBean(MixtureRecordingService.class)
    public MixtureRecordingService mixtureRecordingService(MixtureRecordingRepository mixtureRecordingRepository) {
        final MixtureRecordingService mixtureRecordingService = new MixtureRecordingServiceImpl(mixtureRecordingRepository);
        return mixtureRecordingService;
    }
}
