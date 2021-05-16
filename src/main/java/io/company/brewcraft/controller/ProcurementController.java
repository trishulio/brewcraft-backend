package io.company.brewcraft.controller;

import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.mapper.procurement.ProcurementMapper;
import io.company.brewcraft.service.procurement.ProcurementService;
import io.company.brewcraft.util.controller.AttributeFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/api/v1/procurements", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcurementController extends BaseController {

    private final ProcurementService service;

    public ProcurementController(final AttributeFilter filter, final ProcurementService service) {
        super(filter);
        this.service = service;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProcurementDto add(@Valid @RequestBody @NotNull AddProcurementDto dto) {
        Procurement procurement = ProcurementMapper.INSTANCE.fromDto(dto);
        Procurement added = service.add(procurement);
        return ProcurementMapper.INSTANCE.toDto(added);
    }
}
