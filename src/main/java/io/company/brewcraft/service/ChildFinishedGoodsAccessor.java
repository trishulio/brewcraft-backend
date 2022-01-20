package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.FinishedGood;

public interface ChildFinishedGoodsAccessor {
    final String ATTR_CHILD_FINISHED_GOODS = "childFinishedGoods";

    List<FinishedGood> getChildFinishedGoods();

    void setChildFinishedGoods(List<FinishedGood> childFinishedGoods);
}