package io.company.brewcraft.dto;

import java.util.List;

public class GetSuppliersDto {
    List<SupplierDto> suppliers;

    Long totalItems;

    int totalPages;

    public GetSuppliersDto() {
    }

    public GetSuppliersDto(List<SupplierDto> suppliers, Long totalItems, int totalPages) {
        this.suppliers = suppliers;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<SupplierDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierDto> suppliers) {
        this.suppliers = suppliers;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
