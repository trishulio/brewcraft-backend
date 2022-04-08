package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.model.procurement.ProcurementItemId;
import io.company.brewcraft.service.mapper.procurement.ProcurementItemIdMapper;

public class ProcurementItemIdMapperTest {
    private ProcurementItemIdMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ProcurementItemIdMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        ProcurementItemIdDto dto = mapper.toDto(new ProcurementItemId(1L, 10L));

        ProcurementItemIdDto expected = new ProcurementItemIdDto(1L, 10L);
        assertEquals(expected, dto);
    }

    @Test
    public void testFromDto_ReturnsEntity_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testFromDto_ReturnsEntity_WhenDtoIsNotNull() {
        assertEquals(new ProcurementItemId(1L, 10L), mapper.fromDto(new ProcurementItemIdDto(1L, 10L)));
    }
}
