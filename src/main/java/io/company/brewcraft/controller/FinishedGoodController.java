package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.company.brewcraft.dto.AddFinishedGoodDto;
import io.company.brewcraft.dto.FinishedGoodDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateFinishedGood;
import io.company.brewcraft.dto.UpdateFinishedGoodDto;
import io.company.brewcraft.model.BaseFinishedGood;
import io.company.brewcraft.model.BaseFinishedGoodMaterialPortion;
import io.company.brewcraft.model.BaseFinishedGoodMixturePortion;
import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.model.UpdateFinishedGoodMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodMixturePortion;
import io.company.brewcraft.service.FinishedGoodService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.FinishedGoodMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/finished-goods", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FinishedGoodController extends BaseController {
    private static FinishedGoodMapper mapper = FinishedGoodMapper.INSTANCE;

    private final FinishedGoodService finishedGoodService;

    @Autowired
    public FinishedGoodController(FinishedGoodService finishedGoodService, AttributeFilter filter) {
        super(filter);
        this.finishedGoodService = finishedGoodService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<FinishedGoodDto> getFinishedGoods(
        @RequestParam(required = false, name = "ids") Set<Long> ids,
        @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
        @RequestParam(required = false, name = "sku_ids") Set<Long> skuIds,
        @RequestParam(required = false, name = "mixture_ids") Set<Long> mixtureIds,
        @RequestParam(required = false, name = "brew_stage_ids") Set<Long> brewStageIds,
        @RequestParam(required = false, name = "brew_ids") Set<Long> brewIds,
        @RequestParam(required = false, name = "brew_batch_ids") Set<String> brewBatchIds,
        @RequestParam(required = false, name = "product_ids") Set<Long> productIds,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        final Page<FinishedGood> finishedGoods = this.finishedGoodService.getFinishedGoods(
            ids,
            excludeIds,
            skuIds,
            mixtureIds,
            brewStageIds,
            brewIds,
            brewBatchIds,
            productIds,
            sort,
            orderAscending,
            page,
            size
        );

        return this.response(finishedGoods, attributes);
    }

    @GetMapping(value = "/{finishedGoodId}", consumes = MediaType.ALL_VALUE)
    public FinishedGoodDto getFinishedGood(@PathVariable(required = true, name = "finishedGoodId") Long finishedGoodId, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        final FinishedGood finishedGood = this.finishedGoodService.get(finishedGoodId);
        Validator.assertion(finishedGood != null, EntityNotFoundException.class, "FinishedGood", finishedGoodId.toString());

        final FinishedGoodDto dto = mapper.toDto(finishedGood);
        this.filter(dto, attributes);

        return dto;
    }

    @DeleteMapping("")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public int deleteFinishedGoods(@RequestParam("ids") Set<Long> finishedGoodIds) {
        return this.finishedGoodService.delete(finishedGoodIds);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FinishedGoodDto> addFinishedGood(@Valid @NotNull @RequestBody List<AddFinishedGoodDto> payloads) {
        final List<BaseFinishedGood<? extends BaseFinishedGoodMixturePortion<?>, ? extends BaseFinishedGoodMaterialPortion<?>>> additions = payloads.stream()
                                                                                                                                                    .map(addition -> mapper.fromDto(addition))
                                                                                                                                                    .collect(Collectors.toList());
        final List<FinishedGood> added = this.finishedGoodService.add(additions);

        final List<FinishedGoodDto> dtos = added.stream()
                                                .map(finishedGood -> mapper.toDto(finishedGood))
                                                .collect(Collectors.toList());

        return dtos;
    }

    @PutMapping("/{finishedGoodId}")
    public FinishedGoodDto updateFinishedGood(@PathVariable(required = true, name = "finishedGoodId") Long finishedGoodId, @Valid @NotNull @RequestBody UpdateFinishedGoodDto payload) {
        final UpdateFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion> update = mapper.fromDto(payload);
        update.setId(finishedGoodId);

        final FinishedGood finishedGood = this.finishedGoodService.put(List.of(update)).get(0);
        final FinishedGoodDto dto = mapper.toDto(finishedGood);

        return dto;
    }

    @PutMapping("")
    public List<FinishedGoodDto> updateFinishedGoods(@Valid @NotNull @RequestBody List<UpdateFinishedGoodDto> updateFinishedGoodDtos) {
        final List<UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<?>, ? extends UpdateFinishedGoodMaterialPortion<?>>> finishedGoods = updateFinishedGoodDtos.stream()
                                                                                                                                                                            .map(updateFinishedGoodDto -> mapper.fromDto(updateFinishedGoodDto))
                                                                                                                                                                            .collect(Collectors.toList());

        final List<FinishedGood> putFinishedGoods = this.finishedGoodService.put(finishedGoods);

        return putFinishedGoods.stream()
                               .map(putFinishedGood -> mapper.toDto(putFinishedGood))
                               .collect(Collectors.toList());
    }

    @PatchMapping("/{finishedGoodId}")
    public FinishedGoodDto patchFinishedGood(@PathVariable(required = true, name = "finishedGoodId") Long finishedGoodId, @Valid @NotNull @RequestBody UpdateFinishedGoodDto payload) {
        final UpdateFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion> patch = mapper.fromDto(payload);
        patch.setId(finishedGoodId);

        final FinishedGood finishedGood = this.finishedGoodService.patch(List.of(patch)).get(0);
        final FinishedGoodDto dto = mapper.toDto(finishedGood);

        return dto;
    }

    private PageDto<FinishedGoodDto> response(Page<FinishedGood> finishedGoods, Set<String> attributes) {
        final List<FinishedGoodDto> content = finishedGoods.stream().map(i -> mapper.toDto(i)).collect(Collectors.toList());
        content.forEach(finishedGood -> this.filter(finishedGood, attributes));

        final PageDto<FinishedGoodDto> dto = new PageDto<>();
        dto.setContent(content);
        dto.setTotalElements(finishedGoods.getTotalElements());
        dto.setTotalPages(finishedGoods.getTotalPages());
        return dto;
    }
}
