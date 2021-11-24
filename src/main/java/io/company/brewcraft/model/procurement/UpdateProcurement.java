package io.company.brewcraft.model.procurement;

import io.company.brewcraft.model.Identified;

public interface UpdateProcurement<UPI extends UpdateProcurementItem> extends BaseProcurement<UPI>, Identified<ProcurementId> {
}
