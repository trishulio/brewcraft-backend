package io.company.brewcraft.service;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

public interface IaasObjectStoreFileSystem {
    List<URL> getTemporaryPublicFilePath(List<URI> filePaths, LocalDateTime expiration);
}
