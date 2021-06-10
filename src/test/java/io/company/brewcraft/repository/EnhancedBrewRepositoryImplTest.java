package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.service.ParentBrewAccessor;

public class EnhancedBrewRepositoryImplTest {
    private EnhancedBrewRepository repo;

    private ProductRepository productRepositoryMock;
    
    private BrewRepository brewRepositoryMock;
    
    private AccessorRefresher<Long, ParentBrewAccessor, Brew> accessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
    	productRepositoryMock = mock(ProductRepository.class);
    	brewRepositoryMock = mock(BrewRepository.class);
        accessorMock = mock(AccessorRefresher.class);

        repo = new EnhancedBrewRepositoryImpl(productRepositoryMock, brewRepositoryMock, accessorMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Brew> brews = List.of(new Brew(1L));

        repo.refresh(brews);

        verify(productRepositoryMock, times(1)).refreshAccessors(brews);
        verify(brewRepositoryMock, times(1)).refreshAccessors(brews);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<ParentBrewAccessor> accessors = List.of(mock(ParentBrewAccessor.class), mock(ParentBrewAccessor.class));

        repo.refreshAccessors(accessors);

        verify(accessorMock, times(1)).refreshAccessors(accessors);
    }

}
