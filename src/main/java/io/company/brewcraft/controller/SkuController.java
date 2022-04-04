package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.company.brewcraft.dto.AddSkuDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.dto.UpdateSkuDto;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.service.SkuService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SkuMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/skus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SkuController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SkuController.class);

    private SkuService skuService;

    private SkuMapper skuMapper = SkuMapper.INSTANCE;

    public SkuController(SkuService skuService, AttributeFilter filter) {
        super(filter);
        this.skuService = skuService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<SkuDto> getSkus(
            @RequestParam(required = false, name = "ids") Set<Long> ids,
            @RequestParam(required = false, name = "product_ids") Set<Long> productIds,
            @RequestParam(required = false, name = "is_packageable") Boolean isPackageable,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

        Page<Sku> skuPage = skuService.getSkus(ids, productIds, isPackageable, page, size, sort, orderAscending);

        List<SkuDto> skuList = skuPage.stream()
                                      .map(sku -> skuMapper.toDto(sku))
                                      .collect(Collectors.toList());

        PageDto<SkuDto> dto = new PageDto<>(skuList, skuPage.getTotalPages(), skuPage.getTotalElements());

        return dto;
    }

    @GetMapping(value = "/{skuId}", consumes = MediaType.ALL_VALUE)
    public SkuDto getSku(@PathVariable Long skuId) {
        Sku sku = skuService.getSku(skuId);

        Validator.assertion(sku != null, EntityNotFoundException.class, "SKU", skuId.toString());

        return skuMapper.toDto(sku);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SkuDto addSku(@Valid @RequestBody AddSkuDto addSkuDto) {
        Sku sku = skuMapper.fromDto(addSkuDto);

        Sku addedSku = skuService.addSkus(List.of(sku)).get(0);

        return skuMapper.toDto(addedSku);
    }

    @PutMapping("/{skuId}")
    public SkuDto putSku(@PathVariable Long skuId, @Valid @RequestBody UpdateSkuDto updateSkuDto) {
        Sku sku = skuMapper.fromDto(updateSkuDto);
        sku.setId(skuId);

        Sku putSku = skuService.putSkus(List.of(sku)).get(0);

        return skuMapper.toDto(putSku);
    }

    @PatchMapping("/{skuId}")
    public SkuDto patchSku(@PathVariable Long skuId, @Valid @RequestBody UpdateSkuDto updateSkuDto) {
        Sku sku = skuMapper.fromDto(updateSkuDto);
        sku.setId(skuId);

        Sku patchedSku = skuService.patchSkus(List.of(sku)).get(0);

        return skuMapper.toDto(patchedSku);
    }

    @DeleteMapping(value = "/{skuId}", consumes = MediaType.ALL_VALUE)
    public void deleteSku(@PathVariable Long skuId) {
        skuService.deleteSku(skuId);
    }
}
