package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Product;
import io.company.brewcraft.service.ProductAccessor;

public class EnhancedProductRepositoryImplTest {
	private EnhancedProductRepository productRepository;

	private AccessorRefresher<Long, ProductAccessor, Product> refresherMock;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void init() {
		refresherMock = mock(AccessorRefresher.class);

		productRepository = new EnhancedProductRepositoryImpl(refresherMock);
	}

	@Test
	public void testRefreshAccessors_CallsRefresherAccessor() {
		List<ProductAccessor> accessors = List.of(mock(ProductAccessor.class), mock(ProductAccessor.class));

		productRepository.refreshAccessors(accessors);

		verify(refresherMock, times(1)).refreshAccessors(accessors);
	}
}
