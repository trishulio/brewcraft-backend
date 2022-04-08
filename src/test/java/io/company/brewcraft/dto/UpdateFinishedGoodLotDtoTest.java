package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateFinishedGoodLotDtoTest {
    private UpdateFinishedGoodLotDto finishedGoodLot;

    @BeforeEach
    public void init() {
        finishedGoodLot = new UpdateFinishedGoodLotDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Long skuId = 2L;
        List<UpdateMixturePortionDto> mixturePortions = List.of(new UpdateMixturePortionDto(1L, 5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1));
        List<UpdateMaterialPortionDto> materialPortions = List.of(new UpdateMaterialPortionDto(2L, 6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4), 1));
        List<UpdateFinishedGoodLotPortionDto> finishedGoodLotPortions = List.of(new UpdateFinishedGoodLotPortionDto(3L, 5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1));
        QuantityDto quantity = new QuantityDto("each", BigDecimal.valueOf(100.0));
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);
        Integer version = 1;

        UpdateFinishedGoodLotDto finishedGoodLot = new UpdateFinishedGoodLotDto(id, skuId, mixturePortions, materialPortions, finishedGoodLotPortions, quantity, packagedOn, version);

        assertEquals(1L, finishedGoodLot.getId());
        assertEquals(2L, finishedGoodLot.getSkuId());
        assertEquals(List.of(new UpdateMixturePortionDto(1L, 5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)), finishedGoodLot.getMixturePortions());
        assertEquals(List.of(new UpdateMaterialPortionDto(2L, 6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), finishedGoodLot.getMaterialPortions());
        assertEquals(List.of(new UpdateFinishedGoodLotPortionDto(3L, 5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)), finishedGoodLot.getFinishedGoodLotPortions());
        assertEquals(new QuantityDto("each", BigDecimal.valueOf(100.0)), finishedGoodLot.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodLot.getPackagedOn());
        assertEquals(1, finishedGoodLot.getVersion());
    }

    @Test
    public void testGetSetSkuId() {
        finishedGoodLot.setSkuId(3L);
        assertEquals(3L, finishedGoodLot.getSkuId());
    }

    @Test
    public void testGetSetMixturePortions() {
        finishedGoodLot.setMixturePortions(List.of(new UpdateMixturePortionDto(1L, 5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)));
        assertEquals(List.of(new UpdateMixturePortionDto(1L, 5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)), finishedGoodLot.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        finishedGoodLot.setMaterialPortions(List.of(new UpdateMaterialPortionDto(1L, 6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4), 1)));
        assertEquals(List.of(new UpdateMaterialPortionDto(1L, 6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), finishedGoodLot.getMaterialPortions());
    }

    @Test
    public void testGetSetFinishedGoodLotPortions() {
        finishedGoodLot.setFinishedGoodLotPortions(List.of(new UpdateFinishedGoodLotPortionDto(3L, 5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)));
        assertEquals(List.of(new UpdateFinishedGoodLotPortionDto(3L, 5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)), finishedGoodLot.getFinishedGoodLotPortions());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodLot.setQuantity(new QuantityDto("each", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("each", BigDecimal.valueOf(100.0)), finishedGoodLot.getQuantity());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGoodLot.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodLot.getPackagedOn());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        finishedGoodLot.setVersion(version);
        assertEquals(version, finishedGoodLot.getVersion());
    }
}
