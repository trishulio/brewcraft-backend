package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.service.UserRoleService;
import io.company.brewcraft.service.mapper.user.UserRoleMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/users/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRoleController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(UserRoleController.class);

    private UserRoleService userRoleService;

    private UserRoleMapper userRoleMapper = UserRoleMapper.INSTANCE;

    public UserRoleController(UserRoleService userRoleService, AttributeFilter filter) {
        super(filter);
        this.userRoleService = userRoleService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<UserRoleDto> getRoles(@RequestParam(required = false) Set<Long> ids,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

        Page<UserRole> userRolePage = userRoleService.getRoles(ids, sort, orderAscending, page, size);

        List<UserRoleDto> userRoles = userRolePage.stream()
                                                  .map(role -> userRoleMapper.toDto(role))
                                                  .toList();

        PageDto<UserRoleDto> dto = new PageDto<UserRoleDto>(userRoles, userRolePage.getTotalPages(), userRolePage.getTotalElements());

        return dto;
    }
}
