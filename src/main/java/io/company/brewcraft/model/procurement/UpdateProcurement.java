package io.company.brewcraft.model.procurement;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateProcurement<II extends BaseInvoiceItem<? extends BaseInvoice<II>>, ML extends BaseMaterialLot<? extends BaseShipment<ML>>, PI extends ProcurementItem> extends BaseProcurement<II, ML, PI>, UpdatableEntity<ProcurementId> {
}
