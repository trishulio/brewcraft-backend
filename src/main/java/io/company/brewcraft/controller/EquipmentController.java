package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

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

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.BaseEquipment;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.mapper.EquipmentMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/equipment")
public class EquipmentController extends BaseController {
    private CrudControllerService<
        Long,
        Equipment,
        BaseEquipment,
        UpdateEquipment,
        EquipmentDto,
        AddEquipmentDto,
        UpdateEquipmentDto
    > controller;

    private final EquipmentService equipmentService;

    protected EquipmentController(CrudControllerService<
            Long,
            Equipment,
            BaseEquipment,
            UpdateEquipment,
            EquipmentDto,
            AddEquipmentDto,
            UpdateEquipmentDto
        > controller, EquipmentService equipmentService)
    {
        this.controller = controller;
        this.equipmentService = equipmentService;
    }

    @Autowired
    public EquipmentController(EquipmentService equipmentService, AttributeFilter filter) {
        this(new CrudControllerService<>(filter, EquipmentMapper.INSTANCE, equipmentService, "Equipment"), equipmentService);
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<EquipmentDto> getAllEquipment(
        @RequestParam(required = false, name = "ids") Set<Long> ids,
        @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
        @RequestParam(required = false, name = "facility_ids") Set<Long> facilityIds,
        @RequestParam(required = false, name = "type_ids") Set<Long> typeIds,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
     ) {
        Page<Equipment> equipmentPage = equipmentService.getEquipment(ids, excludeIds, facilityIds, typeIds, page, size, sort, orderAscending);

        return this.controller.getAll(equipmentPage, attributes);
    }

    @GetMapping(value = "/{equipmentId}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentDto getEquipment(@PathVariable(required = true, name = "equipmentId") Long equipmentId, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        return this.controller.get(equipmentId, attributes);
    }

    @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public long deleteEquipment(@RequestParam("ids") Set<Long> equipmentIds) {
        return this.controller.delete(equipmentIds);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<EquipmentDto> addEquipment(@Valid @NotNull @RequestBody List<AddEquipmentDto> addDtos) {
        return this.controller.add(addDtos);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<EquipmentDto> updateEquipment(@Valid @NotNull @RequestBody List<UpdateEquipmentDto> updateDtos) {
        return this.controller.put(updateDtos);
    }

    @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<EquipmentDto> patchEquipment(@Valid @NotNull @RequestBody List<UpdateEquipmentDto> updateDtos) {
        return this.controller.patch(updateDtos);
    }
}
