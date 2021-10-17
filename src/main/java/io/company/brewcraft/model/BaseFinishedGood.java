package io.company.brewcraft.model;

import java.util.List;

public interface BaseFinishedGood<T extends BaseFinishedGoodMixturePortion<? extends BaseFinishedGood<T, U>>, U extends BaseFinishedGoodMaterialPortion<? extends BaseFinishedGood<T, U>>> extends SkuAccessor {
    final String ATTR_SKU = "sku";
    final String ATTR_MATERIAL_PORTIONS = "materialPortions";
    final String ATTR_MIXTURE_PORTIONS = "mixturePortions";

    Sku getSku();

    void setSku(Sku sku);
    
    List<T> getMixturePortions();

    void setMixturePortions(List<T> mixturePortions);

    List<U> getMaterialPortions();

    void setMaterialPortions(List<U> materialPortions);

}
