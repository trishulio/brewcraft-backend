package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialPortionDtoTest {

    private MaterialPortionDto materialPortionBaseDto;

    @BeforeEach
    public void init() {
        materialPortionBaseDto = new MaterialPortionDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        MaterialLotDto materialLotDto = new MaterialLotDto(3L);
        QuantityDto quantityDto = new QuantityDto("kg", new BigDecimal("100"));
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        MaterialPortionDto materialPortionBaseDto = new MaterialPortionDto(id, materialLotDto, quantityDto, addedAt, version);

        assertEquals(1L, materialPortionBaseDto.getId());
        assertEquals(new MaterialLotDto(3L), materialPortionBaseDto.getMaterialLot());
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), materialPortionBaseDto.getQuantity());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortionBaseDto.getAddedAt());
        assertEquals(1, materialPortionBaseDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        materialPortionBaseDto.setId(1L);
        assertEquals(1L, materialPortionBaseDto.getId());
    }

    @Test
    public void testGetSetMaterialLot() {
        materialPortionBaseDto.setMaterialLot(new MaterialLotDto(3L));

        assertEquals(new MaterialLotDto(3L), materialPortionBaseDto.getMaterialLot());
    }

    @Test
    public void testGetSetQuantity() {
        materialPortionBaseDto.setQuantity(new QuantityDto("kg", new BigDecimal("100")));
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), materialPortionBaseDto.getQuantity());
    }

    @Test
    public void testGetSetAddedAt() {
        materialPortionBaseDto.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortionBaseDto.getAddedAt());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        materialPortionBaseDto.setVersion(version);
        assertEquals(version, materialPortionBaseDto.getVersion());
    }
}
