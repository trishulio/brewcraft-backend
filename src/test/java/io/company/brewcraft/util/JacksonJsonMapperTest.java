package io.company.brewcraft.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonJsonMapperTest {

    public static class TestData {
        private int x, y;

        public TestData() {
        }

        public TestData(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    private JsonMapper mapper;

    @BeforeEach
    public void init() {
        mapper = new JacksonJsonMapper(new ObjectMapper());
    }

    @Test
    public void testWriteString_ReturnsJson_WhenObjectIsNotNull() {
        TestData data = new TestData(10, 20);
        String json = mapper.writeString(data);

        assertEquals("{\"x\":10,\"y\":20}", json);
    }

    @Test
    public void testWriteString_ReturnsNull_WhenObjectIsNull() {
        String json = mapper.writeString(null);

        assertEquals("null", json);
    }

    @Test
    public void testRead_Returns() {
        String json = "{\"x\":10,\"y\":20}";

        TestData data = mapper.readString(json, TestData.class);

        assertEquals(10, data.getX());
        assertEquals(20, data.getY());
    }
}
