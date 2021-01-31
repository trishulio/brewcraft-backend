package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.pojo.Material;

public class MaterialMapperTest {

    private MaterialMapper mapper;

    @BeforeEach
    public void init() {
        mapper = MaterialMapper.INSTANCE;
    }

    @Test
    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
        MaterialEntity entity = new MaterialEntity(1L);
        Material material = mapper.fromEntity(entity);

        assertEquals(new Material(1L), material);
    }

    @Test
    public void testFromDto_ReturnPojo_WhenDtoIsNotNull() {
        MaterialDto dto = new MaterialDto(1L);
        Material material = mapper.fromDto(dto);

        assertEquals(new Material(1L), material);
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        Material material = new Material(1L);
        MaterialDto dto = mapper.toDto(material);

        assertEquals(new MaterialDto(1L), dto);
    }

    @Test
    public void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
        Material material = new Material(1L);
        MaterialEntity entity = mapper.toEntity(material);

        assertEquals(new MaterialEntity(1L), entity);
    }
}
