package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;

public class EnhancedBrewRepositoryImplTest {
    private EnhancedBrewRepository repo;

    private ProductRepository productRepositoryMock;
    
    private BrewRepository brewRepositoryMock;
    
    private AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewAccessorMock;
    
    private AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
    	productRepositoryMock = mock(ProductRepository.class);
    	brewRepositoryMock = mock(BrewRepository.class);
        parentBrewAccessorMock = mock(AccessorRefresher.class);
        brewAccessorMock = mock(AccessorRefresher.class);

        repo = new EnhancedBrewRepositoryImpl(productRepositoryMock, brewRepositoryMock, parentBrewAccessorMock, brewAccessorMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Brew> brews = List.of(new Brew(1L));

        repo.refresh(brews);

        verify(productRepositoryMock, times(1)).refreshAccessors(brews);
        verify(brewRepositoryMock, times(1)).refreshParentBrewAccessors(brews);
    }

    @Test
    public void testRefreshParentBrewAccessors_CallsRefresherAccessor() {
        List<ParentBrewAccessor> accessors = List.of(mock(ParentBrewAccessor.class), mock(ParentBrewAccessor.class));

        repo.refreshParentBrewAccessors(accessors);

        verify(parentBrewAccessorMock, times(1)).refreshAccessors(accessors);
    }
    
    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<BrewAccessor> accessors = List.of(mock(BrewAccessor.class), mock(BrewAccessor.class));

        repo.refreshAccessors(accessors);

        verify(brewAccessorMock, times(1)).refreshAccessors(accessors);
    }

}
