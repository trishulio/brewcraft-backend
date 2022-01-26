package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddFinishedGoodLotDtoTest {

    private AddFinishedGoodLotDto finishedGoodLot;

    @BeforeEach
    public void init() {
        finishedGoodLot = new AddFinishedGoodLotDto();
    }

    @Test
    public void testConstructor() {
        Long skuId = 2L;
        List<AddMixturePortionDto> mixturePortions = List.of(new AddMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0))));
        List<AddMaterialPortionDto> materialPortions = List.of(new AddMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4)));
        List<AddFinishedGoodLotPortionDto> finishedGoodLotPortions = List.of(new AddFinishedGoodLotPortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0))));
        QuantityDto quantity = new QuantityDto("each", BigDecimal.valueOf(100.0));
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);

        AddFinishedGoodLotDto finishedGoodLot = new AddFinishedGoodLotDto(skuId, mixturePortions, materialPortions, finishedGoodLotPortions, quantity, packagedOn);

        assertEquals(2L, finishedGoodLot.getSkuId());
        assertEquals(List.of(new AddMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))), finishedGoodLot.getMixturePortions());
        assertEquals(List.of(new AddMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4))), finishedGoodLot.getMaterialPortions());
        assertEquals(List.of(new AddFinishedGoodLotPortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))), finishedGoodLot.getFinishedGoodLotPortions());
        assertEquals(new QuantityDto("each", BigDecimal.valueOf(100.0)), finishedGoodLot.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodLot.getPackagedOn());
    }

    @Test
    public void testGetSetSkuId() {
        finishedGoodLot.setSkuId(3L);
        assertEquals(3L, finishedGoodLot.getSkuId());
    }

    @Test
    public void testGetSetMixturePortions() {
        finishedGoodLot.setMixturePortions(List.of(new AddMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))));
        assertEquals(List.of(new AddMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))), finishedGoodLot.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        finishedGoodLot.setMaterialPortions(List.of(new AddMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4))));
        assertEquals(List.of(new AddMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4))), finishedGoodLot.getMaterialPortions());
    }

    @Test
    public void testGetSetFinishedGoodLotPortions() {
        finishedGoodLot.setFinishedGoodLotPortions(List.of(new AddFinishedGoodLotPortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))));
        assertEquals(List.of(new AddFinishedGoodLotPortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))), finishedGoodLot.getFinishedGoodLotPortions());
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
}
