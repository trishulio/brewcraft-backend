package io.company.brewcraft.model;

import java.time.LocalDateTime;

public interface BaseFinishedGood<T extends BaseFinishedGoodMixturePortion<? extends BaseFinishedGood<T, U>>, U extends BaseFinishedGoodMaterialPortion<? extends BaseFinishedGood<T, U>>> extends SkuAccessor, MixturePortionsAccessor<T>, MaterialPortionsAccessor<U> {
    final String ATTR_SKU = "sku";
    final String ATTR_MATERIAL_PORTIONS = "materialPortions";
    final String ATTR_MIXTURE_PORTIONS = "mixturePortions";
    final String ATTR_PACKAGED_ON = "packagedOn";

    LocalDateTime getPackagedOn();

    void setPackagedOn(LocalDateTime packagedOn);
}
