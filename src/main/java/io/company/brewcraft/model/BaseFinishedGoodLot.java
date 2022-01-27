package io.company.brewcraft.model;

import java.time.LocalDateTime;

public interface BaseFinishedGoodLot<T extends BaseFinishedGoodLotMixturePortion<? extends BaseFinishedGoodLot<T, U>>, U extends BaseFinishedGoodLotMaterialPortion<? extends BaseFinishedGoodLot<T, U>>> extends SkuAccessor, MixturePortionsAccessor<T>, MaterialPortionsAccessor<U>, FinishedGoodLotFinishedGoodLotPortionsAccessor, QuantityAccessor {
    final String ATTR_SKU = "sku";
    final String ATTR_MATERIAL_PORTIONS = "materialPortions";
    final String ATTR_MIXTURE_PORTIONS = "mixturePortions";
    final String ATTR_FINISHED_GOOD_LOT_PORTIONS = "finishedGoodLotPortions";
    final String ATTR_PACKAGED_ON = "packagedOn";

    LocalDateTime getPackagedOn();

    void setPackagedOn(LocalDateTime packagedOn);
}
