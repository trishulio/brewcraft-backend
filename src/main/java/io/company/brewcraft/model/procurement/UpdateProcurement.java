package io.company.brewcraft.model.procurement;

import io.company.brewcraft.service.CrudEntity;

public interface UpdateProcurement<UPI extends UpdateProcurementItem> extends BaseProcurement<UPI>, CrudEntity<ProcurementId> {
}
