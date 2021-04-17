package io.company.brewcraft.configuration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.InvoiceStatusService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.impl.FacilityServiceImpl;
import io.company.brewcraft.service.impl.ShipmentItemService;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.service.impl.SupplierContactServiceImpl;
import io.company.brewcraft.service.impl.SupplierServiceImpl;
import io.company.brewcraft.service.impl.TenantManagementServiceImpl;
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
        ShipmentItemService mItemService = mock(ShipmentItemService.class);

        ShipmentService service = serviceAutoConfiguration.shipmentService(mRepo, mItemService);
        
        assertSame(ShipmentService.class, service.getClass());
    }

    @Test
    public void testShipmentItemService_ReturnsInstanceOfShipmentItemService() {
        UtilityProvider mUtilProvider = mock(UtilityProvider.class);
        ShipmentItemService service = serviceAutoConfiguration.shipmentItemService(mUtilProvider);
        
        assertSame(ShipmentItemService.class, service.getClass());
    }
    
    @Test
    public void testUtilityProvider_ReturnsInstanceOfThreadLocalUtilityProvider() {
        UtilityProvider provider = serviceAutoConfiguration.utilityProvider();
        
        assertSame(ThreadLocalUtilityProvider.class, provider.getClass());
    }
}
