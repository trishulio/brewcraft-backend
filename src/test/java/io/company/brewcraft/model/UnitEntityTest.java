package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class UnitEntityTest {

    private UnitEntity unit;

    @BeforeEach
    public void init() {
        unit = new UnitEntity();
    }

    @Test
    public void testAllArgsConstructor() {
        unit = new UnitEntity("KG", "Kilogram");
        assertEquals("KG", unit.getSymbol());
        assertEquals("Kilogram", unit.getName());
    }

    @Test
    public void testAccessSymbol() {
        assertNull(unit.getSymbol());
        unit.setSymbol("KG");
        assertEquals("KG", unit.getSymbol());
    }

    @Test
    public void testAccessName() {
        assertNull(unit.getName());
        unit.setName("Kilogram");
        assertEquals("Kilogram", unit.getName());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        unit = new UnitEntity("KG", "Kilogram");

        final String json = "{\"symbol\":\"KG\",\"name\":\"Kilogram\"}";
        JSONAssert.assertEquals(json, unit.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
