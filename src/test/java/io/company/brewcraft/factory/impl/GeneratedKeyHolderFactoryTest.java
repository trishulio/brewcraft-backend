package io.company.brewcraft.factory.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import io.company.brewcraft.factory.KeyHolderFactory;

public class GeneratedKeyHolderFactoryTest {

    private KeyHolderFactory keyHolderFactory;

    @BeforeEach
    public void init() {
        keyHolderFactory = new GeneratedKeyHolderFactory();
    }

    @Test
    public void testNewKeyHolder_returnsInstanceOfGeneratedKeyHolder() {
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        assertTrue(keyHolder instanceof GeneratedKeyHolder);
    }
}
