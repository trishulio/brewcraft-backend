package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BaseSku;
import io.company.brewcraft.model.BaseSkuMaterial;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.UpdateSku;
import io.company.brewcraft.model.UpdateSkuMaterial;

public interface SkuService {
    public Page<Sku> getSkus(Set<Long> ids, Set<Long> productIds, Boolean isPackageable, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public Sku getSku(Long id);

    public List<Sku> addSkus(List<BaseSku<? extends BaseSkuMaterial<?>>> skus);

    public List<Sku> putSkus(List<UpdateSku<? extends UpdateSkuMaterial<?>>> skus);

    public List<Sku> patchSkus(List<UpdateSku<? extends UpdateSkuMaterial<?>>> skus);

    public void deleteSku(Long id);

    public boolean skuExists(Long id);

 }

