package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.pojo.Shipment;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedShipmentRepositoryImplTest {

    private EnhancedShipmentRepository enhancedRepo;

    private ShipmentRepository mShipmentRepo;
    private InvoiceRepository mInvoiceRepo;

    @BeforeEach
    public void init() {
        mShipmentRepo = mock(ShipmentRepository.class);
        mInvoiceRepo = mock(InvoiceRepository.class);

        enhancedRepo = new EnhancedShipmentRepositoryImpl(mShipmentRepo, mInvoiceRepo);
    }

    @Test
    public void testSave_SetsHibernateReferencesAndSaves_WhenShipmentIsNotNull() {
        doReturn(Optional.of(new InvoiceEntity(1L))).when(mInvoiceRepo).findById(1L);

        Shipment shipment = new Shipment(1L);
        assertNull(shipment.getInvoice());

        Shipment ret = enhancedRepo.save(1L, shipment);

        Shipment expected = new Shipment(1L);
        expected.setInvoice(new InvoiceEntity(1L));

        assertEquals(expected, ret);
        verify(mShipmentRepo, times(1)).save(ret);
    }

    @Test
    public void testSave_ThrowsEntityNotFoundException_WhenInvoiceIsNotFound() {
        doReturn(Optional.empty()).when(mInvoiceRepo).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> enhancedRepo.save(1L, new Shipment(1L)));
    }
}
