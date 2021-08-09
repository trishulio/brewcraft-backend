package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetSupplierContactsDtoTest {

    private GetSupplierContactsDto getSupplierContactsDto;

    @BeforeEach
    public void init() {
        getSupplierContactsDto = new GetSupplierContactsDto();
    }

    @Test
    public void testConstructor() {
        List<SupplierContactWithSupplierDto> supplierContactDtos = new ArrayList<>();
        Long totalItems = 10L;
        int totalPages = 2;

        GetSupplierContactsDto getSupplierContactsDto = new GetSupplierContactsDto(supplierContactDtos, totalItems, totalPages);
        assertSame(supplierContactDtos, getSupplierContactsDto.getSupplierContacts());
        assertSame(totalItems, getSupplierContactsDto.getTotalItems());
        assertSame(totalPages, getSupplierContactsDto.getTotalPages());
    }

    @Test
    public void testGetSetSupplierDtos() {
        List<SupplierContactWithSupplierDto> supplierContactDtos = new ArrayList<>();
        getSupplierContactsDto.setSupplierContacts(supplierContactDtos);
        assertSame(supplierContactDtos, getSupplierContactsDto.getSupplierContacts());
    }

    @Test
    public void testGetSetTotalItems() {
        Long totalItems = 10L;
        getSupplierContactsDto.setTotalItems(totalItems);
        assertSame(totalItems, getSupplierContactsDto.getTotalItems());
    }

    @Test
    public void testGetSetTotalPages() {
        int totalPages = 10;
        getSupplierContactsDto.setTotalPages(totalPages);
        assertSame(totalPages, getSupplierContactsDto.getTotalPages());
    }

}
