package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.BrewTaskServiceImpl;

public class BrewTaskServiceImplTest {
    private BrewTaskService brewTaskService;

    private BrewTaskRepository brewTaskRepositoryMock;

    @BeforeEach
    public void init() {
        brewTaskRepositoryMock = mock(BrewTaskRepository.class);
        brewTaskService = new BrewTaskServiceImpl(brewTaskRepositoryMock);
    }

    @Test
    public void testGetBrewTasks_ReturnsListOfBrewTasks() {
        Page<BrewTask> expectedBrewTaskPage = new PageImpl<>(
                List.of(new BrewTask(1L, "MASH"), new BrewTask(2L, "BOIL")));

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(brewTaskRepositoryMock.findAll(ArgumentMatchers.<Specification<BrewTask>>any(), pageableArgument.capture())).thenReturn(expectedBrewTaskPage);

        Page<BrewTask> actualBrewTaskPage = brewTaskService.getTasks(null, null, 0, 100, new TreeSet<>(List.of("id")), true);

        assertEquals(List.of(new BrewTask(1L, "MASH"), new BrewTask(2L, "BOIL")), actualBrewTaskPage.getContent());
    }

    @Test
    public void testGetBrewTask_ReturnsPojo_WhenEntityExists() {
        doReturn(Optional.ofNullable(new BrewTask(1L, "MASH"))).when(brewTaskRepositoryMock).findById(1L);

        BrewTask brewTask = brewTaskService.getTask(1L);
        assertEquals(new BrewTask(1L, "MASH"), brewTask);
    }

    @Test
    public void testGetBrewTask_ReturnsNull_WhenEntityDoesNotExists() {
        doReturn(Optional.ofNullable(null)).when(brewTaskRepositoryMock).findById(1L);

        BrewTask brewTask = brewTaskService.getTask(1L);
        assertNull(brewTask);
    }
}
