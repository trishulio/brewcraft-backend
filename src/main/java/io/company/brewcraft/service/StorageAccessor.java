package io.company.brewcraft.service;

import io.company.brewcraft.model.Storage;

public interface StorageAccessor {
    public void setStorage(Storage storage);

    public Storage getStorage();
}
