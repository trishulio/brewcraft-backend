package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddMaterialLotDto;
import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateMaterialLotDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MaterialLotMapperTest {

    private MaterialLotMapper mapper;

    @BeforeEach
    public void init() {
        this.mapper = MaterialLotMapper.INSTANCE;
    }

    @Test
    public void toDto_ReturnsDtoFromMaterialLot_WhenMaterialLotIsNotNull() {
        final MaterialLot lot = new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1);

        final MaterialLotDto dto = this.mapper.toDto(lot);

        final MaterialLotDto expected = new MaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("1")), new InvoiceItemDto(1L), new StorageDto(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1);

        assertEquals(expected, dto);
    }

    @Test
    public void toDto_ReturnsNull_WhenShipmentIsNull() {
        assertNull(this.mapper.toDto(null));
    }

    @Test
    public void fromDto_ReturnMaterialLot_WhenDtoIsNotNull() {
        final UpdateMaterialLotDto dto = new UpdateMaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("1")), 1L, 3L, 1);

        final MaterialLot lot = this.mapper.fromDto(dto);

        final MaterialLot expected = new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), null, null, 1);
        assertEquals(expected, lot);
    }

    @Test
    public void fromDto_ReturnsNull_WhenMaterialLotDtoIsNull() {
        assertNull(this.mapper.fromDto((UpdateMaterialLotDto) null));
        assertNull(this.mapper.fromDto((AddMaterialLotDto) null));
    }

}
