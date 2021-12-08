package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.repository.user.UserRoleRepository;
import io.company.brewcraft.service.UserRoleService;

public class UserRoleServiceTest {

    private UserRoleService userRoleService;

    private UserRoleRepository userRoleRepository;

    @BeforeEach
    public void init() {
        userRoleRepository = Mockito.mock(UserRoleRepository.class);
        userRoleService = new UserRoleService(userRoleRepository);
    }

    @Test
    public void testGetRoles_returnsRoles() throws Exception {
        Page<UserRole> expectedRolesPage = new PageImpl<>(List.of(new UserRole(1L, "ADMIN", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)));

        final ArgumentCaptor<Specification<UserRole>> specificationCaptor = ArgumentCaptor.forClass(Specification.class);

        when(userRoleRepository.findAll(specificationCaptor.capture(), eq(PageRequest.of(0, 100, Sort.by(Direction.ASC, new String[] {"id"}))))).thenReturn(expectedRolesPage);

        Page<UserRole> actualRolesPage = userRoleService.getRoles(null, new TreeSet<>(List.of("id")), true, 0, 100);

        assertEquals(List.of(new UserRole(1L, "ADMIN", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), actualRolesPage.getContent());

        // TODO: Pending testing for the specification
        // specificationCaptor.getValue();
    }

    @Test
    public void testUserRoleService_classIsTransactional() throws Exception {
        Transactional transactional = userRoleService.getClass().getAnnotation(Transactional.class);

        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }

    @Test
    public void testUserRoleService_methodsAreNotTransactional() throws Exception {
        Method[] methods = userRoleService.getClass().getMethods();
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
