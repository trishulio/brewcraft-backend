package io.company.brewcraft.configuration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialPortionRepository;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.repository.MixtureRecordingRepository;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.ProductCategoryRepository;
import io.company.brewcraft.repository.ProductMeasureValueRepository;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.service.AggregationService;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.service.BrewStageStatusServiceImpl;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.BrewTaskServiceImpl;
import io.company.brewcraft.service.CrudService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.InvoiceStatusService;
import io.company.brewcraft.service.LotAggregationService;
import io.company.brewcraft.service.MaterialPortionService;
import io.company.brewcraft.service.MaterialPortionServiceImpl;
import io.company.brewcraft.service.MeasureService;
import io.company.brewcraft.service.MixtureRecordingService;
import io.company.brewcraft.service.MixtureRecordingServiceImpl;
import io.company.brewcraft.service.MixtureService;
import io.company.brewcraft.service.MixtureServiceImpl;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.ProductMeasureValueService;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.StockLotService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.impl.BrewServiceImpl;
import io.company.brewcraft.service.impl.BrewStageServiceImpl;
import io.company.brewcraft.service.impl.FacilityServiceImpl;
import io.company.brewcraft.service.impl.MaterialLotService;
import io.company.brewcraft.service.impl.MeasureServiceImpl;
import io.company.brewcraft.service.impl.ProductCategoryServiceImpl;
import io.company.brewcraft.service.impl.ProductMeasureValueServiceImpl;
import io.company.brewcraft.service.impl.ProductServiceImpl;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.service.impl.SupplierContactServiceImpl;
import io.company.brewcraft.service.impl.SupplierServiceImpl;
import io.company.brewcraft.service.impl.TenantManagementServiceImpl;
import io.company.brewcraft.service.impl.procurement.ProcurementServiceImpl;
import io.company.brewcraft.service.impl.user.UserServiceImpl;
import io.company.brewcraft.service.procurement.ProcurementService;
import io.company.brewcraft.service.user.UserService;
import io.company.brewcraft.util.ThreadLocalUtilityProvider;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.controller.AttributeFilter;

public class ServiceAutoConfigurationTest {

    private ServiceAutoConfiguration serviceAutoConfiguration;

    @BeforeEach
    public void init() {
        this.serviceAutoConfiguration = new ServiceAutoConfiguration();
    }

    @Test
    public void testTenantManagementService_returnsInstanceOfTenantManagementServiceImpl() {
        final TenantManagementService tenantManagementService = this.serviceAutoConfiguration.tenantManagementService(null, null, null);
        assertTrue(tenantManagementService instanceof TenantManagementServiceImpl);
    }

    @Test
    public void testSupplierService_returnsInstanceOfSupplierServiceImpl() {
        final SupplierService supplierService = this.serviceAutoConfiguration.supplierService(null);
        assertTrue(supplierService instanceof SupplierServiceImpl);
    }

    @Test
    public void testSupplierContactService_returnsInstanceOfSupplierContactServiceImpl() {
        final SupplierContactService supplierContactService = this.serviceAutoConfiguration.supplierContactService(null, null);
        assertTrue(supplierContactService instanceof SupplierContactServiceImpl);
    }

    @Test
    public void testFacilityService_returnsInstanceOfFacilityServiceImpl() {
        final FacilityService facilityService = this.serviceAutoConfiguration.facilityService(null);
        assertTrue(facilityService instanceof FacilityServiceImpl);
    }

    @Test
    public void testInvoiceItemService_ReturnsInstanceOfInvoiceItemService() {
        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        this.serviceAutoConfiguration.invoiceItemService(mUtilProvider);
    }

    @Test
    public void testInvoiceService_ReturnsInstanceOfInvoiceService() {
        final InvoiceRepository mInvoiceRepo = mock(InvoiceRepository.class);
        final InvoiceItemService mInvoiceItemService = mock(InvoiceItemService.class);

        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);

