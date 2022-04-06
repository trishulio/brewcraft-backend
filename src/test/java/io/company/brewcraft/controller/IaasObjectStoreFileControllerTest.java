package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.AddIaasObjectStoreFileDto;
import io.company.brewcraft.dto.IaasObjectStoreFileDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateIaasObjectStoreFileDto;
import io.company.brewcraft.model.BaseIaasObjectStoreFile;
import io.company.brewcraft.model.IaasObjectStoreFile;
import io.company.brewcraft.model.UpdateIaasObjectStoreFile;
import io.company.brewcraft.service.IaasObjectStoreFileService;

public class IaasObjectStoreFileControllerTest {

    private IaasObjectStoreFileController controller;

    private CrudControllerService<
                URI,
                IaasObjectStoreFile,
                BaseIaasObjectStoreFile,
                UpdateIaasObjectStoreFile,
                IaasObjectStoreFileDto,
                AddIaasObjectStoreFileDto,
                UpdateIaasObjectStoreFileDto
            > mCrudController;

    private IaasObjectStoreFileService mService;

    @BeforeEach
    public void init() {
        this.mCrudController = mock(CrudControllerService.class);
        this.mService = mock(IaasObjectStoreFileService.class);
        this.controller = new IaasObjectStoreFileController(mCrudController, mService);
    }

    @Test
    public void testGetAllIaasObjectStoreFile_ReturnsDtosFromController() {
        doReturn(new PageImpl<>(List.of(new IaasObjectStoreFile(URI.create("file_1.txt"))))).when(mService).getAll(
            Set.of(URI.create("file_1.txt"))
        );
        doReturn(new PageDto<>(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new IaasObjectStoreFile(URI.create("file_1.txt")))), Set.of(""));

        List<IaasObjectStoreFileDto> page = this.controller.getAll(
            Set.of(URI.create("file_1.txt"))
        );

        PageDto<IaasObjectStoreFileDto> expected = new PageDto<>(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), 1, 1);
        assertEquals(expected, page);
    }

    @Test
    public void testGetIaasObjectStoreFile_ReturnsDtoFromController() {
        doReturn(new IaasObjectStoreFileDto(URI.create("file_1.txt"))).when(mCrudController).get(URI.create("file_1.txt"), Set.of(""));

        IaasObjectStoreFileDto dto = this.controller.getIaasObjectStoreFile(URI.create("file_1.txt"), Set.of(""));

        IaasObjectStoreFileDto expected = new IaasObjectStoreFileDto(URI.create("file_1.txt"));
        assertEquals(expected, dto);
    }

    @Test
    public void testDeleteIaasObjectStoreFiles_ReturnsDeleteCountFromController() {
        doReturn(1L).when(mCrudController).delete(Set.of(URI.create("file_1.txt")));

        assertEquals(1L, this.controller.deleteIaasObjectStoreFiles(Set.of(URI.create("file_1.txt"))));
    }

    @Test
    public void testAddIaasObjectStoreFiles_AddsToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt")))).when(mCrudController).add(List.of(new AddIaasObjectStoreFileDto()));

        List<IaasObjectStoreFileDto> dtos = this.controller.addIaasObjectStoreFile(List.of(new AddIaasObjectStoreFileDto()));

        assertEquals(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), dtos);
    }

    @Test
    public void testUpdateIaasObjectStoreFiles_PutsToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt")))).when(mCrudController).put(List.of(new UpdateIaasObjectStoreFileDto(URI.create("file_1.txt"))));

        List<IaasObjectStoreFileDto> dtos = this.controller.updateIaasObjectStoreFile(List.of(new UpdateIaasObjectStoreFileDto(URI.create("file_1.txt"))));

        assertEquals(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), dtos);
    }

    @Test
    public void testPatchIaasObjectStoreFiles_PatchToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt")))).when(mCrudController).patch(List.of(new UpdateIaasObjectStoreFileDto(URI.create("file_1.txt"))));

        List<IaasObjectStoreFileDto> dtos = this.controller.patchIaasObjectStoreFile(List.of(new UpdateIaasObjectStoreFileDto(URI.create("file_1.txt"))));

        assertEquals(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), dtos);
    }
}
