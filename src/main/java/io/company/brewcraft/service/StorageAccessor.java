package io.company.brewcraft.service;

import io.company.brewcraft.model.Storage;

public interface StorageAccessor {
    final String ATTR_STORAGE = "storage";

    public void setStorage(Storage storage);

    public Storage getStorage();
}
