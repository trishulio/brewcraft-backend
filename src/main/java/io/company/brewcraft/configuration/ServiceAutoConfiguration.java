package io.company.brewcraft.configuration;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.controller.IaasObjectStoreFileController;
import io.company.brewcraft.controller.UserDtoDecorator;
import io.company.brewcraft.dto.BaseEquipment;
import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.dto.UpdateFinishedGoodLot;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.migration.MigrationManager;
import io.company.brewcraft.migration.TenantRegister;
import io.company.brewcraft.model.AdminTenant;
import io.company.brewcraft.model.BaseFinishedGoodLot;
import io.company.brewcraft.model.BaseFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.BaseFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.BaseFinishedGoodLotMixturePortion;
import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasObjectStoreFile;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.BaseIaasUser;
import io.company.brewcraft.model.BaseIaasUserTenantMembership;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseMixtureMaterialPortion;
import io.company.brewcraft.model.BaseMixtureRecording;
import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.BaseSku;
import io.company.brewcraft.model.BaseSkuMaterial;
import io.company.brewcraft.model.BaseTenant;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasObjectStoreFile;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRepositoryProvider;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;
import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.model.IaasUserTenantMembership;
import io.company.brewcraft.model.IaasUserTenantMembershipId;
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
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantAccessor;
import io.company.brewcraft.model.UpdateFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMixturePortion;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.model.UpdateIaasObjectStore;
import io.company.brewcraft.model.UpdateIaasObjectStoreFile;
import io.company.brewcraft.model.UpdateIaasPolicy;
import io.company.brewcraft.model.UpdateIaasRole;
import io.company.brewcraft.model.UpdateIaasRolePolicyAttachment;
import io.company.brewcraft.model.UpdateIaasUser;
import io.company.brewcraft.model.UpdateIaasUserTenantMembership;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdateMixtureMaterialPortion;
import io.company.brewcraft.model.UpdateMixtureRecording;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.model.UpdateSku;
import io.company.brewcraft.model.UpdateSkuMaterial;
import io.company.brewcraft.model.UpdateTenant;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.user.BaseUser;
import io.company.brewcraft.model.user.BaseUserRole;
import io.company.brewcraft.model.user.UpdateUser;
import io.company.brewcraft.model.user.UpdateUserRole;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
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
import io.company.brewcraft.service.BlockingAsyncExecutor;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.service.BrewStageStatusServiceImpl;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.BrewTaskServiceImpl;
import io.company.brewcraft.service.BulkIaasClient;
import io.company.brewcraft.service.CrudRepoService;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.service.FinishedGoodInventoryServiceImpl;
import io.company.brewcraft.service.FinishedGoodLotAccessor;
import io.company.brewcraft.service.FinishedGoodLotFinishedGoodLotPortionService;
import io.company.brewcraft.service.FinishedGoodLotMaterialPortionService;
import io.company.brewcraft.service.FinishedGoodLotMixturePortionService;
import io.company.brewcraft.service.FinishedGoodLotService;
import io.company.brewcraft.service.IaasAuthorizationFetch;
import io.company.brewcraft.service.IaasClient;
import io.company.brewcraft.service.IaasIdpTenantService;
import io.company.brewcraft.service.IaasObjectStoreFileService;
import io.company.brewcraft.service.IaasObjectStoreService;
import io.company.brewcraft.service.IaasPolicyService;
import io.company.brewcraft.service.IaasRepository;
import io.company.brewcraft.service.IaasRepositoryProviderProxy;
import io.company.brewcraft.service.IaasRolePolicyAttachmentService;
import io.company.brewcraft.service.IaasRoleService;
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
import io.company.brewcraft.service.ProductDtoDecorator;
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
import io.company.brewcraft.service.TemporaryImageSrcDecorator;
import io.company.brewcraft.service.TenantContextIaasAuthorizationFetch;
import io.company.brewcraft.service.TenantIaasAuthResourceMapper;
import io.company.brewcraft.service.TenantIaasAuthService;
import io.company.brewcraft.service.TenantIaasIdpResourcesMapper;
import io.company.brewcraft.service.TenantIaasIdpService;
import io.company.brewcraft.service.TenantIaasResourceBuilder;
import io.company.brewcraft.service.TenantIaasService;
import io.company.brewcraft.service.TenantIaasUserMapper;
import io.company.brewcraft.service.TenantIaasUserService;
import io.company.brewcraft.service.TenantIaasVfsResourceMapper;
import io.company.brewcraft.service.TenantIaasVfsService;
import io.company.brewcraft.service.TransactionService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.UserRoleService;
import io.company.brewcraft.service.UserSalutationService;
import io.company.brewcraft.service.impl.BrewServiceImpl;
import io.company.brewcraft.service.impl.BrewStageServiceImpl;
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
import io.company.brewcraft.service.impl.TenantService;
import io.company.brewcraft.service.impl.procurement.ProcurementService;
import io.company.brewcraft.service.impl.user.UserService;
import io.company.brewcraft.service.mapper.TenantIaasIdpTenantMapper;
import io.company.brewcraft.util.ThreadLocalUtilityProvider;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.controller.AttributeFilter;

