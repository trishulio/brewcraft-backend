package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.service.mapper.procurement.ProcurementIdMapper;

public class ProcurementIdMapperTest {
    private ProcurementIdMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ProcurementIdMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        ProcurementId id = mapper.fromDto(new ProcurementIdDto(1L, 2L));

        ProcurementId expected = new ProcurementId(1L, 2L);
        assertEquals(expected, id);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        ProcurementIdDto id = mapper.toDto(new ProcurementId(1L, 2L));

        ProcurementIdDto expected = new ProcurementIdDto(1L, 2L);
        assertEquals(expected, id);
    }
}
