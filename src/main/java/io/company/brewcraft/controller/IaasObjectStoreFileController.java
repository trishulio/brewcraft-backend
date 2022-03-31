package io.company.brewcraft.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddIaasObjectStoreFileDto;
import io.company.brewcraft.dto.IaasObjectStoreFileDto;
import io.company.brewcraft.dto.UpdateIaasObjectStoreFileDto;
import io.company.brewcraft.model.BaseIaasObjectStoreFile;
import io.company.brewcraft.model.IaasObjectStoreFile;
import io.company.brewcraft.model.UpdateIaasObjectStoreFile;
import io.company.brewcraft.service.IaasObjectStoreFileService;
import io.company.brewcraft.service.mapper.IaasObjectStoreFileMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/vfs/files")
public class IaasObjectStoreFileController extends BaseController {
    private static IaasObjectStoreFileMapper mapper = IaasObjectStoreFileMapper.INSTANCE;

    private CrudControllerService<
        URI,
        IaasObjectStoreFile,
        BaseIaasObjectStoreFile,
        UpdateIaasObjectStoreFile,
        IaasObjectStoreFileDto,
        AddIaasObjectStoreFileDto,
        UpdateIaasObjectStoreFileDto
    > controller;

    private final IaasObjectStoreFileService objectStoreFileService;

    protected IaasObjectStoreFileController(CrudControllerService<
            URI,
            IaasObjectStoreFile,
            BaseIaasObjectStoreFile,
            UpdateIaasObjectStoreFile,
            IaasObjectStoreFileDto,
            AddIaasObjectStoreFileDto,
            UpdateIaasObjectStoreFileDto
        > controller, IaasObjectStoreFileService objectStoreFileService)
    {
        this.controller = controller;
        this.objectStoreFileService = objectStoreFileService;
    }

    @Autowired
    public IaasObjectStoreFileController(IaasObjectStoreFileService objectStoreFileService, AttributeFilter filter) {
        this(new CrudControllerService<>(filter, IaasObjectStoreFileMapper.INSTANCE, objectStoreFileService, "IaasObjectStoreFile"), objectStoreFileService);
    }

    @GetMapping
    public List<IaasObjectStoreFileDto> getAll(
        @RequestParam(name = "files") Set<URI> files
    ) {
        final List<IaasObjectStoreFile> objectStoreFiles = this.objectStoreFileService.getAll(files);

        return objectStoreFiles.stream().map(IaasObjectStoreFileMapper.INSTANCE::toDto).toList();
    }

    @GetMapping("/{file}")
    public IaasObjectStoreFileDto getIaasObjectStoreFile(@PathVariable(required = true, name = "fileId") URI fileId, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        return this.controller.get(fileId, attributes);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public long deleteIaasObjectStoreFiles(@RequestParam("fileIds") Set<URI> fileIds) {
        return this.controller.delete(fileIds);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<IaasObjectStoreFileDto> addIaasObjectStoreFile(@Valid @NotNull @RequestBody List<AddIaasObjectStoreFileDto> addDtos) {
        return this.controller.add(addDtos);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<IaasObjectStoreFileDto> updateIaasObjectStoreFile(@Valid @NotNull @RequestBody List<UpdateIaasObjectStoreFileDto> updateDtos) {
        return this.controller.put(updateDtos);
    }

    @PatchMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<IaasObjectStoreFileDto> patchIaasObjectStoreFile(@Valid @NotNull @RequestBody List<UpdateIaasObjectStoreFileDto> updateDtos) {
        return this.controller.patch(updateDtos);
    }
}
