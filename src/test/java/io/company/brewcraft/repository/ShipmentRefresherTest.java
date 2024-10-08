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
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.MaterialLotRefresher;
import io.company.brewcraft.repository.ShipmentRefresher;
import io.company.brewcraft.repository.ShipmentStatusRefresher;

public class ShipmentRefresherTest {
    private ShipmentRefresher shipmentRefresher;

    private AccessorRefresher<Long, ShipmentAccessor, Shipment> mRefresher;

    private ShipmentStatusRefresher mStatusRefresher;
    private MaterialLotRefresher mLotRefresher;

    @BeforeEach
    public void init() {
        this.mRefresher = mock(AccessorRefresher.class);
        this.mStatusRefresher = mock(ShipmentStatusRefresher.class);
        this.mLotRefresher = mock(MaterialLotRefresher.class);

        this.shipmentRefresher = new ShipmentRefresher(this.mRefresher, this.mStatusRefresher, this.mLotRefresher);
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

        this.shipmentRefresher.refresh(shipments);

        verify(this.mStatusRefresher, times(1)).refreshAccessors(shipments);
        verify(this.mLotRefresher, times(1)).refresh(lots);
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

        this.shipmentRefresher.refresh(shipments);

        verify(this.mStatusRefresher, times(1)).refreshAccessors(shipments);
        verify(this.mLotRefresher, times(1)).refresh(lots);
    }

    @Test
    public void testRefresh_DoesNotRefreshShipments_WhenListIsNull() {
        this.shipmentRefresher.refresh(null);

        verify(this.mStatusRefresher, times(1)).refreshAccessors(null);
        verify(this.mLotRefresher, times(1)).refresh(null);
    }

    @Test
    public void testRefresh_DoesNotRefreshLots_WhenLotsAreNull() {
        final List<Shipment> shipments = List.of(
            new Shipment(1L),
            new Shipment(2L),
            new Shipment(3L)
        );

        this.shipmentRefresher.refresh(shipments);

        verify(this.mStatusRefresher, times(1)).refreshAccessors(shipments);
        verify(this.mLotRefresher, times(1)).refresh(List.of());
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

        this.shipmentRefresher.refresh(shipments);

        verify(this.mStatusRefresher, times(1)).refreshAccessors(shipments);
        verify(this.mLotRefresher, times(1)).refresh(List.of());
    }

    @Test
    public void testRefreshAccessors_DelegatesTheCallToAccessorRefresher() {
        final Collection<? extends ShipmentAccessor> accessors = List.of(mock(ShipmentAccessor.class), mock(ShipmentAccessor.class));

        this.shipmentRefresher.refreshAccessors(accessors);

        verify(this.mRefresher, times(1)).refreshAccessors(accessors);
    }
}
