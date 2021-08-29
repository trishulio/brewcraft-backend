package io.company.brewcraft.model;

public interface BaseDummyCrudEntity {
    String ATTR_EXCLUDED_VALUE = "excludedValue";
    String ATTR_VALUE = "value";

    String getExcludedValue();

    void setExcludedValue(String excludedValue);

    String getValue();

    void setValue(String value);
}
