package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetSuppliersDtoTest {
    private GetSuppliersDto getSuppliersDto;

    @BeforeEach
    public void init() {
        getSuppliersDto = new GetSuppliersDto();
    }

    @Test
    public void testConstructor() {
        List<SupplierDto> supplierDtos = new ArrayList<>();
        Long totalItems = 10L;
        int totalPages = 2;

        GetSuppliersDto getSuppliersDto = new GetSuppliersDto(supplierDtos, totalItems, totalPages);
        assertSame(supplierDtos, getSuppliersDto.getSuppliers());
        assertSame(totalItems, getSuppliersDto.getTotalItems());
        assertSame(totalPages, getSuppliersDto.getTotalPages());
    }

    @Test
    public void testGetSetSupplierDtos() {
        List<SupplierDto> supplierDtos = new ArrayList<>();
        getSuppliersDto.setSuppliers(supplierDtos);
        assertSame(supplierDtos, getSuppliersDto.getSuppliers());
    }

    @Test
    public void testGetSetTotalItems() {
        Long totalItems = 10L;
        getSuppliersDto.setTotalItems(totalItems);
        assertSame(totalItems, getSuppliersDto.getTotalItems());
    }

    @Test
    public void testGetSetTotalPages() {
        int totalPages = 10;
        getSuppliersDto.setTotalPages(totalPages);
        assertSame(totalPages, getSuppliersDto.getTotalPages());
    }
}
