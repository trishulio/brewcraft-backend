package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.BaseDto;

public class AddProcurementDto extends BaseDto {

    private AddInvoiceDto invoice;

    public AddInvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(AddInvoiceDto invoice) {
        this.invoice = invoice;
    }
}
