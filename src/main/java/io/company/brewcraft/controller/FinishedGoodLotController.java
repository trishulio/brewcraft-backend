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

import io.company.brewcraft.dto.AddFinishedGoodLotDto;
import io.company.brewcraft.dto.FinishedGoodLotDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateFinishedGoodLot;
import io.company.brewcraft.dto.UpdateFinishedGoodLotDto;
import io.company.brewcraft.model.BaseFinishedGoodLot;
import io.company.brewcraft.model.BaseFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.BaseFinishedGoodLotMixturePortion;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMixturePortion;
import io.company.brewcraft.service.FinishedGoodLotService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.FinishedGoodLotMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/finished-goods")
public class FinishedGoodLotController extends BaseController {
    private static FinishedGoodLotMapper mapper = FinishedGoodLotMapper.INSTANCE;

    private final FinishedGoodLotService finishedGoodLotService;

    @Autowired
    public FinishedGoodLotController(FinishedGoodLotService finishedGoodService, AttributeFilter filter) {
        super(filter);
        this.finishedGoodLotService = finishedGoodService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<FinishedGoodLotDto> getFinishedGoods(
        @RequestParam(required = false, name = "ids") Set<Long> ids,
        @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
        @RequestParam(required = false, name = "sku_ids") Set<Long> skuIds,
        @RequestParam(required = false, name = "mixture_ids") Set<Long> mixtureIds,
        @RequestParam(required = false, name = "brew_stage_ids") Set<Long> brewStageIds,
        @RequestParam(required = false, name = "brew_ids") Set<Long> brewIds,
        @RequestParam(required = false, name = "brew_batch_ids") Set<String> brewBatchIds,
        @RequestParam(required = false, name = "product_ids") Set<Long> productIds,
        @RequestParam(required = false, name = "sku_is_null") Boolean skuIsNull,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        final Page<FinishedGoodLot> finishedGoods = this.finishedGoodLotService.getFinishedGoodLots(
            ids,
            excludeIds,
            skuIds,
            mixtureIds,
            brewStageIds,
            brewIds,
            brewBatchIds,
            productIds,
            skuIsNull,
            sort,
            orderAscending,
            page,
            size
        );

        return this.response(finishedGoods, attributes);
    }

    @GetMapping(value = "/{finishedGoodId}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FinishedGoodLotDto getFinishedGood(@PathVariable(required = true, name = "finishedGoodId") Long finishedGoodId, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        final FinishedGoodLot finishedGood = this.finishedGoodLotService.get(finishedGoodId);
        Validator.assertion(finishedGood != null, EntityNotFoundException.class, "FinishedGood", finishedGoodId.toString());

        final FinishedGoodLotDto dto = mapper.toDto(finishedGood);
        this.filter(dto, attributes);

        return dto;
    }

    @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public long deleteFinishedGoods(@RequestParam("ids") Set<Long> finishedGoodIds) {
        return this.finishedGoodLotService.delete(finishedGoodIds);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<FinishedGoodLotDto> addFinishedGood(@Valid @NotNull @RequestBody List<AddFinishedGoodLotDto> payloads) {
        final List<BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<?>, ? extends BaseFinishedGoodLotMaterialPortion<?>>> additions = payloads.stream()
                                                                                                                                                    .map(addition -> mapper.fromDto(addition))
                                                                                                                                                    .collect(Collectors.toList());
        final List<FinishedGoodLot> added = this.finishedGoodLotService.add(additions);

        final List<FinishedGoodLotDto> dtos = added.stream()
                                                .map(finishedGood -> mapper.toDto(finishedGood))
                                                .toList();

        return dtos;
    }

    @PutMapping(value = "/{finishedGoodId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FinishedGoodLotDto updateFinishedGood(@PathVariable(required = true, name = "finishedGoodId") Long finishedGoodId, @Valid @NotNull @RequestBody UpdateFinishedGoodLotDto payload) {
        final UpdateFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion> update = mapper.fromDto(payload);
        update.setId(finishedGoodId);

        final FinishedGoodLot finishedGood = this.finishedGoodLotService.put(List.of(update)).get(0);
        final FinishedGoodLotDto dto = mapper.toDto(finishedGood);

        return dto;
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FinishedGoodLotDto> updateFinishedGoods(@Valid @NotNull @RequestBody List<UpdateFinishedGoodLotDto> updateFinishedGoodDtos) {
        final List<UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>> finishedGoods = updateFinishedGoodDtos.stream()
                                                                                                                                                                            .map(updateFinishedGoodDto -> mapper.fromDto(updateFinishedGoodDto))
                                                                                                                                                                            .collect(Collectors.toList());

        final List<FinishedGoodLot> putFinishedGoods = this.finishedGoodLotService.put(finishedGoods);

        return putFinishedGoods.stream()
                               .map(putFinishedGood -> mapper.toDto(putFinishedGood))
                               .toList();
    }

    @PatchMapping(value = "/{finishedGoodId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FinishedGoodLotDto patchFinishedGood(@PathVariable(required = true, name = "finishedGoodId") Long finishedGoodId, @Valid @NotNull @RequestBody UpdateFinishedGoodLotDto payload) {
        final UpdateFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion> patch = mapper.fromDto(payload);
        patch.setId(finishedGoodId);

        final FinishedGoodLot finishedGood = this.finishedGoodLotService.patch(List.of(patch)).get(0);
        final FinishedGoodLotDto dto = mapper.toDto(finishedGood);

        return dto;
    }

    private PageDto<FinishedGoodLotDto> response(Page<FinishedGoodLot> finishedGoods, Set<String> attributes) {
        final List<FinishedGoodLotDto> content = finishedGoods.stream().map(i -> mapper.toDto(i)).toList();
        content.forEach(finishedGood -> this.filter(finishedGood, attributes));

        final PageDto<FinishedGoodLotDto> dto = new PageDto<>();
        dto.setContent(content);
        dto.setTotalElements(finishedGoods.getTotalElements());
        dto.setTotalPages(finishedGoods.getTotalPages());
        return dto;
    }
}
