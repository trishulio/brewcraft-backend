package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
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

import io.company.brewcraft.dto.AddSupplierContactDto;
import io.company.brewcraft.dto.GetSupplierContactsDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierContactWithSupplierDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.dto.UpdateSupplierContactWithSupplierDto;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierContactMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/suppliers")
public class SupplierContactController extends BaseController {
    private SupplierContactService supplierContactService;

    private SupplierContactMapper supplierContactMapper = SupplierContactMapper.INSTANCE;

    public SupplierContactController(SupplierContactService supplierContactService, AttributeFilter filter) {
        super(filter);
        this.supplierContactService = supplierContactService;
    }

    @GetMapping(value = "/contacts", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GetSupplierContactsDto getContacts(
        @RequestParam(required = false, name = "ids") Set<Long> ids,
        @RequestParam(required = false, name = "supplier_ids") Set<Long> supplierIds,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {
        Page<SupplierContact> supplierContacts = supplierContactService.getSupplierContacts(ids, supplierIds, page, size, sort, orderAscending);

        List<SupplierContactWithSupplierDto> supplierContactDtos = supplierContacts.stream().map(supplierContact -> supplierContactMapper.toDtoWithSupplier(supplierContact)).toList();

        return new GetSupplierContactsDto(supplierContactDtos, supplierContacts.getTotalElements(), supplierContacts.getTotalPages());
    }

    @GetMapping(value = "/contacts/{contactId}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SupplierContactWithSupplierDto getContact(@PathVariable Long contactId) {
        SupplierContact supplierContact = supplierContactService.getContact(contactId);

        if (supplierContact == null) {
            throw new EntityNotFoundException("SupplierContact", contactId.toString());
        } else {
        }

        return supplierContactMapper.toDtoWithSupplier(supplierContact);
    }

    @PostMapping(value = "{supplierId}/contacts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierContactDto addContact(@PathVariable Long supplierId, @Valid @RequestBody AddSupplierContactDto supplierContactDo) {
        SupplierContact supplierContact = supplierContactMapper.fromDto(supplierContactDo);

        SupplierContact addedContact = supplierContactService.addContact(supplierId, supplierContact);

        return supplierContactMapper.toDto(addedContact);
    }

    @PutMapping(value = "{supplierId}/contacts/{contactId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SupplierContactDto putContact(@PathVariable Long supplierId, @PathVariable Long contactId, @Valid @RequestBody UpdateSupplierContactDto supplierContactDto) {
        SupplierContact supplierContact = supplierContactMapper.fromDto(supplierContactDto);

        SupplierContact putContact = supplierContactService.putContact(supplierId, contactId, supplierContact);

        return supplierContactMapper.toDto(putContact);
    }

    @PatchMapping(value = "/contacts/{contactId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SupplierContactWithSupplierDto patchContact(@PathVariable Long contactId, @Valid @RequestBody UpdateSupplierContactWithSupplierDto supplierContactDto) {
        SupplierContact supplierContact  = supplierContactMapper.fromDto(supplierContactDto);

        SupplierContact patchedContact = supplierContactService.patchContact(contactId, supplierContactDto.getSupplierId(), supplierContact);

        return supplierContactMapper.toDtoWithSupplier(patchedContact);
    }

    @DeleteMapping(value = "/contacts/{contactId}", consumes = MediaType.ALL_VALUE)
    public void deleteContact(@PathVariable Long contactId) {
        supplierContactService.deleteContact(contactId);
    }
}
