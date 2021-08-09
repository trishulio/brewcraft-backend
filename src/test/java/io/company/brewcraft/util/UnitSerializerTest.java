package io.company.brewcraft.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.measure.Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonSerializer;

import tec.uom.se.unit.Units;

public class UnitSerializerTest {
    private JsonSerializer<Unit> serializer;
    private MockJsonGenerator mGen;

    @BeforeEach
    public void init() {
        serializer = new UnitSerializer();
        mGen = new MockJsonGenerator();
    }

    @Test
    public void testSerialize_ReturnNull_WhenValueIsNull() throws IOException {
        serializer.serialize(null, mGen, null);

        assertEquals("null", mGen.json());
    }

    @Test
    public void testSerialize_ReturnsJsonUnit_WhenValueIsNotNull() throws IOException {
        serializer.serialize(Units.KILOGRAM, mGen, null);

        assertEquals("{\"symbol\":\"kg\"}", mGen.json());
    }
}
