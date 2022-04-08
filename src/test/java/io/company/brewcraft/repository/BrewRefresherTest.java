package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.repository.user.impl.UserRefresher;
import io.company.brewcraft.service.AssignedToAccessor;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.OwnedByAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;
import io.company.brewcraft.service.ProductAccessor;

public class BrewRefresherTest {
    private BrewRefresher brewRefresher;

    private Refresher<Product, ProductAccessor> productRefresherMock;

    private UserRefresher userRefresherMock;

    private AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewAccessorMock;

    private AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        productRefresherMock = mock(Refresher.class);
        userRefresherMock = mock(UserRefresher.class);
        parentBrewAccessorMock = mock(AccessorRefresher.class);
        brewAccessorMock = mock(AccessorRefresher.class);

        brewRefresher = new BrewRefresher(productRefresherMock, userRefresherMock, parentBrewAccessorMock, brewAccessorMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        //Spy on brewRefresher as it makes a call to itself
        brewRefresher = spy(new BrewRefresher(productRefresherMock, userRefresherMock, parentBrewAccessorMock, brewAccessorMock));

        List<Brew> brews = List.of(new Brew(1L));

        brewRefresher.refresh(brews);

        verify(productRefresherMock, times(1)).refreshAccessors(brews);
        verify(userRefresherMock, times(1)).refreshOwnedByAccessors(brews);
        verify(userRefresherMock, times(1)).refreshOwnedByAccessors(brews);
        verify(brewRefresher, times(1)).refreshParentAccessors(brews);
    }

    @Test
    public void testRefreshParentBrewAccessors_CallsRefresherAccessor() {
        List<ParentBrewAccessor> accessors = List.of(mock(ParentBrewAccessor.class), mock(ParentBrewAccessor.class));

        brewRefresher.refreshParentAccessors(accessors);

        verify(parentBrewAccessorMock, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<BrewAccessor> accessors = List.of(mock(BrewAccessor.class), mock(BrewAccessor.class));

        brewRefresher.refreshAccessors(accessors);

        verify(brewAccessorMock, times(1)).refreshAccessors(accessors);
    }
}
