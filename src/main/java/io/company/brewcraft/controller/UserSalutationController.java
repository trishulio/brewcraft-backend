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
import io.company.brewcraft.dto.user.UserSalutationDto;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.service.UserSalutationService;
import io.company.brewcraft.service.mapper.user.UserSalutationMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/users/salutations")
public class UserSalutationController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(UserSalutationController.class);

    private UserSalutationService userSalutationService;

    private UserSalutationMapper userSalutationMapper = UserSalutationMapper.INSTANCE;

    public UserSalutationController(UserSalutationService userService, AttributeFilter filter) {
        super(filter);
        this.userSalutationService = userService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<UserSalutationDto> getSalutations(@RequestParam(required = false) Set<Long> ids,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {
        Page<UserSalutation> userSalutationPage = userSalutationService.getSalutations(ids, sort, orderAscending, page, size);

        List<UserSalutationDto> userSalutations = userSalutationPage.stream()
                                                                    .map(salutation -> userSalutationMapper.toDto(salutation))
                                                                    .toList();

        PageDto<UserSalutationDto> dto = new PageDto<UserSalutationDto>(userSalutations, userSalutationPage.getTotalPages(),
                userSalutationPage.getTotalElements());

        return dto;
    }
}
