package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.service.FinishedGoodLotAccessor;
import io.company.brewcraft.service.FinishedGoodLotFinishedGoodLotPortionAccessor;

public class FinishedGoodLotFinishedGoodLotPortionRefresherTest {
    private FinishedGoodLotFinishedGoodLotPortionRefresher finishedGoodLotFinishedGoodLotPortionRefresher;

    private Refresher<FinishedGoodLot, FinishedGoodLotAccessor> finishedGoodLotRefresher;

    private AccessorRefresher<Long, FinishedGoodLotFinishedGoodLotPortionAccessor, FinishedGoodLotFinishedGoodLotPortion> refresher;

    @BeforeEach
    public void init() {
        this.finishedGoodLotRefresher = mock(Refresher.class);
        this.refresher = mock(AccessorRefresher.class);

        this.finishedGoodLotFinishedGoodLotPortionRefresher = new FinishedGoodLotFinishedGoodLotPortionRefresher(finishedGoodLotRefresher, refresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        final List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotFinishedGoodLotPortions = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L),
            new FinishedGoodLotFinishedGoodLotPortion(2L)
        );

        finishedGoodLotFinishedGoodLotPortions.get(0).setFinishedGoodLot(new FinishedGoodLot(10L));

        finishedGoodLotFinishedGoodLotPortions.get(1).setFinishedGoodLot(new FinishedGoodLot(20L));

        this.finishedGoodLotFinishedGoodLotPortionRefresher.refresh(finishedGoodLotFinishedGoodLotPortions);

        verify(this.finishedGoodLotRefresher, times(1)).refreshAccessors(finishedGoodLotFinishedGoodLotPortions);
    }

    @Test
    public void testRefreshAccessors_DelegatesTheCallToAccessorRefresher() {
        final Collection<? extends FinishedGoodLotFinishedGoodLotPortionAccessor> accessors = List.of(mock(FinishedGoodLotFinishedGoodLotPortionAccessor.class), mock(FinishedGoodLotFinishedGoodLotPortionAccessor.class));

        this.finishedGoodLotFinishedGoodLotPortionRefresher.refreshAccessors(accessors);

        verify(this.refresher, times(1)).refreshAccessors(accessors);
    }
}

