package io.company.brewcraft.model.procurement;

public interface ProcurementAccessor {
    final String ATTR_PROCUREMENT = "procurement";

    Procurement getProcurement();

    void setProcurement(Procurement procurement);
}
