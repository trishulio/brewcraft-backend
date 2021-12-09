package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.service.UserRoleService;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class UserRoleControllerTest {

   private UserRoleController userRoleController;

   private UserRoleService userRoleService;

   @BeforeEach
   public void init() {
       userRoleService = mock(UserRoleService.class);

       userRoleController = new UserRoleController(userRoleService, new AttributeFilter());
   }

   @Test
   public void testGetRoles() {
       Page<UserRole> mPage = new PageImpl<>(List.of(new UserRole(1L, "ADMIN", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)));

       doReturn(mPage).when(userRoleService).getRoles(
           Set.of(1L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10
       );

       PageDto<UserRoleDto> dto = userRoleController.getRoles(
               Set.of(1L),
               new TreeSet<>(List.of("id")),
               true,
               1,
               10
       );

       assertEquals(1, dto.getTotalPages());
       assertEquals(List.of(new UserRoleDto(1L, "ADMIN", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), dto.getContent());
   }
}