package io.company.brewcraft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateProductMeasureValue;
import io.company.brewcraft.model.ProductMeasureValue;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.ProductMeasureValueService;

@Transactional
public class ProductMeasureValueServiceImpl extends BaseService implements ProductMeasureValueService {

    @Override
    public List<ProductMeasureValue> merge(List<ProductMeasureValue> existingValues, List<ProductMeasureValue> newValues) {
        if (existingValues == null) {
            return newValues;
        }

        List<ProductMeasureValue> updatedProductMeasureValues = new ArrayList<>();

        Map<String, ProductMeasureValue> nameToMeasureLookup = new HashMap<String, ProductMeasureValue>();
        existingValues.stream().filter(value -> value != null && value.getMeasure() != null && value.getMeasure().getName() != null).forEach(measure -> nameToMeasureLookup.put(measure.getMeasure().getName(), measure));

        if (newValues != null) {
            newValues.forEach(measureValue -> {
                ProductMeasureValue exisingValue = nameToMeasureLookup.get(measureValue.getMeasure().getName());
                if (exisingValue != null) {
                    exisingValue.override(measureValue, getPropertyNames(UpdateProductMeasureValue.class));
                    updatedProductMeasureValues.add(exisingValue);
                } else {
                    updatedProductMeasureValues.add(measureValue);
                }
            });
        }

        return updatedProductMeasureValues;
    }

}
