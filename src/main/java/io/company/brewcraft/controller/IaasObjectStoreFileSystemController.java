package io.company.brewcraft.controller;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.service.IaasObjectStoreFileSystem;

@RestController
@RequestMapping("/api/v1/iaas/vfs")
public class IaasObjectStoreFileSystemController {
    private IaasObjectStoreFileSystem fileSystem;

    @Autowired
    public IaasObjectStoreFileSystemController(IaasObjectStoreFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @GetMapping("/uploads/urls")
    public List<URL> get(
        @RequestParam(name = "files") List<URI> files,
        @RequestParam(name = "expiration") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expiration
    ) {
        return this.fileSystem.getTemporaryPublicFileUploadPath(files, expiration);
    }
}
