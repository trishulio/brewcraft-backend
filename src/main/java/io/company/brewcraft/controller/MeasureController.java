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

import io.company.brewcraft.dto.MeasureDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.service.MeasureService;
import io.company.brewcraft.service.mapper.MeasureMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/measures", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MeasureController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(MeasureController.class);

	private MeasureService measureService;

	private MeasureMapper measureMapper = MeasureMapper.INSTANCE;

	public MeasureController(MeasureService measureService, AttributeFilter filter) {
		super(filter);
		this.measureService = measureService;
	}

	@GetMapping(value = "", consumes = MediaType.ALL_VALUE)
	public PageDto<MeasureDto> getMeasures(@RequestParam(required = false) Set<Long> ids,
			@RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
			@RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
			@RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
			@RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

		Page<Measure> measuresPage = measureService.getMeasures(ids, page, size, sort, orderAscending);

		List<MeasureDto> measureList = measuresPage.stream().map(measure -> measureMapper.toDto(measure))
				.collect(Collectors.toList());

		PageDto<MeasureDto> dto = new PageDto<MeasureDto>(measureList, measuresPage.getTotalPages(),
				measuresPage.getTotalElements());

		return dto;
	}

}