        this.serviceAutoConfiguration.invoiceService(mUtilProvider, mInvoiceItemService, mInvoiceRepo);
    }

    @Test
    public void testPurchaseOrderService_ReturnsInstanceOfPurchaseOrderService() {
        final PurchaseOrderRepository mInvoiceRepo = mock(PurchaseOrderRepository.class);

        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        
        this.serviceAutoConfiguration.purchaseOrderService(mUtilProvider, mInvoiceRepo);
    }

    @Test
    public void testInvoiceStatusService_ReturnsInstanceOfInvoiceStatusService() {
        final InvoiceStatusRepository mRepo = mock(InvoiceStatusRepository.class);
        final InvoiceStatusService service = this.serviceAutoConfiguration.invoiceStatusService(mRepo);

        assertTrue(service instanceof InvoiceStatusService);
    }

    @Test
    public void testAttributeFilter_ReturnsInstanceOfAttributeFilter() {
        new AttributeFilter();
    }

    @Test
    public void testShipmentService_ReturnsInstanceOfShipmentService() {
        final ShipmentRepository mShipmentRepo = mock(ShipmentRepository.class);
        final MaterialLotService mMaterialLotService = mock(MaterialLotService.class);

        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);

        this.serviceAutoConfiguration.shipmentService(mUtilProvider, mShipmentRepo, mMaterialLotService);
    }

    @Test
    public void testMaterialLotService_ReturnsInstanceOfMaterialLotService() {
        final UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        final MaterialLotService service = this.serviceAutoConfiguration.materialLotService(mUtilProvider);

        assertSame(MaterialLotService.class, service.getClass());
    }

    @Test
    public void testUtilityProvider_ReturnsInstanceOfThreadLocalUtilityProvider() {
        final UtilityProvider provider = this.serviceAutoConfiguration.utilityProvider();

        assertSame(ThreadLocalUtilityProvider.class, provider.getClass());
    }

    @Test
    public void testAggregationService_ReturnsInstanceOfAggregationService() {
        final AggregationRepository mAggrRepo = mock(AggregationRepository.class);
        final AggregationService service = this.serviceAutoConfiguration.aggrService(mAggrRepo);

        assertSame(AggregationService.class, service.getClass());
    }

    @Test
    public void testLotInventorySerivce_ReturnsInstanceOfMaterialLotInventoryService() {
        final AggregationService mAggrService = mock(AggregationService.class);
        final LotAggregationService service = this.serviceAutoConfiguration.lotInventoryService(mAggrService);

        assertSame(LotAggregationService.class, service.getClass());
    }

    @Test
    public void testProductService_ReturnsInstanceOfProductService() {
        final ProductRepository productRepositoryMock = mock(ProductRepository.class);
        final ProductCategoryService productCategoryServiceMock = mock(ProductCategoryService.class);
        final ProductMeasureValueService productMeasureValueServiceMock = mock(ProductMeasureValueService.class);
        final MeasureService productMeasureServiceMock = mock(MeasureService.class);

        final ProductService service = this.serviceAutoConfiguration.productService(productRepositoryMock, productCategoryServiceMock, productMeasureValueServiceMock, productMeasureServiceMock);

        assertTrue(service instanceof ProductServiceImpl);
    }

    @Test
    public void testProductCategory_ReturnsInstanceOfProductCategoryService() {
        final ProductCategoryRepository productCategoryRepositoryMock = mock(ProductCategoryRepository.class);
        final ProductCategoryService service = this.serviceAutoConfiguration.productCategoryService(productCategoryRepositoryMock);

        assertTrue(service instanceof ProductCategoryServiceImpl);
    }

    @Test
    public void testMeasureService_ReturnsInstanceOfMeasureService() {
        final MeasureRepository measureRepositoryMock = mock(MeasureRepository.class);
        final MeasureService service = this.serviceAutoConfiguration.measureService(measureRepositoryMock);

        assertTrue(service instanceof MeasureServiceImpl);
    }

    @Test
    public void testProductMeasureValueService_ReturnsInstanceOfProductMeasureValueService() {
        final ProductMeasureValueRepository productMeasureValueRepositoryMock = mock(ProductMeasureValueRepository.class);
        final ProductMeasureValueService service = this.serviceAutoConfiguration.productMeasureValueService(productMeasureValueRepositoryMock);

        assertTrue(service instanceof ProductMeasureValueServiceImpl);
    }

    @Test
    public void testUserService_ReturnsInstanceOfUserService() {
        final UserRepository userRepositoryMock = mock(UserRepository.class);
        final IdpUserRepository idpUserRepositoryMock = mock(IdpUserRepository.class);

        final UserService service = this.serviceAutoConfiguration.userService(userRepositoryMock, idpUserRepositoryMock);

        assertTrue(service instanceof UserServiceImpl);
    }

    @Test
    public void testProcurementService_ReturnsInstanceOfProcurementService() {
        final InvoiceService invoiceServiceMock = mock(InvoiceService.class);
        final PurchaseOrderService purchaseOrderServiceMock = mock(PurchaseOrderService.class);
        final ShipmentService shipmentServiceMock = mock(ShipmentService.class);

        final ProcurementService service = this.serviceAutoConfiguration.procurementService(invoiceServiceMock, purchaseOrderServiceMock, shipmentServiceMock);

        assertTrue(service instanceof ProcurementServiceImpl);
    }

    @Test
    public void testBrewService_ReturnsInstanceOfBrewService() {
        final BrewRepository brewRepositoryMock = mock(BrewRepository.class);
        final BrewService service = this.serviceAutoConfiguration.brewService(brewRepositoryMock);

        assertTrue(service instanceof BrewServiceImpl);
    }

    @Test
    public void testBrewTaskService_ReturnsInstanceOfBrewTaskService() {
        final BrewTaskRepository brewTaskRepositoryMock = mock(BrewTaskRepository.class);
        final BrewTaskService service = this.serviceAutoConfiguration.brewTaskService(brewTaskRepositoryMock);

        assertTrue(service instanceof BrewTaskServiceImpl);
    }

    @Test
    public void testBrewStageService_ReturnsInstanceOfBrewStageService() {
        final BrewStageRepository brewStageRepositoryMock = mock(BrewStageRepository.class);

        final BrewStageService service = this.serviceAutoConfiguration.brewStageService(brewStageRepositoryMock);

        assertTrue(service instanceof BrewStageServiceImpl);
    }

    @Test
    public void testBrewStageStatusService_ReturnsInstanceOfBrewStageStatusService() {
        final BrewStageStatusRepository brewStageStatusRepositoryMock = mock(BrewStageStatusRepository.class);

        final BrewStageStatusService service = this.serviceAutoConfiguration.brewStageStatusService(brewStageStatusRepositoryMock);

        assertTrue(service instanceof BrewStageStatusServiceImpl);
    }

    @Test
    public void testMixtureService_ReturnsInstanceOfMixtureService() {
        final MixtureRepository mixtureRepositoryMock = mock(MixtureRepository.class);
        final MixtureService service = this.serviceAutoConfiguration.mixtureService(mixtureRepositoryMock);

        assertTrue(service instanceof MixtureServiceImpl);
    }

    @Test
    public void testMaterialPortionService_ReturnsInstanceOfMaterialPortionService() {
        final MaterialPortionRepository materialPortionRepositoryMock = mock(MaterialPortionRepository.class);
        final StockLotService stockLotServiceMock = mock(StockLotService.class);
        final MaterialPortionService service = this.serviceAutoConfiguration.materialPortionService(materialPortionRepositoryMock, stockLotServiceMock);

        assertTrue(service instanceof MaterialPortionServiceImpl);
    }

    @Test
    public void testMixtureRecordingService_ReturnsInstanceOfMixtureRecordingService() {
        final MixtureRecordingRepository mixtureRecordingRepositoryMock = mock(MixtureRecordingRepository.class);

        final MixtureRecordingService service = this.serviceAutoConfiguration.mixtureRecordingService(mixtureRecordingRepositoryMock);

        assertTrue(service instanceof MixtureRecordingServiceImpl);
    }

}
