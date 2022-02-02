package io.company.brewcraft.configuration;

import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateFinishedGoodLot;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.migration.MigrationManager;
import io.company.brewcraft.migration.TenantRegister;
import io.company.brewcraft.model.BaseFinishedGoodLot;
import io.company.brewcraft.model.BaseFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.BaseFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.BaseFinishedGoodLotMixturePortion;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseMixtureMaterialPortion;
import io.company.brewcraft.model.BaseMixtureRecording;
import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.BaseSku;
import io.company.brewcraft.model.BaseSkuMaterial;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentAccessor;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.model.UpdateFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMixturePortion;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdateMixtureMaterialPortion;
import io.company.brewcraft.model.UpdateMixtureRecording;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.model.UpdateSku;
import io.company.brewcraft.model.UpdateSkuMaterial;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.repository.FinishedGoodInventoryRepository;
import io.company.brewcraft.repository.FinishedGoodLotRepository;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialCategoryRepository;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.repository.MixtureMaterialPortionRepository;
import io.company.brewcraft.repository.MixtureRecordingRepository;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.ProcurementRepository;
import io.company.brewcraft.repository.ProductCategoryRepository;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.QuantityUnitRepository;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.repository.SkuRepository;
import io.company.brewcraft.repository.StockLotRepository;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.repository.user.UserRoleRepository;
import io.company.brewcraft.repository.user.UserSalutationRepository;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.service.AggregationService;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.service.BrewStageStatusServiceImpl;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.BrewTaskServiceImpl;
import io.company.brewcraft.service.CrudRepoService;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.FinishedGoodLotAccessor;
import io.company.brewcraft.service.FinishedGoodLotFinishedGoodLotPortionService;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.service.FinishedGoodInventoryServiceImpl;
import io.company.brewcraft.service.FinishedGoodLotMaterialPortionService;
import io.company.brewcraft.service.FinishedGoodLotMixturePortionService;
import io.company.brewcraft.service.FinishedGoodLotService;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.InvoiceStatusService;
import io.company.brewcraft.service.LotAggregationService;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.MaterialService;
import io.company.brewcraft.service.MeasureService;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.MixtureMaterialPortionAccessor;
import io.company.brewcraft.service.MixtureMaterialPortionService;
import io.company.brewcraft.service.MixtureMaterialPortionServiceImpl;
import io.company.brewcraft.service.MixtureRecordingAccessor;
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
import io.company.brewcraft.service.SkuMaterialService;
import io.company.brewcraft.service.SkuService;
import io.company.brewcraft.service.StockLotService;
import io.company.brewcraft.service.StockLotServiceImpl;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.TransactionService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.UserRoleService;
import io.company.brewcraft.service.UserSalutationService;
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
import io.company.brewcraft.service.impl.SkuServiceImpl;
import io.company.brewcraft.service.impl.StorageServiceImpl;
import io.company.brewcraft.service.impl.SupplierContactServiceImpl;
import io.company.brewcraft.service.impl.SupplierServiceImpl;
import io.company.brewcraft.service.impl.TenantManagementServiceImpl;
import io.company.brewcraft.service.impl.procurement.ProcurementService;
import io.company.brewcraft.service.impl.user.UserServiceImpl;
import io.company.brewcraft.service.mapper.TenantMapper;
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
    public InvoiceService invoiceService(UtilityProvider utilProvider, InvoiceItemService invoiceItemService, final InvoiceRepository invoiceRepo, final Refresher<Invoice, InvoiceAccessor> invoiceRefresher) {
        final UpdateService<Long, Invoice, BaseInvoice<? extends BaseInvoiceItem<?>>, UpdateInvoice<? extends UpdateInvoiceItem<?>>> updateService = new SimpleUpdateService<>(utilProvider, BaseInvoice.class, UpdateInvoice.class, Invoice.class, Set.of(BaseInvoice.ATTR_INVOICE_ITEMS));
        final RepoService<Long, Invoice, InvoiceAccessor> repoService = new CrudRepoService<>(invoiceRepo, invoiceRefresher);
        return new InvoiceService(updateService, invoiceItemService, repoService);
    }

    @Bean
    @ConditionalOnMissingBean(PurchaseOrderService.class)
    public PurchaseOrderService purchaseOrderService(UtilityProvider utilProvider, PurchaseOrderRepository poRepo, Refresher<PurchaseOrder, PurchaseOrderAccessor> purchaseOrderRefresher) {
        final UpdateService<Long, PurchaseOrder, BasePurchaseOrder, UpdatePurchaseOrder> updateService = new SimpleUpdateService<>(utilProvider, BasePurchaseOrder.class, UpdatePurchaseOrder.class, PurchaseOrder.class, Set.of());
        final RepoService<Long, PurchaseOrder, PurchaseOrderAccessor> repoService = new CrudRepoService<>(poRepo, purchaseOrderRefresher);
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
    public ShipmentService shipmentService(UtilityProvider utilProvider, ShipmentRepository repo, MaterialLotService lotService, Refresher<Shipment, ShipmentAccessor> shipmentRefresher) {
        final UpdateService<Long, Shipment, BaseShipment<? extends BaseMaterialLot<?>>, UpdateShipment<? extends UpdateMaterialLot<?>>> updateService = new SimpleUpdateService<>(utilProvider, BaseShipment.class, UpdateShipment.class, Shipment.class, Set.of(BaseShipment.ATTR_LOTS));
        final RepoService<Long, Shipment, ShipmentAccessor> repoService = new CrudRepoService<>(repo, shipmentRefresher);
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
    @ConditionalOnMissingBean(UserRoleService.class)
    public UserRoleService userRoleService(UserRoleRepository userRoleRepository) {
        final UserRoleService userRoleService = new UserRoleService(userRoleRepository);
        return userRoleService;
    }

    @Bean
    @ConditionalOnMissingBean(UserSalutationService.class)
    public UserSalutationService userSalutationService(UserSalutationRepository userSalutationRepository) {
        final UserSalutationService userSalutationService = new UserSalutationService(userSalutationRepository);
        return userSalutationService;
    }

    @Bean
    @ConditionalOnMissingBean(UserService.class)
    public UserService userService(UserRepository userRepository, IdpUserRepository idpRepo, Refresher<User, UserAccessor> userRefresher, ContextHolder contexHolder) {
        return new UserServiceImpl(userRepository, idpRepo, userRefresher, contexHolder);
    }

    @Bean
    @ConditionalOnMissingBean(TransactionService.class)
    public TransactionService transactionService() {
        return new TransactionService();
    }

    @Bean
    @ConditionalOnMissingBean(ProcurementService.class)
    public ProcurementService procurementService(InvoiceService invoiceService, ShipmentService shipmentService, ProcurementRepository procurementRepo, Refresher<Procurement, ProcurementAccessor> procurementRefresher) {
        final RepoService<ProcurementId, Procurement, ProcurementAccessor> repoService = new CrudRepoService<>(procurementRepo, procurementRefresher);
        return new ProcurementService(invoiceService, shipmentService, repoService);
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
    public BrewService brewService(BrewRepository brewRepository, Refresher<Brew, BrewAccessor> brewRefresher) {
        final BrewService brewService = new BrewServiceImpl(brewRepository, brewRefresher);
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
    public BrewStageService brewStageService(BrewStageRepository brewStageRepository, Refresher<BrewStage, BrewStageAccessor> brewStageRefresher) {
        final BrewStageService brewStageService = new BrewStageServiceImpl(brewStageRepository, brewStageRefresher);
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
    public MixtureService mixtureService(MixtureRepository mixtureRepository, Refresher<Mixture, MixtureAccessor> mixtureRefresher) {
        final MixtureService mixtureService = new MixtureServiceImpl(mixtureRepository, mixtureRefresher);
        return mixtureService;
    }

    @Bean
    @ConditionalOnMissingBean(MixtureMaterialPortionService.class)
    public MixtureMaterialPortionService mixtureMaterialPortionService(UtilityProvider utilProvider, MixtureMaterialPortionRepository materialPortionRepository, StockLotService stockLotService, Refresher<MixtureMaterialPortion, MixtureMaterialPortionAccessor> mixtureMaterialPortionRefresher) {
        final RepoService<Long, MixtureMaterialPortion, MixtureMaterialPortionAccessor> repoService = new CrudRepoService<>(materialPortionRepository, mixtureMaterialPortionRefresher);
        final UpdateService<Long, MixtureMaterialPortion, BaseMixtureMaterialPortion, UpdateMixtureMaterialPortion> updateService = new SimpleUpdateService<>(utilProvider, BaseMixtureMaterialPortion.class, UpdateMixtureMaterialPortion.class, MixtureMaterialPortion.class, Set.of());
        return new MixtureMaterialPortionServiceImpl(repoService, updateService, stockLotService);
    }

    @Bean
    @ConditionalOnMissingBean(MixtureRecordingService.class)
    public MixtureRecordingService mixtureRecordingService(UtilityProvider utilProvider, MixtureRecordingRepository mixtureRecordingRepository, Refresher<MixtureRecording, MixtureRecordingAccessor> mixtureRecordingRefresher) {
        final RepoService<Long, MixtureRecording, MixtureRecordingAccessor> repoService = new CrudRepoService<>(mixtureRecordingRepository, mixtureRecordingRefresher);
        final UpdateService<Long, MixtureRecording, BaseMixtureRecording, UpdateMixtureRecording> updateService = new SimpleUpdateService<>(utilProvider, BaseMixtureRecording.class, UpdateMixtureRecording.class, MixtureRecording.class, Set.of());
        return new MixtureRecordingServiceImpl(repoService, updateService);
    }

    @Bean
    public SkuMaterialService skuMaterialService(UtilityProvider utilProvider) {
        final UpdateService<Long, SkuMaterial, BaseSkuMaterial<?>, UpdateSkuMaterial<?>> updateService = new SimpleUpdateService<>(utilProvider, BaseSkuMaterial.class, UpdateSkuMaterial.class, SkuMaterial.class, Set.of(BaseSkuMaterial.ATTR_SKU));
        return new SkuMaterialService(updateService);
    }

    @Bean
    @ConditionalOnMissingBean(SkuService.class)
    public SkuService skuService(UtilityProvider utilProvider, SkuRepository skuRepository, SkuMaterialService skuMaterialService, Refresher<Sku, SkuAccessor> skuRefresher) {
        final UpdateService<Long, Sku, BaseSku<? extends BaseSkuMaterial<?>>, UpdateSku<? extends UpdateSkuMaterial<?>>> updateService = new SimpleUpdateService<>(utilProvider, BaseSku.class, UpdateSku.class, Sku.class, Set.of(BaseSku.ATTR_MATERIALS));
        final RepoService<Long, Sku, SkuAccessor> repoService = new CrudRepoService<>(skuRepository, skuRefresher);
        return new SkuServiceImpl(repoService, updateService, skuMaterialService);
    }

    @Bean
    public FinishedGoodLotMaterialPortionService finishedGoodMaterialPortionService(UtilityProvider utilProvider) {
        final UpdateService<Long, FinishedGoodLotMaterialPortion, BaseFinishedGoodLotMaterialPortion<?>, UpdateFinishedGoodLotMaterialPortion<?>> updateService = new SimpleUpdateService<>(utilProvider, BaseFinishedGoodLotMaterialPortion.class, UpdateFinishedGoodLotMaterialPortion.class, FinishedGoodLotMaterialPortion.class, Set.of(BaseFinishedGoodLotMaterialPortion.ATTR_FINISHED_GOOD_LOT));
        return new FinishedGoodLotMaterialPortionService(updateService);
    }

    @Bean
    public FinishedGoodLotMixturePortionService finishedGoodMixturePortionService(UtilityProvider utilProvider) {
        final UpdateService<Long, FinishedGoodLotMixturePortion, BaseFinishedGoodLotMixturePortion<?>, UpdateFinishedGoodLotMixturePortion<?>> updateService = new SimpleUpdateService<>(utilProvider, BaseFinishedGoodLotMixturePortion.class, UpdateFinishedGoodLotMixturePortion.class, FinishedGoodLotMixturePortion.class, Set.of(BaseFinishedGoodLotMixturePortion.ATTR_FINISHED_GOOD_LOT));
        return new FinishedGoodLotMixturePortionService(updateService);
    }

    @Bean
    public FinishedGoodLotFinishedGoodLotPortionService finishedGoodLotFinishedGoodLotPortionService(UtilityProvider utilProvider) {
        final UpdateService<Long, FinishedGoodLotFinishedGoodLotPortion, BaseFinishedGoodLotFinishedGoodLotPortion, UpdateFinishedGoodLotFinishedGoodLotPortion> updateService = new SimpleUpdateService<>(utilProvider, BaseFinishedGoodLotFinishedGoodLotPortion.class, UpdateFinishedGoodLotFinishedGoodLotPortion.class, FinishedGoodLotFinishedGoodLotPortion.class, Set.of(BaseFinishedGoodLotFinishedGoodLotPortion.ATTR_FINISHED_GOOD_LOT_TARGET));
        return new FinishedGoodLotFinishedGoodLotPortionService(updateService);
    }

    @Bean
    @ConditionalOnMissingBean(FinishedGoodLotService.class)
    public FinishedGoodLotService finishedGoodService(UtilityProvider utilProvider, FinishedGoodLotMixturePortionService fgMixturePortionService, FinishedGoodLotMaterialPortionService fgMaterialPortionService, FinishedGoodLotFinishedGoodLotPortionService fgLotFinishedGoodPortionService, final FinishedGoodLotRepository finishedGoodRepository, Refresher<FinishedGoodLot, FinishedGoodLotAccessor> finishedGoodRefresher) {
        final UpdateService<Long, FinishedGoodLot, BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<?>, ? extends BaseFinishedGoodLotMaterialPortion<?>>, UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>> updateService = new SimpleUpdateService<>(utilProvider, BaseFinishedGoodLot.class, UpdateFinishedGoodLot.class, FinishedGoodLot.class, Set.of(BaseFinishedGoodLotMaterialPortion.ATTR_FINISHED_GOOD_LOT));

        final RepoService<Long, FinishedGoodLot, FinishedGoodLotAccessor> repoService = new CrudRepoService<>(finishedGoodRepository, finishedGoodRefresher);
        return new FinishedGoodLotService(updateService, fgMixturePortionService, fgMaterialPortionService, fgLotFinishedGoodPortionService, repoService);
    }

    @Bean
    @ConditionalOnMissingBean(FinishedGoodInventoryService.class)
    public FinishedGoodInventoryService finishedGoodInventoryService(AggregationService aggrService, FinishedGoodInventoryRepository finishedGoodInventoryRepository) {
        final FinishedGoodInventoryService finishedGoodInventoryService = new FinishedGoodInventoryServiceImpl(aggrService, finishedGoodInventoryRepository);
        return finishedGoodInventoryService;
    }
}
