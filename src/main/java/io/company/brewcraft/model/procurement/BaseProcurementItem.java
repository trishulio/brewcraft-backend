package io.company.brewcraft.model.procurement;

import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.MaterialLotAccessor;

public interface BaseProcurementItem extends MaterialLotAccessor, InvoiceItemAccessor {
}
