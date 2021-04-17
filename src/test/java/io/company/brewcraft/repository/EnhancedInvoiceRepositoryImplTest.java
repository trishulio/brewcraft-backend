package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedInvoiceRepositoryImplTest {

    private EnhancedInvoiceRepository repo;

    private InvoiceStatusRepository mStatusRepo;
    private PurchaseOrderRepository mPoRepo;
    private MaterialRepository mMaterialRepo;

    @BeforeEach
    public void init() {
        mStatusRepo = mock(InvoiceStatusRepository.class);
        mPoRepo = mock(PurchaseOrderRepository.class);
        mMaterialRepo = mock(MaterialRepository.class);

        repo = new EnhancedInvoiceRepositoryImpl(mStatusRepo, mPoRepo, mMaterialRepo);
    }

    @Test
    public void testSave_ReturnsInvoice_WithRefreshedChildEntities() {
        doReturn(Optional.of(new PurchaseOrder(1L))).when(mPoRepo).findById(1L);
        doReturn(Optional.of(new InvoiceStatus(2L, "FINAL"))).when(mStatusRepo).findByName("FINAL");

        doReturn(List.of(
           new Material(5L, "Material_5", "Description_5", null, "UPC_5", null, null, null, 5),
           new Material(4L, "Material_4", "Description_4", null, "UPC_4", null, null, null, 4),
           new Material(3L, "Material_3", "Description_3", null, "UPC_3", null, null, null, 3))
       ).when(mMaterialRepo).findAllById(Set.of(3L, 4L, 5L));

       InvoiceItem item1 = new InvoiceItem(11L);
       InvoiceItem item2 = new InvoiceItem(12L);
       InvoiceItem item3 = new InvoiceItem(13L);
       item1.setMaterial(new Material(3L));
       item2.setMaterial(new Material(4L));
       item3.setMaterial(new Material(5L));
       List<InvoiceItem> items = List.of(item1, item2, item3);

       Invoice invoice = new Invoice();
       invoice.setItems(items);
       invoice.setStatus(new InvoiceStatus(null, "FINAL"));

       repo.refresh(1L, invoice);

       assertEquals(new PurchaseOrder(1L), invoice.getPurchaseOrder());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       Iterator<InvoiceItem> it = invoice.getItems().iterator();
       assertEquals(new Material(3L, "Material_3", "Description_3", null, "UPC_3", null, null, null, 3), it.next().getMaterial());
       assertEquals(new Material(4L, "Material_4", "Description_4", null, "UPC_4", null, null, null, 4), it.next().getMaterial());
       assertEquals(new Material(5L, "Material_5", "Description_5", null, "UPC_5", null, null, null, 5), it.next().getMaterial());
   }

   @Test
   public void testSave_ThrowsEntityNotFoundException_WhenPurchaseOrderDoesNotExist() {
       doReturn(Optional.empty()).when(mPoRepo).findById(1L);

       assertThrows(EntityNotFoundException.class, () -> repo.refresh(1L, new Invoice()), "PurchaseOrder not found with id: 1");
   }

   @Test
   public void testSave_ThrowsEntityNotFoundException_WhenInvoiceStatusNameIsNonExistent() {
       doReturn(Optional.of(new PurchaseOrder(1L))).when(mPoRepo).findById(1L);
       doReturn(Optional.empty()).when(mStatusRepo).findByName("PENDING");

       Invoice invoice = new Invoice();
       invoice.setStatus(new InvoiceStatus(2L, "PENDING"));

       assertThrows(EntityNotFoundException.class, () -> repo.refresh(1L, invoice), "InvoiceStatus not found with name: PENDING");
   }

    @Test
    public void testSave_ThrowsEntityNotFoundException_WhenAllMaterialsDontExist() {
        doReturn(Optional.of(new PurchaseOrder(1L))).when(mPoRepo).findById(1L);
        doReturn(Optional.of(new InvoiceStatus(2L, "FINAL"))).when(mStatusRepo).findByName("FINAL");

        doReturn(List.of(
           new Material(3L, "Material_5", "Description_5", null, "UPC_5", null, null, null, 5),
           new Material(4L, "Material_4", "Description_4", null, "UPC_4", null, null, null, 4))
       ).when(mMaterialRepo).findAllById(Set.of(3L, 4L));

       InvoiceItem item1 = new InvoiceItem(11L);
       InvoiceItem item2 = new InvoiceItem(12L);
       InvoiceItem item3 = new InvoiceItem(13L);
       item1.setMaterial(new Material(3L));
       item2.setMaterial(new Material(4L));
       item3.setMaterial(new Material(5L));
       List<InvoiceItem> items = List.of(item1, item2, item3);

       Invoice invoice = new Invoice();
       invoice.setItems(items);
       invoice.setStatus(new InvoiceStatus(null, "FINAL"));

       assertThrows(EntityNotFoundException.class, () -> repo.refresh(1L, invoice), "Cannot find all materials in Id-Set: [3, 4, 5]. Materials found with Ids: [5, 4]");
   }

    @Test
    public void testSave_SavesWithNullPurchaseOrder_WhenPurchaseOrderIdIsNull() {
        doReturn(Optional.of(new InvoiceStatus(2L, "FINAL"))).when(mStatusRepo).findByName("FINAL");

        Invoice invoice = new Invoice();
        invoice.setPurchaseOrder(new PurchaseOrder(1L));
        invoice.setStatus(new InvoiceStatus(null, "FINAL"));

        repo.refresh(null, invoice);

        assertNull(invoice.getPurchaseOrder());
    }
}
