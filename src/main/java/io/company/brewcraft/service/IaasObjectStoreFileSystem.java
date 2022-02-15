package io.company.brewcraft.service;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

public interface IaasObjectStoreFileSystem {
    List<URL> getTemporaryPublicFileDownloadPath(List<URI> filePaths, LocalDateTime expiration);
    List<URL> getTemporaryPublicFileUploadPath(List<URI> filePaths, LocalDateTime expiration);
}
