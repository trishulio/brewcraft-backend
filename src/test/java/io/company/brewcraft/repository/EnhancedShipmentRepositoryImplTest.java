package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;

public class EnhancedShipmentRepositoryImplTest {

    private EnhancedShipmentRepository repo;

    private ShipmentStatusRepository mStatusRepo;
    private MaterialLotRepository mLotRepo;

    @BeforeEach
    public void init() {
        mStatusRepo = mock(ShipmentStatusRepository.class);
        mLotRepo = mock(MaterialLotRepository.class);

        repo = new EnhancedShipmentRepositoryImpl(mStatusRepo, mLotRepo);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Shipment> shipments = List.of(
            new Shipment(1L),
            new Shipment(2L),
            new Shipment(3L)
        );
        List<MaterialLot> lots = List.of(new MaterialLot(10L), new MaterialLot(20L), new MaterialLot(30L));

        shipments.get(0).setLots(List.of(lots.get(0)));
        shipments.get(1).setLots(List.of(lots.get(1)));
        shipments.get(2).setLots(List.of(lots.get(2)));
        
        repo.refresh(shipments);

        verify(mStatusRepo, times(1)).refreshAccessors(shipments);
        verify(mLotRepo, times(1)).refresh(lots);
    }
}
