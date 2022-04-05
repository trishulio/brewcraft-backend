package io.company.brewcraft.controller;

import java.util.List;
import java.util.SortedSet;

import javax.validation.Valid;

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

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.GetSuppliersDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1")
public class SupplierController extends BaseController {

    private SupplierService supplierService;

    private SupplierMapper supplierMapper = SupplierMapper.INSTANCE;

    public SupplierController(SupplierService supplierService, AttributeFilter filter) {
        super(filter);
        this.supplierService = supplierService;
    }

    @GetMapping(value = "/suppliers", consumes = MediaType.ALL_VALUE)
    public GetSuppliersDto getSuppliers(
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {
        Page<Supplier> suppliers = supplierService.getSuppliers(page, size, sort, orderAscending);

        List<SupplierDto> supplierDtos = suppliers.stream().map(supplier -> supplierMapper.toDto(supplier)).toList();

        return new GetSuppliersDto(supplierDtos, suppliers.getTotalElements(), suppliers.getTotalPages());
    }

    @GetMapping(value = "/suppliers/{supplierId}", consumes = MediaType.ALL_VALUE)
    public SupplierDto getSupplier(@PathVariable Long supplierId) {
        Supplier supplier = supplierService.getSupplier(supplierId);

        if (supplier == null) {
            throw new EntityNotFoundException("Supplier", supplierId.toString());
        } else {
            return supplierMapper.toDto(supplier);
        }
    }

    @PostMapping("/suppliers")
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierDto addSupplier(@Valid @RequestBody AddSupplierDto supplierDto) {
        Supplier supplier = supplierMapper.fromDto(supplierDto);

        Supplier addedSupplier = supplierService.addSupplier(supplier);

        return supplierMapper.toDto(addedSupplier);
    }

    @PutMapping("/suppliers/{supplierId}")
    public SupplierDto putSupplier(@Valid @RequestBody UpdateSupplierDto supplierDto, @PathVariable Long supplierId) {
        Supplier supplier = supplierMapper.fromDto(supplierDto);

        Supplier putSupplier = supplierService.putSupplier(supplierId, supplier);

        return supplierMapper.toDto(putSupplier);
    }

    @PatchMapping("/suppliers/{supplierId}")
    public SupplierDto patchSupplier(@Valid @RequestBody UpdateSupplierDto supplierDto, @PathVariable Long supplierId) {
        Supplier supplier = supplierMapper.fromDto(supplierDto);

        Supplier patchedSupplier = supplierService.patchSupplier(supplierId, supplier);

        return supplierMapper.toDto(patchedSupplier);
    }

    @DeleteMapping(value = "/suppliers/{supplierId}", consumes = MediaType.ALL_VALUE)
    public void deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
    }
}
