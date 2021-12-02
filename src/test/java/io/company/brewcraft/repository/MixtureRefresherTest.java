package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentMixturesAccessor;

public class MixtureRefresherTest {
    private MixtureRefresher mixtureRefresher;

    private Refresher<Equipment, EquipmentAccessor> equipmentRefresher;

    private Refresher<BrewStage, BrewStageAccessor> brewStageRefresherMock;

    private AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureAccessorMock;

    private CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixturesAccessorMock;


    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        equipmentRefresher = mock(Refresher.class);
        brewStageRefresherMock = mock(Refresher.class);
        mixtureAccessorMock = mock(AccessorRefresher.class);
        parentMixturesAccessorMock = mock(CollectionAccessorRefresher.class);

        mixtureRefresher = spy(new MixtureRefresher(equipmentRefresher, brewStageRefresherMock, mixtureAccessorMock, parentMixturesAccessorMock));
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Mixture> mixtures = List.of(new Mixture(1L));

        List<Mixture> parentMixtures = List.of(new Mixture(2L));
        mixtures.get(0).setParentMixtures(parentMixtures);

        mixtureRefresher.refresh(mixtures);

        verify(equipmentRefresher, times(1)).refreshAccessors(mixtures);
        verify(brewStageRefresherMock, times(1)).refreshAccessors(mixtures);
        verify(mixtureRefresher, times(1)).refreshParentAccessors(mixtures);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<MixtureAccessor> accessors = List.of(mock(MixtureAccessor.class), mock(MixtureAccessor.class));

        mixtureRefresher.refreshAccessors(accessors);

        verify(mixtureAccessorMock, times(1)).refreshAccessors(accessors);
    }

}
