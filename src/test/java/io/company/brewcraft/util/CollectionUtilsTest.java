package io.company.brewcraft.util;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class CollectionUtilsTest {

    @Test
    public void testAddToArray_ReturnsNewArrayWithSourceElements_WhenThereAreNoExtraElements() {
        Integer[] input = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] output = CollectionUtils.addToArray(input);

        assertArrayEquals(input, new Integer[] { 1, 2, 3, 4, 5 });
        assertArrayEquals(output, new Integer[] { 1, 2, 3, 4, 5 });
    }

    @Test
    public void testAddToArray_ReturnsNewArrayWithNewItemsAppendedToSourceArray_WhenExtraElementsAreProvided() {
        Integer[] input = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] output = CollectionUtils.addToArray(input, 10, 11, 12);

        assertArrayEquals(input, new Integer[] { 1, 2, 3, 4, 5 });
        assertArrayEquals(output, new Integer[] { 1, 2, 3, 4, 5, 10, 11, 12 });
    }
}
