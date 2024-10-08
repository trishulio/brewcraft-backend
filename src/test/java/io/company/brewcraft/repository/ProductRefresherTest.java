package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Product;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.ProductRefresher;
import io.company.brewcraft.service.ProductAccessor;

public class ProductRefresherTest {
    private ProductRefresher productRefresher;

    private AccessorRefresher<Long, ProductAccessor, Product> refresherMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        refresherMock = mock(AccessorRefresher.class);

        productRefresher = new ProductRefresher(refresherMock);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<ProductAccessor> accessors = List.of(mock(ProductAccessor.class), mock(ProductAccessor.class));

        productRefresher.refreshAccessors(accessors);

        verify(refresherMock, times(1)).refreshAccessors(accessors);
    }
}
