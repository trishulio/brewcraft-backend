package io.company.brewcraft.model;

import java.time.LocalDateTime;

import io.company.brewcraft.service.ChildFinishedGoodsAccessor;

public interface BaseFinishedGood<T extends BaseFinishedGoodMixturePortion<? extends BaseFinishedGood<T, U>>, U extends BaseFinishedGoodMaterialPortion<? extends BaseFinishedGood<T, U>>> extends SkuAccessor, MixturePortionsAccessor<T>, MaterialPortionsAccessor<U>, ChildFinishedGoodsAccessor {
    final String ATTR_SKU = "sku";
    final String ATTR_MATERIAL_PORTIONS = "materialPortions";
    final String ATTR_MIXTURE_PORTIONS = "mixturePortions";
    final String ATTR_PARENT_FINISHED_GOOD = "parentFinishedGood";
    final String ATTR_PACKAGED_ON = "packagedOn";

    FinishedGood getParentFinishedGood();

    void setParentFinishedGood(FinishedGood finishedGood);

    LocalDateTime getPackagedOn();

    void setPackagedOn(LocalDateTime packagedOn);
}
