package io.company.brewcraft.factory.impl;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import io.company.brewcraft.factory.KeyHolderFactory;

public class GeneratedKeyHolderFactory implements KeyHolderFactory {

    public KeyHolder newKeyHolder() {
        return new GeneratedKeyHolder();
    }
}
