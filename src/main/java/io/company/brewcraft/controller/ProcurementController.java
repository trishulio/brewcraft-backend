package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.service.impl.procurement.ProcurementService;
import io.company.brewcraft.service.mapper.procurement.ProcurementIdMapper;
import io.company.brewcraft.service.mapper.procurement.ProcurementMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/procurements", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcurementController extends BaseController {

    private CrudControllerService<
        ProcurementId,
        Procurement,
        BaseProcurement<InvoiceItem, MaterialLot, ProcurementItem>,
        UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>,
        ProcurementDto,
        AddProcurementDto,
        UpdateProcurementDto
    > controller;

    @Autowired
    public ProcurementController(AttributeFilter filter, ProcurementService service) {
        this(new CrudControllerService<>(filter, ProcurementMapper.INSTANCE, service, ""));
    }

    protected ProcurementController(
            CrudControllerService<
            ProcurementId,
            Procurement,
            BaseProcurement<InvoiceItem, MaterialLot, ProcurementItem>,
            UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>,
            ProcurementDto,
            AddProcurementDto,
            UpdateProcurementDto
        > controller) {
        this.controller = controller;
    }

    @GetMapping("/{shipmentId}/{invoiceId}")
    public ProcurementDto get(@PathVariable(required = true, name = "shipmentId") Long shipmentId, @PathVariable(required = true, name = "invoiceId") Long invoiceId, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        ProcurementId id = new ProcurementId(shipmentId, invoiceId);
        return this.controller.get(id, attributes);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProcurementDto> add(@Valid @RequestBody @NotNull List<AddProcurementDto> dtos) {
        return this.controller.add(dtos);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProcurementDto> put(@Valid @RequestBody @NotNull List<UpdateProcurementDto> dtos) {
        return this.controller.put(dtos);
    }

    @PatchMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProcurementDto> patch(List<UpdateProcurementDto> dtos) {
        return this.controller.patch(dtos);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public int delete(@RequestParam("ids") Set<ProcurementIdDto> ids) {
        Set<ProcurementId> pIds = ids.stream().map(id -> ProcurementIdMapper.INSTANCE.fromDto(id)).collect(Collectors.toSet());
        return this.controller.delete(pIds);
    }
}
