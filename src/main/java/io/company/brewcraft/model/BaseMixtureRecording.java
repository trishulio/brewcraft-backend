package io.company.brewcraft.model;

import java.time.LocalDateTime;

import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ProductMeasureAccessor;

public interface BaseMixtureRecording extends MixtureAccessor, ProductMeasureAccessor {

    Mixture getMixture();

    void setMixture(Mixture mixture);

    ProductMeasure getProductMeasure();

    void setProductMeasure(ProductMeasure productMeasure);

    String getValue();
    
    void setValue(String value);

	LocalDateTime getRecordedAt();

	void setRecordedAt(LocalDateTime recordedAt);

}
