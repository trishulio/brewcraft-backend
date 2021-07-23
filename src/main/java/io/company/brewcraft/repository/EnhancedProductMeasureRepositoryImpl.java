//package io.company.brewcraft.repository;
//
//import java.util.Collection;
//
//import io.company.brewcraft.model.ProductMeasure;
//import io.company.brewcraft.service.ProductMeasureAccessor;
//
//public class EnhancedProductMeasureRepositoryImpl implements EnhancedProductMeasureRepository {
//	
//    private AccessorRefresher<Long, ProductMeasureAccessor, ProductMeasure> refresher;
//    
//    public EnhancedProductMeasureRepositoryImpl(AccessorRefresher<Long, ProductMeasureAccessor, ProductMeasure> refresher) {
//        this.refresher = refresher;
//    }
//	
//	@Override
//	public void refreshAccessors(Collection<? extends ProductMeasureAccessor> accessors) {
//        this.refresher.refreshAccessors(accessors);
//	}
//
//}
