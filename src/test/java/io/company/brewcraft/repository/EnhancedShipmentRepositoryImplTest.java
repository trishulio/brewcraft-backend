package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentAccessor;

public class EnhancedShipmentRepositoryImplTest {

    private EnhancedShipmentRepository repo;

    private AccessorRefresher<Long, ShipmentAccessor, Shipment> mRefresher;

    private ShipmentStatusRepository mStatusRepo;
    private MaterialLotRepository mLotRepo;

    @BeforeEach
    public void init() {
        this.mRefresher = mock(AccessorRefresher.class);
        this.mStatusRepo = mock(ShipmentStatusRepository.class);
        this.mLotRepo = mock(MaterialLotRepository.class);

        this.repo = new EnhancedShipmentRepositoryImpl(this.mRefresher, this.mStatusRepo, this.mLotRepo);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        final List<Shipment> shipments = List.of(
            new Shipment(1L),
            new Shipment(2L),
            new Shipment(3L)
        );
        final List<MaterialLot> lots = List.of(new MaterialLot(10L), new MaterialLot(20L), new MaterialLot(30L));

        shipments.get(0).setLots(List.of(lots.get(0)));
        shipments.get(1).setLots(List.of(lots.get(1)));
        shipments.get(2).setLots(List.of(lots.get(2)));

        this.repo.refresh(shipments);

        verify(this.mStatusRepo, times(1)).refreshAccessors(shipments);
        verify(this.mLotRepo, times(1)).refresh(lots);
    }

    @Test
    public void testRefresh_SkipsNullShipments() {
        final List<Shipment> shipments = new ArrayList<>();
        shipments.add(new Shipment(1L));
        shipments.add(null);
        shipments.add(new Shipment(2L));

        final List<MaterialLot> lots = List.of(new MaterialLot(10L), new MaterialLot(20L));

        shipments.get(0).setLots(List.of(lots.get(0)));
        shipments.get(2).setLots(List.of(lots.get(1)));

        this.repo.refresh(shipments);

        verify(this.mStatusRepo, times(1)).refreshAccessors(shipments);
        verify(this.mLotRepo, times(1)).refresh(lots);
    }

    @Test
    public void testRefresh_DoesNotRefreshShipments_WhenListIsNull() {
        this.repo.refresh(null);

        verify(this.mStatusRepo, times(1)).refreshAccessors(null);
        verify(this.mLotRepo, times(1)).refresh(null);
    }

    @Test
    public void testRefresh_DoesNotRefreshLots_WhenLotsAreNull() {
        final List<Shipment> shipments = List.of(
            new Shipment(1L),
            new Shipment(2L),
            new Shipment(3L)
        );

        this.repo.refresh(shipments);

        verify(this.mStatusRepo, times(1)).refreshAccessors(shipments);
        verify(this.mLotRepo, times(1)).refresh(List.of());
    }

    @Test
    public void testRefresh_DoesNotRefreshLots_WhenLotsSizeIs0() {
        final List<Shipment> shipments = List.of(
            new Shipment(1L),
            new Shipment(2L),
            new Shipment(3L)
        );
        shipments.get(0).setLots(List.of());
        shipments.get(1).setLots(List.of());
        shipments.get(2).setLots(List.of());

        this.repo.refresh(shipments);

        verify(this.mStatusRepo, times(1)).refreshAccessors(shipments);
        verify(this.mLotRepo, times(1)).refresh(List.of());
    }

    @Test
    public void testRefreshAccessors_DelegatesTheCallToAccessorRefresher() {
        final Collection<? extends ShipmentAccessor> accessors = List.of(mock(ShipmentAccessor.class), mock(ShipmentAccessor.class));

        this.repo.refreshAccessors(accessors);

        verify(this.mRefresher, times(1)).refreshAccessors(accessors);
    }
}
