package io.company.brewcraft.factory;

import org.springframework.jdbc.support.KeyHolder;

public interface KeyHolderFactory {

    KeyHolder newKeyHolder();
}