@Configuration
public class ServiceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserDtoDecorator.class)
    public UserDtoDecorator userDecorator(TemporaryImageSrcDecorator imgDecorator) {
        return new UserDtoDecorator(imgDecorator);
    }

    @Bean
    @ConditionalOnMissingBean(TemporaryImageSrcDecorator.class)
    public TemporaryImageSrcDecorator temporaryImageSrcDecorator(IaasObjectStoreFileController objectStoreFileController) {
        return new TemporaryImageSrcDecorator(objectStoreFileController);
    }

    @Bean
    @ConditionalOnMissingBean(ProductDtoDecorator.class)
    public ProductDtoDecorator productDtoDecorator(TemporaryImageSrcDecorator temporaryImageSrcDecorator) {
        return new ProductDtoDecorator(temporaryImageSrcDecorator);
    }

    @Bean
    @ConditionalOnMissingBean(BlockingAsyncExecutor.class)
    public BlockingAsyncExecutor executor() {
        return new BlockingAsyncExecutor();
    }

    @Bean
    @ConditionalOnMissingBean(Tenant.class)
    public Tenant adminTenant(@Value("${app.config.tenant.admin.id}") String id, @Value("${app.config.tenant.admin.name}") String name) {
        UUID adminId = UUID.fromString(id);

        return new AdminTenant(adminId, name);
    }

    @Bean
    @ConditionalOnMissingBean(TenantService.class)
    public TenantService tenantManagementService(TenantRepository tenantRepository, MigrationManager migrationManager, TenantRegister tenantRegister, TenantIaasService tenantIaasService, Refresher<Tenant, TenantAccessor> tenantRefresher, UtilityProvider utilProvider, Tenant adminTenant) {
        RepoService<UUID, Tenant, TenantAccessor> repoService = new CrudRepoService<>(tenantRepository, tenantRefresher);
        UpdateService<UUID, Tenant, BaseTenant, UpdateTenant> updateService = new SimpleUpdateService<>(utilProvider, BaseTenant.class, UpdateTenant.class, Tenant.class, Set.of(""));

        final TenantService tenantService = new TenantService(
            adminTenant,
            repoService,
            updateService,
            tenantRepository,
            migrationManager,
            tenantIaasService
        );
        return tenantService;
    }

    @Bean
    @ConditionalOnMissingBean(TenantIaasAuthService.class)
    public TenantIaasAuthService tenantIaasAuthService(IaasRoleService roleService, TenantIaasResourceBuilder resourceBuilder) {
        return new TenantIaasAuthService(TenantIaasAuthResourceMapper.INSTANCE, roleService, resourceBuilder);
    }

    @Bean
    @ConditionalOnMissingBean(IaasIdpTenantService.class)
    public IaasIdpTenantService iaasIdpTenantService(UtilityProvider utilProvider, BlockingAsyncExecutor executor, IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> client) {
        UpdateService<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> updateService = new SimpleUpdateService<>(utilProvider, BaseIaasIdpTenant.class, UpdateIaasIdpTenant.class, IaasIdpTenant.class, java.util.Set.of());
        IaasRepository<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> iaasRepo = new BulkIaasClient<>(executor, client);

        return new IaasIdpTenantService(updateService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(TenantIaasIdpService.class)
    public TenantIaasIdpService tenantIaasIdpService(IaasIdpTenantService idpService) {
        return new TenantIaasIdpService(idpService, TenantIaasIdpResourcesMapper.INSTANCE);
    }

    @Bean
    @ConditionalOnMissingBean(TenantIaasService.class)
    public TenantIaasService tenantIaasService(TenantIaasAuthService authService, TenantIaasIdpService idpService, TenantIaasVfsService vfsService) {
        return new TenantIaasService(authService, idpService, vfsService, TenantIaasIdpTenantMapper.INSTANCE);
    }

    @Bean
    @ConditionalOnMissingBean(TenantIaasVfsService.class)
    public TenantIaasVfsService iaasVfsService(IaasPolicyService iaasPolicyService, IaasObjectStoreService iaasObjectStoreService, IaasRolePolicyAttachmentService iaasRolePolicyAttachmentService, TenantIaasResourceBuilder resourceBuilder) {
        return new TenantIaasVfsService(TenantIaasVfsResourceMapper.INSTANCE, iaasPolicyService, iaasObjectStoreService, iaasRolePolicyAttachmentService, resourceBuilder);
    }

    @Bean
    @ConditionalOnMissingBean(TenantIaasUserService.class)
    public TenantIaasUserService tenantIaasUserService(
            BlockingAsyncExecutor executor,
            ContextHolder ctxHolder,
            IaasClient<String, IaasUser, BaseIaasUser, UpdateIaasUser> userClient,
            IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> membershipClient
    ) {
        IaasRepository<String, IaasUser, BaseIaasUser, UpdateIaasUser> userRepository = new BulkIaasClient<>(executor, userClient);
        IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> membershipRepository = new BulkIaasClient<>(executor, membershipClient);

        return new TenantIaasUserService(userRepository, membershipRepository, TenantIaasUserMapper.INSTANCE, TenantIaasIdpTenantMapper.INSTANCE, ctxHolder);
    }

    @Bean
    @ConditionalOnMissingBean(IaasPolicyService.class)
    public IaasPolicyService iaasPolicyService(UtilityProvider utilProvider, BlockingAsyncExecutor executor, IaasClient<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> iaasClient) {
        UpdateService<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> updateService = new SimpleUpdateService<>(utilProvider, BaseIaasPolicy.class, UpdateIaasPolicy.class, IaasPolicy.class, Set.of());
        IaasRepository<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> iaasRepo = new BulkIaasClient<>(executor, iaasClient);

        return new IaasPolicyService(updateService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(IaasRoleService.class)
    public IaasRoleService iaasRoleService(UtilityProvider utilProvider, BlockingAsyncExecutor executor, IaasClient<String, IaasRole, BaseIaasRole, UpdateIaasRole> iaasClient) {
        UpdateService<String, IaasRole, BaseIaasRole, UpdateIaasRole> updateService = new SimpleUpdateService<>(utilProvider, BaseIaasRole.class, UpdateIaasRole.class, IaasRole.class, Set.of());
        IaasRepository<String, IaasRole, BaseIaasRole, UpdateIaasRole> iaasRepo = new BulkIaasClient<>(executor, iaasClient);

        return new IaasRoleService(updateService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(IaasObjectStoreService.class)
    public IaasObjectStoreService iaasObjectStoreService(UtilityProvider utilProvider, BlockingAsyncExecutor executor, IaasClient<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> iaasClient) {
        UpdateService<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> updateService = new SimpleUpdateService<>(utilProvider, BaseIaasObjectStore.class, UpdateIaasObjectStore.class, IaasObjectStore.class, Set.of());
        IaasRepository<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> iaasRepo = new BulkIaasClient<>(executor, iaasClient);

        return new IaasObjectStoreService(updateService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(IaasRolePolicyAttachmentService.class)
    public IaasRolePolicyAttachmentService iaasRolePolicyAttachmentService(UtilityProvider utilProvider, BlockingAsyncExecutor executor, IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment> iaasClient) {
        UpdateService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment> updateService = new SimpleUpdateService<>(utilProvider, BaseIaasRolePolicyAttachment.class, UpdateIaasRolePolicyAttachment.class, IaasRolePolicyAttachment.class, Set.of());
        IaasRepository<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment> iaasRepo = new BulkIaasClient<>(executor, iaasClient);
        return new IaasRolePolicyAttachmentService(updateService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(IaasObjectStoreFileService.class)
    public IaasObjectStoreFileService objectStoreFileService(UtilityProvider utilProvider, BlockingAsyncExecutor executor, IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> iaasObjectStoreFileClientProvider) {
        final UpdateService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> updateService = new SimpleUpdateService<>(utilProvider, BaseIaasObjectStoreFile.class, UpdateIaasObjectStoreFile.class, IaasObjectStoreFile.class, Set.of(IaasObjectStoreFile.ATTR_MIN_VALID_UNTIL));
        IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> iaasRepo = new IaasRepositoryProviderProxy<>(iaasObjectStoreFileClientProvider);

        return new IaasObjectStoreFileService(updateService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(TenantContextIaasAuthorizationFetch.class)
    public TenantContextIaasAuthorizationFetch tenantIaasAuthorizationFetch(IaasAuthorizationFetch authFetcher, ContextHolder contextHolder) {
        return new TenantContextIaasAuthorizationFetch(authFetcher, contextHolder);
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
    @ConditionalOnMissingBean(InvoiceItemService.class)
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
    public EquipmentService equipmentService(UtilityProvider utilProvider, EquipmentRepository equipmentRepository, Refresher<Equipment, EquipmentAccessor> userRefresher) {
        final UpdateService<Long, Equipment, BaseEquipment, UpdateEquipment> updateService = new SimpleUpdateService<>(utilProvider, BaseEquipment.class, UpdateEquipment.class, Equipment.class, Set.of());
        final RepoService<Long, Equipment, EquipmentAccessor> repoService = new CrudRepoService<>(equipmentRepository, userRefresher);
        return new EquipmentService(updateService, repoService);
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
    public UserRoleService userRoleService(UtilityProvider utilProvider, UserRoleRepository userRoleRepository, Refresher<UserRole, UserRoleAccessor> userRoleRefresher) {
        final UpdateService<Long, UserRole, BaseUserRole, UpdateUserRole> updateService = new SimpleUpdateService<>(utilProvider, BaseUserRole.class, UpdateUserRole.class, UserRole.class, Set.of());
        final RepoService<Long, UserRole, UserRoleAccessor> repoService = new CrudRepoService<>(userRoleRepository, userRoleRefresher);
        return new UserRoleService(updateService, repoService);
    }

    @Bean
    @ConditionalOnMissingBean(UserSalutationService.class)
    public UserSalutationService userSalutationService(UserSalutationRepository userSalutationRepository) {
        final UserSalutationService userSalutationService = new UserSalutationService(userSalutationRepository);
        return userSalutationService;
    }

    @Bean
    @ConditionalOnMissingBean(UserService.class)
    public UserService userService(UtilityProvider utilProvider, UserRepository userRepository, Refresher<User, UserAccessor> userRefresher, TenantIaasUserService iaasService) {
        final UpdateService<Long, User, BaseUser, UpdateUser> updateService = new SimpleUpdateService<>(utilProvider, BaseUser.class, UpdateUser.class, User.class, Set.of());
        final RepoService<Long, User, UserAccessor> repoService = new CrudRepoService<>(userRepository, userRefresher);
        return new UserService(updateService, repoService, userRepository, iaasService);
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
    @ConditionalOnMissingBean(SkuMaterialService.class)
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
    @ConditionalOnMissingBean(FinishedGoodLotMaterialPortionService.class)
    public FinishedGoodLotMaterialPortionService finishedGoodMaterialPortionService(UtilityProvider utilProvider) {
        final UpdateService<Long, FinishedGoodLotMaterialPortion, BaseFinishedGoodLotMaterialPortion<?>, UpdateFinishedGoodLotMaterialPortion<?>> updateService = new SimpleUpdateService<>(utilProvider, BaseFinishedGoodLotMaterialPortion.class, UpdateFinishedGoodLotMaterialPortion.class, FinishedGoodLotMaterialPortion.class, Set.of(BaseFinishedGoodLotMaterialPortion.ATTR_FINISHED_GOOD_LOT));
        return new FinishedGoodLotMaterialPortionService(updateService);
    }

    @Bean
    @ConditionalOnMissingBean(FinishedGoodLotMixturePortionService.class)
    public FinishedGoodLotMixturePortionService finishedGoodMixturePortionService(UtilityProvider utilProvider) {
        final UpdateService<Long, FinishedGoodLotMixturePortion, BaseFinishedGoodLotMixturePortion<?>, UpdateFinishedGoodLotMixturePortion<?>> updateService = new SimpleUpdateService<>(utilProvider, BaseFinishedGoodLotMixturePortion.class, UpdateFinishedGoodLotMixturePortion.class, FinishedGoodLotMixturePortion.class, Set.of(BaseFinishedGoodLotMixturePortion.ATTR_FINISHED_GOOD_LOT));
        return new FinishedGoodLotMixturePortionService(updateService);
    }

    @Bean
    @ConditionalOnMissingBean(FinishedGoodLotFinishedGoodLotPortionService.class)
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
