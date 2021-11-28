package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.BrewRefresher;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;
import io.company.brewcraft.service.ProductAccessor;

public class BrewRefresherTest {

    private IBrewRefresher<Brew, BrewAccessor, ParentBrewAccessor> brewRefresher;

    private Refresher<Product, ProductAccessor> productRefresherMock;

    private AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewAccessorMock;

    private AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        productRefresherMock = mock(Refresher.class);
        parentBrewAccessorMock = mock(AccessorRefresher.class);
        brewAccessorMock = mock(AccessorRefresher.class);

        brewRefresher = spy(new BrewRefresher(productRefresherMock, parentBrewAccessorMock, brewAccessorMock));
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Brew> brews = List.of(new Brew(1L));

        brewRefresher.refresh(brews);

        verify(productRefresherMock, times(1)).refreshAccessors(brews);
        verify(brewRefresher, times(1)).refreshParentBrewAccessors(brews);
    }

    @Test
    public void testRefreshParentBrewAccessors_CallsRefresherAccessor() {
        List<ParentBrewAccessor> accessors = List.of(mock(ParentBrewAccessor.class), mock(ParentBrewAccessor.class));

        brewRefresher.refreshParentBrewAccessors(accessors);

        verify(parentBrewAccessorMock, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<BrewAccessor> accessors = List.of(mock(BrewAccessor.class), mock(BrewAccessor.class));

        brewRefresher.refreshAccessors(accessors);

        verify(brewAccessorMock, times(1)).refreshAccessors(accessors);
    }

}
