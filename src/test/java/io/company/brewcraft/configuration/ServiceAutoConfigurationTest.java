package io.company.brewcraft.configuration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialLotAggregationRepository;
import io.company.brewcraft.repository.MaterialPortionRepository;
import io.company.brewcraft.repository.MixtureRecordingRepository;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.ProductCategoryRepository;
import io.company.brewcraft.repository.ProductMeasureRepository;
import io.company.brewcraft.repository.ProductMeasureValueRepository;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.service.BrewStageStatusServiceImpl;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.BrewTaskServiceImpl;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.InvoiceStatusService;
import io.company.brewcraft.service.MaterialLotInventoryService;
import io.company.brewcraft.service.MaterialPortionService;
import io.company.brewcraft.service.MaterialPortionServiceImpl;
import io.company.brewcraft.service.MixtureRecordingService;
import io.company.brewcraft.service.MixtureRecordingServiceImpl;
import io.company.brewcraft.service.MixtureService;
import io.company.brewcraft.service.MixtureServiceImpl;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.ProductMeasureService;
import io.company.brewcraft.service.ProductMeasureValueService;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.impl.BrewServiceImpl;
import io.company.brewcraft.service.impl.BrewStageServiceImpl;
import io.company.brewcraft.service.impl.FacilityServiceImpl;
import io.company.brewcraft.service.impl.MaterialLotService;
import io.company.brewcraft.service.impl.ProductCategoryServiceImpl;
import io.company.brewcraft.service.impl.ProductMeasureServiceImpl;
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
        serviceAutoConfiguration = new ServiceAutoConfiguration();
    }

    @Test
    public void testTenantManagementService_returnsInstanceOfTenantManagementServiceImpl() {
        TenantManagementService tenantManagementService = serviceAutoConfiguration.tenantManagementService(null, null, null);
        assertTrue(tenantManagementService instanceof TenantManagementServiceImpl);
    }

    @Test
    public void testSupplierService_returnsInstanceOfSupplierServiceImpl() {
        SupplierService supplierService = serviceAutoConfiguration.supplierService(null);
        assertTrue(supplierService instanceof SupplierServiceImpl);
    }

    @Test
    public void testSupplierContactService_returnsInstanceOfSupplierContactServiceImpl() {
        SupplierContactService supplierContactService = serviceAutoConfiguration.supplierContactService(null, null);
        assertTrue(supplierContactService instanceof SupplierContactServiceImpl);
    }

    @Test
    public void testFacilityService_returnsInstanceOfFacilityServiceImpl() {
        FacilityService facilityService = serviceAutoConfiguration.facilityService(null);
        assertTrue(facilityService instanceof FacilityServiceImpl);
    }

    @Test
    public void testInvoiceItemService_ReturnsInstanceOfInvoiceItemService() {
        UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        InvoiceItemService service = serviceAutoConfiguration.invoiceItemService(mUtilProvider);

        assertTrue(service instanceof InvoiceItemService);
    }

    @Test
    public void testInvoiceService_ReturnsInstanceOfInvoiceService() {
        InvoiceRepository mInvoiceRepo = mock(InvoiceRepository.class);
        InvoiceItemService mInvoiceItemService = mock(InvoiceItemService.class);

        InvoiceService service = serviceAutoConfiguration.invoiceService(mInvoiceRepo, mInvoiceItemService);
        assertTrue(service instanceof InvoiceService);
    }

    @Test
    public void testPurchaseOrderService_ReturnsInstanceOfPurchaseOrderService() {
        PurchaseOrderRepository poRepo = mock(PurchaseOrderRepository.class);
        PurchaseOrderService service = serviceAutoConfiguration.purchaseOrderService(poRepo);

        assertTrue(service instanceof PurchaseOrderService);
    }

    @Test
    public void testInvoiceStatusService_ReturnsInstanceOfInvoiceStatusService() {
        InvoiceStatusRepository mRepo = mock(InvoiceStatusRepository.class);
        InvoiceStatusService service = serviceAutoConfiguration.invoiceStatusService(mRepo);

        assertTrue(service instanceof InvoiceStatusService);
    }

    @Test
    public void testAttributeFilter_ReturnsInstanceOfAttributeFilter() {
        new AttributeFilter();
    }
    
    @Test
    public void testShipmentService_ReturnsInstanceOfShipmentService() {
        ShipmentRepository mRepo = mock(ShipmentRepository.class);
        MaterialLotService mItemService = mock(MaterialLotService.class);

        ShipmentService service = serviceAutoConfiguration.shipmentService(mRepo, mItemService);
        
        assertSame(ShipmentService.class, service.getClass());
    }

    @Test
    public void testMaterialLotService_ReturnsInstanceOfMaterialLotService() {
        UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        MaterialLotService service = serviceAutoConfiguration.materialLotService(mUtilProvider);
        
        assertSame(MaterialLotService.class, service.getClass());
    }
    
    @Test
    public void testUtilityProvider_ReturnsInstanceOfThreadLocalUtilityProvider() {
        UtilityProvider provider = serviceAutoConfiguration.utilityProvider();
        
        assertSame(ThreadLocalUtilityProvider.class, provider.getClass());
    }
    
    @Test
    public void testLotInventorySerivce_RetunrsInstanceOfMaterialLotInventoryServiceWithAggrRepo() {
        MaterialLotAggregationRepository mRepo = mock(MaterialLotAggregationRepository.class);
        MaterialLotInventoryService service = serviceAutoConfiguration.lotInventoryService(mRepo);
        
        assertSame(MaterialLotInventoryService.class, service.getClass());
    }
    
    @Test
    public void testProductService_ReturnsInstanceOfProductService() {
    	ProductRepository productRepositoryMock = mock(ProductRepository.class);
    	ProductCategoryService productCategoryServiceMock = mock(ProductCategoryService.class);
    	ProductMeasureValueService productMeasureValueServiceMock = mock(ProductMeasureValueService.class);
    	ProductMeasureService productMeasureServiceMock = mock(ProductMeasureService.class);

        ProductService service = serviceAutoConfiguration.productService(productRepositoryMock, productCategoryServiceMock, productMeasureValueServiceMock, productMeasureServiceMock);
        
        assertTrue(service instanceof ProductServiceImpl);
    }
    
    @Test
    public void testProductCategory_ReturnsInstanceOfProductCategoryService() {
        ProductCategoryRepository productCategoryRepositoryMock = mock(ProductCategoryRepository.class);
        ProductCategoryService service = serviceAutoConfiguration.productCategoryService(productCategoryRepositoryMock);
        
        assertTrue(service instanceof ProductCategoryServiceImpl);
    }
    
    @Test
    public void testProductMeasureService_ReturnsInstanceOfProductMeasureService() {
    	ProductMeasureRepository productMeasureRepositoryMock = mock(ProductMeasureRepository.class);
        ProductMeasureService service = serviceAutoConfiguration.productMeasureService(productMeasureRepositoryMock);
        
        assertTrue(service instanceof ProductMeasureServiceImpl);
    }
    
    @Test
    public void testProductMeasureValueService_ReturnsInstanceOfProductMeasureValueService() {
        ProductMeasureValueRepository productMeasureValueRepositoryMock = mock(ProductMeasureValueRepository.class);
        ProductMeasureValueService service = serviceAutoConfiguration.productMeasureValueService(productMeasureValueRepositoryMock);
        
        assertTrue(service instanceof ProductMeasureValueServiceImpl);
    }
    
    @Test
    public void testUserService_ReturnsInstanceOfUserService() {
    	UserRepository userRepositoryMock = mock(UserRepository.class);
    	IdpUserRepository idpUserRepositoryMock = mock(IdpUserRepository.class);

    	UserService service = serviceAutoConfiguration.userService(userRepositoryMock, idpUserRepositoryMock);
        
        assertTrue(service instanceof UserServiceImpl);
    }
    
    @Test
    public void testProcurementService_ReturnsInstanceOfProcurementService() {
    	InvoiceService invoiceServiceMock = mock(InvoiceService.class);
    	PurchaseOrderService purchaseOrderServiceMock = mock(PurchaseOrderService.class);
    	ShipmentService shipmentServiceMock = mock(ShipmentService.class);

    	ProcurementService service = serviceAutoConfiguration.procurementService(invoiceServiceMock, purchaseOrderServiceMock, shipmentServiceMock);
        
        assertTrue(service instanceof ProcurementServiceImpl);
    }
    
    @Test
    public void testBrewService_ReturnsInstanceOfBrewService() {
    	BrewRepository brewRepositoryMock = mock(BrewRepository.class);
    	BrewService service = serviceAutoConfiguration.brewService(brewRepositoryMock);
        
        assertTrue(service instanceof BrewServiceImpl);
    }
    
    @Test
    public void testBrewTaskService_ReturnsInstanceOfBrewTaskService() {
    	BrewTaskRepository brewTaskRepositoryMock = mock(BrewTaskRepository.class);
    	BrewTaskService service = serviceAutoConfiguration.brewTaskService(brewTaskRepositoryMock);
        
        assertTrue(service instanceof BrewTaskServiceImpl);
    }
    
    @Test
    public void testBrewStageService_ReturnsInstanceOfBrewStageService() {
    	BrewStageRepository brewStageRepositoryMock = mock(BrewStageRepository.class);
    	BrewService brewServiceMock = mock(BrewService.class);

    	BrewStageService service = serviceAutoConfiguration.brewStageService(brewStageRepositoryMock, brewServiceMock);
        
        assertTrue(service instanceof BrewStageServiceImpl);
    }
    
    @Test
    public void testBrewStageStatusService_ReturnsInstanceOfBrewStageStatusService() {
    	BrewStageStatusRepository brewStageStatusRepositoryMock = mock(BrewStageStatusRepository.class);
    	
    	BrewStageStatusService service = serviceAutoConfiguration.brewStageStatusService(brewStageStatusRepositoryMock);
        
        assertTrue(service instanceof BrewStageStatusServiceImpl);
    }
    
    @Test
    public void testMixtureService_ReturnsInstanceOfMixtureService() {
    	MixtureRepository mixtureRepositoryMock = mock(MixtureRepository.class);
    	MixtureService service = serviceAutoConfiguration.mixtureService(mixtureRepositoryMock);
        
        assertTrue(service instanceof MixtureServiceImpl);
    }
    
    @Test
    public void testMaterialPortionService_ReturnsInstanceOfMaterialPortionService() {
    	MaterialPortionRepository materialPortionRepositoryMock = mock(MaterialPortionRepository.class);
    	MaterialPortionService service = serviceAutoConfiguration.materialPortionService(materialPortionRepositoryMock);
        
        assertTrue(service instanceof MaterialPortionServiceImpl);
    }
    
    @Test
    public void testMixtureRecordingService_ReturnsInstanceOfMixtureRecordingService() {
    	MixtureRecordingRepository mixtureRecordingRepositoryMock = mock(MixtureRecordingRepository.class);
    	MixtureService mixtureServiceMock = mock(MixtureService.class);

    	MixtureRecordingService service = serviceAutoConfiguration.mixtureRecordingService(mixtureRecordingRepositoryMock, mixtureServiceMock);
        
        assertTrue(service instanceof MixtureRecordingServiceImpl);
    }
    
}
