package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceItemEntity;
import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.model.PurchaseOrderEntity;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedInvoiceRepositoryTest {

    private EnhancedInvoiceRepository repo;

    private InvoiceRepository mInvoiceRepo;
    private InvoiceStatusRepository mStatusRepo;
    private PurchaseOrderRepository mPoRepo;
    private MaterialRepository mMaterialRepo;

    @BeforeEach
    public void init() {
        mInvoiceRepo = mock(InvoiceRepository.class);
        mStatusRepo = mock(InvoiceStatusRepository.class);
        mPoRepo = mock(PurchaseOrderRepository.class);
        mMaterialRepo = mock(MaterialRepository.class);

        repo = new EnhancedInvoiceRepositoryImpl(mInvoiceRepo, mStatusRepo, mPoRepo, mMaterialRepo);
    }

    @Test
    public void testRefreshAndAdd_ReturnsInvoiceEntity_WithRefreshedChildEntities() {
        doAnswer(i -> i.getArgument(0, InvoiceEntity.class)).when(mInvoiceRepo).saveAndFlush(any(InvoiceEntity.class));

        doReturn(Optional.of(new PurchaseOrderEntity(1L))).when(mPoRepo).findById(1L);
        doReturn(Optional.of(new InvoiceStatusEntity(2L, "FINAL"))).when(mStatusRepo).findByName("FINAL");

        doReturn(List.of(new MaterialEntity(5L, "Material_5", "Description_5", null, "UPC_5", null, null, null, 5), new MaterialEntity(4L, "Material_4", "Description_4", null, "UPC_4", null, null, null, 4),
                new MaterialEntity(3L, "Material_3", "Description_3", null, "UPC_3", null, null, null, 3))).when(mMaterialRepo).findAllById(Set.of(3L, 4L, 5L));

        List<InvoiceItemEntity> items = List.of(new InvoiceItemEntity(11L), new InvoiceItemEntity(12L), new InvoiceItemEntity(13L));
        items.get(0).setMaterial(new MaterialEntity(3L));
        items.get(1).setMaterial(new MaterialEntity(4L));
        items.get(2).setMaterial(new MaterialEntity(5L));

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setItems(items);
        invoice.setStatus(new InvoiceStatusEntity(null, "FINAL"));

        InvoiceEntity ret = repo.refreshAndAdd(1L, invoice);

        verify(mInvoiceRepo, times(1)).saveAndFlush(invoice);

        assertEquals(new PurchaseOrderEntity(1L), ret.getPurchaseOrder());
        assertEquals(new InvoiceStatusEntity(2L, "FINAL"), ret.getStatus());
        assertEquals(new MaterialEntity(3L, "Material_3", "Description_3", null, "UPC_3", null, null, null, 3), ret.getItems().get(0).getMaterial());
        assertEquals(new MaterialEntity(4L, "Material_4", "Description_4", null, "UPC_4", null, null, null, 4), ret.getItems().get(1).getMaterial());
        assertEquals(new MaterialEntity(5L, "Material_5", "Description_5", null, "UPC_5", null, null, null, 5), ret.getItems().get(2).getMaterial());
    }

    @Test
    public void testRefreshAndAdd_ThrowsEntityNotFoundException_WhenPurchaseOrderDoesNotExist() {
        doReturn(Optional.empty()).when(mPoRepo).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> repo.refreshAndAdd(1L, new InvoiceEntity()), "PurchaseOrder not found with id: 1");
    }

    @Test
    public void testRefreshAndAdd_ThrowsEntityNotFoundException_WhenInvoiceStatusNameIsNonExistent() {
        doReturn(Optional.of(new PurchaseOrderEntity(1L))).when(mPoRepo).findById(1L);
        doReturn(Optional.empty()).when(mStatusRepo).findByName("PENDING");

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setStatus(new InvoiceStatusEntity(2L, "PENDING"));

        assertThrows(EntityNotFoundException.class, () -> repo.refreshAndAdd(1L, invoice), "InvoiceStatus not found with name: PENDING");
    }
}
