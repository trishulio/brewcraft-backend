package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.FinishedGoodLot;

public interface ChildFinishedGoodsAccessor {
    final String ATTR_CHILD_FINISHED_GOODS = "childFinishedGoods";

    List<FinishedGoodLot> getChildFinishedGoods();

    void setChildFinishedGoods(List<FinishedGoodLot> childFinishedGoods);
}