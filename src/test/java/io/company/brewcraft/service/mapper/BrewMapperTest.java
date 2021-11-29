package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddBrewDto;
import io.company.brewcraft.dto.BrewDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.UpdateBrewDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.Product;

public class BrewMapperTest {

    private BrewMapper brewMapper;

    @BeforeEach
    public void init() {
        brewMapper = BrewMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsEntity() {
        BrewDto dto = new BrewDto(1L, "testName", "testDesc", "2", new ProductDto(), 3L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

        Brew brew = brewMapper.fromDto(dto);

        Brew parentBrew = new Brew(3L);
        parentBrew.addChildBrew(brew);
        Brew expectedBrew = new Brew(1L, "testName", "testDesc", "2", new Product(), parentBrew, null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), null, 1);

        assertEquals(expectedBrew, brew);
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddBrewDto dto = new AddBrewDto("testName", "testDesc", "2", 3L, 4L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4));

        Brew brew = brewMapper.fromDto(dto);

        Brew parentBrew = new Brew(3L);
        parentBrew.addChildBrew(brew);
        Brew expectedBrew = new Brew(null, "testName", "testDesc", "2", new Product(3L), parentBrew, null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), null, null, null);

        assertEquals(expectedBrew, brew);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateBrewDto dto = new UpdateBrewDto("testName", "testDesc", "2", 3L, 4L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        Brew brew = brewMapper.fromDto(dto);

        Brew parentBrew = new Brew(3L);
        parentBrew.addChildBrew(brew);
        Brew expectedBrew = new Brew(null, "testName", "testDesc", "2", new Product(3L), parentBrew, null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), null, null, 1);

        assertEquals(expectedBrew, brew);
    }

    @Test
    public void testToDto_ReturnsDto() {
        Brew brew = new Brew(1L, "testName", "testDesc", "2", new Product(), new Brew(2L), null, null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

        BrewDto dto = brewMapper.toDto(brew);

        assertEquals(new BrewDto(1L, "testName", "testDesc", "2", new ProductDto(), 2L, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1), dto);
    }

}
