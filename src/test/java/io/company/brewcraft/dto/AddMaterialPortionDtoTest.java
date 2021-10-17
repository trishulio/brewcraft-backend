package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddMaterialPortionDtoTest {

    private AddMaterialPortionDto addMaterialPortionDto;

    @BeforeEach
    public void init() {
        addMaterialPortionDto = new AddMaterialPortionDto();
    }

    @Test
    public void testConstructor() {
        Long materialLotId = 2L;
        QuantityDto quantityDto = new QuantityDto("kg", new BigDecimal("100"));
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);

        AddMaterialPortionDto addMaterialPortionDto = new AddMaterialPortionDto(materialLotId, quantityDto, addedAt);

        assertEquals(2L, addMaterialPortionDto.getMaterialLotId());
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), addMaterialPortionDto.getQuantity());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addMaterialPortionDto.getAddedAt());
    }

    @Test
    public void testGetSetMaterialLotId() {
        addMaterialPortionDto.setMaterialLotId(3L);

        assertEquals(3L, addMaterialPortionDto.getMaterialLotId());
    }

    @Test
    public void testGetSetQuantity() {
        addMaterialPortionDto.setQuantity(new QuantityDto("kg", new BigDecimal("100")));
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), addMaterialPortionDto.getQuantity());
    }

    @Test
    public void testGetSetAddedAt() {
        addMaterialPortionDto.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addMaterialPortionDto.getAddedAt());
    }

}
